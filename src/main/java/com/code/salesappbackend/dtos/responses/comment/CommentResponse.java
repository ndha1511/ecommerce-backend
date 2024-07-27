package com.code.salesappbackend.dtos.responses.comment;

import com.code.salesappbackend.models.socket.Comment;
import com.code.salesappbackend.models.socket.CommentMedia;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private Comment comment;
    private List<CommentMedia> commentMedia;
}
