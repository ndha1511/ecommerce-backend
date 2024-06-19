package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends BaseRepository<Message, String> {
}