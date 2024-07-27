package com.code.salesappbackend.repositories.socket;

import com.code.salesappbackend.models.socket.Comment;
import com.code.salesappbackend.repositories.BaseRepository;

import java.util.List;


public interface CommentRepository extends BaseRepository<Comment, Long> {
    List<Comment> findAllByProductId(Long productId);
}