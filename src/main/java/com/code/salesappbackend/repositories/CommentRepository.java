package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.Comment;

import java.util.List;


public interface CommentRepository extends BaseRepository<Comment, Long> {
    List<Comment> findAllByProductId(Long productId);
}