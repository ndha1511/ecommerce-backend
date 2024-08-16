package com.code.salesappbackend.controllers.socket;

import com.code.salesappbackend.dtos.responses.Response;
import com.code.salesappbackend.dtos.responses.ResponseSuccess;
import com.code.salesappbackend.services.interfaces.socket.RoomService;
import com.code.salesappbackend.utils.ValidToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final ValidToken validToken;

    @GetMapping("/{email}")
    public Response getRoom(@PathVariable String email,
                            @RequestParam(defaultValue = "1") int pageNo,
                            @RequestParam(defaultValue = "10") int pageSize,
                            HttpServletRequest req) {
        validToken.valid(email, req);
        String emailSearch = "sender-" + email;
        String[] search = {emailSearch};
        String[] sort = {"isSeen:asc"};
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "success",
                roomService.getPageData(pageNo, pageSize, search, sort)
        );

    }
}
