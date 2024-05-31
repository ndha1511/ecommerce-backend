package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.MessageMedia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageMediaRepository extends JpaRepository<MessageMedia, Long> {
}