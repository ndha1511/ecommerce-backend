package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.CommentMedia;

import java.util.List;


public interface CommentMediaRepository extends BaseRepository<CommentMedia, Long> {
    List<CommentMedia> findAllByCommentId(Long commentId);
}