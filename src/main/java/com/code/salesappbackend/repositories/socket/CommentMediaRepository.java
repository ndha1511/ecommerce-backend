package com.code.salesappbackend.repositories.socket;

import com.code.salesappbackend.models.socket.CommentMedia;
import com.code.salesappbackend.repositories.BaseRepository;

import java.util.List;


public interface CommentMediaRepository extends BaseRepository<CommentMedia, Long> {
    List<CommentMedia> findAllByCommentId(Long commentId);
}