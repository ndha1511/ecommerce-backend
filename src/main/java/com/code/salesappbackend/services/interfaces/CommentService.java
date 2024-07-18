package com.code.salesappbackend.services.interfaces;

import com.code.salesappbackend.dtos.requests.CommentDto;
import com.code.salesappbackend.dtos.responses.comments.CommentResponse;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.exceptions.MediaTypeNotSupportException;
import com.code.salesappbackend.models.Comment;

import java.io.IOException;

public interface CommentService extends BaseService<Comment, Long> {
    CommentResponse addComment(CommentDto commentDto) throws DataNotFoundException, IOException, MediaTypeNotSupportException;
}
