package com.code.salesappbackend.controllers.socket;

import com.code.salesappbackend.dtos.requests.socket.MessageDto;
import com.code.salesappbackend.dtos.responses.Response;
import com.code.salesappbackend.dtos.responses.ResponseSuccess;
import com.code.salesappbackend.services.interfaces.socket.MessageService;
import com.code.salesappbackend.utils.ValidToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final ValidToken validToken;

    @PostMapping("/send")
    public Response senMessage(@ModelAttribute @Valid MessageDto messageDto,
                               HttpServletRequest req)
            throws Exception {
        validToken.valid(messageDto.getSender(), req);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "success",
                messageService.sendMessage(messageDto)
        );
    }

    @GetMapping("/{roomId}")
    public Response getMessage(
                               @PathVariable String roomId,
                               @RequestParam String email,
                               @RequestParam(defaultValue = "1") int pageNo,
                               @RequestParam(defaultValue = "10") int pageSize,
                               HttpServletRequest req) {
        validToken.valid(email, req);
        String roomSearch = "roomId-" + roomId;
        String[] search = {roomSearch};
        String[] sort = {"sendDate:desc"};
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "success",
                messageService.getPageData(pageNo, pageSize, search, sort)
        );
    }

}
