package com.code.salesappbackend.dtos.responses.comments;

import com.code.salesappbackend.models.Comment;
import com.code.salesappbackend.models.CommentMedia;
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
