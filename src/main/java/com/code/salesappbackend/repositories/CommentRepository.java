package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends BaseRepository<Comment, Long> {
}