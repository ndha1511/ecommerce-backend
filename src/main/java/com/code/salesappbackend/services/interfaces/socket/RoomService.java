package com.code.salesappbackend.services.interfaces.socket;

import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.models.socket.RoomChat;
import com.code.salesappbackend.services.interfaces.BaseService;

import java.util.List;

public interface RoomService extends BaseService<RoomChat, Long> {
    List<RoomChat> getRoomsBySender(String sender);
    String getRoomIdBySenderAndReceiver(String sender, String receiver, boolean createIfNotExists) throws DataNotFoundException;
}
