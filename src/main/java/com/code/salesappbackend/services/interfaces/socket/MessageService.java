package com.code.salesappbackend.services.interfaces.socket;

import com.code.salesappbackend.dtos.requests.socket.MessageDto;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.exceptions.MediaTypeNotSupportException;
import com.code.salesappbackend.models.socket.Message;
import com.code.salesappbackend.services.interfaces.BaseService;

import java.io.IOException;

public interface MessageService extends BaseService<Message, String> {
    Message sendMessage(MessageDto messageDto) throws IOException, DataNotFoundException, MediaTypeNotSupportException;
}
