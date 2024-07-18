package com.code.salesappbackend.controllers;

import com.code.salesappbackend.dtos.requests.CommentDto;
import com.code.salesappbackend.dtos.responses.Response;
import com.code.salesappbackend.dtos.responses.ResponseSuccess;
import com.code.salesappbackend.dtos.responses.comments.CommentResponse;
import com.code.salesappbackend.services.interfaces.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final SimpMessagingTemplate messagingTemplate;
    private final CommentService commentService;

    @PostMapping("/send")
    public Response send(@ModelAttribute CommentDto commentDto)
    throws Exception{
        CommentResponse comment = commentService.addComment(commentDto);
        messagingTemplate.convertAndSend("/topic/product/" + commentDto.getProductId(),
                comment);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "oke"
        );
    }

    @GetMapping("/page-comment")
    public Response pageComments(@RequestParam(defaultValue = "1") int pageNo,
                                @RequestParam(defaultValue = "10") int pageSize,
                                @RequestParam(required = false) String[] search,
                                @RequestParam(required = false) String[] sort) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "get comment successfully",
                commentService.getPageData(pageNo, pageSize, search, sort)
        );
    }
}
