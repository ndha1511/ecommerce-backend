package com.code.salesappbackend.services.interfaces.socket;

import com.code.salesappbackend.dtos.requests.socket.CommentDto;
import com.code.salesappbackend.dtos.responses.comment.CommentResponse;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.exceptions.MediaTypeNotSupportException;
import com.code.salesappbackend.models.socket.Comment;
import com.code.salesappbackend.services.interfaces.BaseService;

import java.io.IOException;

public interface CommentService extends BaseService<Comment, Long> {
    CommentResponse addComment(CommentDto commentDto) throws DataNotFoundException, IOException, MediaTypeNotSupportException;
}
