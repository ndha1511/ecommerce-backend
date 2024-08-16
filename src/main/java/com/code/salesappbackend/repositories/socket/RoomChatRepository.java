package com.code.salesappbackend.repositories.socket;

import com.code.salesappbackend.models.socket.RoomChat;
import com.code.salesappbackend.repositories.BaseRepository;

import java.util.Optional;

public interface RoomChatRepository extends BaseRepository<RoomChat, Long> {
    Optional<RoomChat> findByRoomId(String roomId);
    Optional<RoomChat> findBySenderAndReceiver(String sender, String receiver);
}