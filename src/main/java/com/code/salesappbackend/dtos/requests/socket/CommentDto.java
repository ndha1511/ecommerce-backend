package com.code.salesappbackend.dtos.requests.socket;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
public class CommentDto {
    private String email;
    private String content;
    private Long productId;
    private Float rating;
    private List<MultipartFile> media;
}
