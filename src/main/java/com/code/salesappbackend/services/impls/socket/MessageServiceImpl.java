package com.code.salesappbackend.services.impls.socket;

import com.code.salesappbackend.dtos.requests.socket.MessageDto;
import com.code.salesappbackend.dtos.responses.socket.MessageResponse;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.exceptions.MediaTypeNotSupportException;
import com.code.salesappbackend.models.enums.MessageType;
import com.code.salesappbackend.models.socket.Message;
import com.code.salesappbackend.models.socket.RoomChat;
import com.code.salesappbackend.repositories.BaseRepository;
import com.code.salesappbackend.repositories.socket.RoomChatRepository;
import com.code.salesappbackend.services.impls.BaseServiceImpl;
import com.code.salesappbackend.services.interfaces.socket.MessageService;
import com.code.salesappbackend.services.interfaces.socket.RoomService;
import com.code.salesappbackend.utils.S3Upload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message, String> implements MessageService {

    private final Set<String> IMAGE_EXTENSIONS = new HashSet<>(Arrays.asList("jpg", "jpeg", "png", "gif", "bmp"));
    private final Set<String> VIDEO_EXTENSIONS = new HashSet<>(Arrays.asList("mp4", "avi", "mov", "mkv", "webm"));
    private final S3Upload s3Upload;
    private final RoomService roomService;
    private final RoomChatRepository roomChatRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public MessageServiceImpl(BaseRepository<Message, String> repository,
                              S3Upload s3Upload,
                              RoomService roomService,
                              RoomChatRepository roomChatRepository,
                              SimpMessagingTemplate messagingTemplate) {
        super(repository, Message.class);
        this.s3Upload = s3Upload;
        this.roomService = roomService;
        this.roomChatRepository = roomChatRepository;
        this.messagingTemplate = messagingTemplate;
    }


    @Override
    public Message sendMessage(MessageDto messageDto) throws IOException, DataNotFoundException, MediaTypeNotSupportException {
        Message message = new Message();
        message.setSender(messageDto.getSender());
        message.setReceiver(messageDto.getReceiver());
        message.setMessageType(MessageType.TEXT);
        message.setSendDate(LocalDateTime.now());
        message.setContent(messageDto.getMessage());
        if(messageDto.getFile() != null) {
            message.setMessageType(getMessageType(messageDto.getFile()));
            message.setPath(s3Upload.uploadFile(messageDto.getFile()));
        }
        message.setId(UUID.randomUUID().toString());
        message.setRoomId(roomService
                .getRoomIdBySenderAndReceiver(messageDto.getSender(),
                        messageDto.getReceiver(),
                        true));
        super.save(message);
        RoomChat roomReceiver = roomChatRepository.findBySenderAndReceiver(message.getReceiver(), message.getSender())
                .orElseThrow(() -> new DataNotFoundException("room not found"));
        roomReceiver.setSeen(false);
        roomChatRepository.save(roomReceiver);
        sendToSocket(message);
        return message;
    }


    private String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex >= 0 && lastDotIndex < filename.length() - 1) {
            return filename.substring(lastDotIndex + 1);
        }
        return "";
    }

    private MessageType getMessageType(MultipartFile file) throws MediaTypeNotSupportException {
        String filename = file.getOriginalFilename();
        if (filename == null) {
            return MessageType.TEXT;
        }
        String extension = getFileExtension(filename);
        if(IMAGE_EXTENSIONS.contains(extension.toLowerCase())) {
            return MessageType.IMAGE;
        }
        if(VIDEO_EXTENSIONS.contains(extension.toLowerCase())) {
            return MessageType.VIDEO;
        }
        throw new MediaTypeNotSupportException("only allow sending image or video");
    }

    private void sendToSocket(Message message) {
        MessageResponse<Message> response = new MessageResponse<>();
        response.setData(message);
        response.setType("message");
        messagingTemplate.convertAndSendToUser(message.getReceiver(),"/queue/notifications", response);
    }
}
