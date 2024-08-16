package com.code.salesappbackend.services.impls.socket;

import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.models.socket.RoomChat;
import com.code.salesappbackend.repositories.BaseRepository;
import com.code.salesappbackend.repositories.socket.RoomChatRepository;
import com.code.salesappbackend.repositories.user.UserRepository;
import com.code.salesappbackend.services.impls.BaseServiceImpl;
import com.code.salesappbackend.services.interfaces.socket.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl extends BaseServiceImpl<RoomChat, Long> implements RoomService {
    private final RoomChatRepository roomChatRepository;
    private final UserRepository userRepository;


    public RoomServiceImpl(BaseRepository<RoomChat, Long> repository,
                           RoomChatRepository roomChatRepository, UserRepository userRepository) {
        super(repository, RoomChat.class);
        this.roomChatRepository = roomChatRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<RoomChat> getRoomsBySender(String sender) {
        return List.of();
    }

    @Override
    public String getRoomIdBySenderAndReceiver(String sender, String receiver, boolean createIfNotExists) throws DataNotFoundException {
        String roomId = concatRoomId(sender, receiver);
        Optional<RoomChat> optional = roomChatRepository.findBySenderAndReceiver(sender, receiver);
        if(!userRepository.existsByEmail(sender)) throw new DataNotFoundException("sender not found");
        if(!userRepository.existsByEmail(receiver)) throw new DataNotFoundException("receiver not found");
        if(optional.isEmpty()) {
            if(!createIfNotExists) {
                throw new DataNotFoundException("Room not found");
            }
            saveRoom(roomId, sender, receiver);
            saveRoom(roomId, receiver, sender);
        } else {
            roomId = optional.get().getRoomId();
        }
        return roomId;
    }

    private String concatRoomId(String sender, String receiver) {
        return sender + "_" + receiver;
    }

    private void saveRoom(String roomId, String sender, String receiver) {
        RoomChat roomChat = new RoomChat();
        roomChat.setRoomId(roomId);
        roomChat.setSender(sender);
        roomChat.setReceiver(receiver);
        roomChatRepository.save(roomChat);
    }


}
