package com.code.salesappbackend.controllers.user;

import com.code.salesappbackend.dtos.requests.auth.ChangePasswordRequest;
import com.code.salesappbackend.dtos.requests.user.UserDto;
import com.code.salesappbackend.dtos.requests.user.UserUpdateDto;
import com.code.salesappbackend.dtos.responses.Response;
import com.code.salesappbackend.dtos.responses.ResponseSuccess;

import com.code.salesappbackend.mappers.user.UserMapper;
import com.code.salesappbackend.models.user.User;
import com.code.salesappbackend.services.interfaces.user.UserService;
import com.code.salesappbackend.utils.S3Upload;
import com.code.salesappbackend.utils.ValidToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final ValidToken validToken;
    private final S3Upload s3Upload;


    @PostMapping
    public Response createUser(@RequestBody UserDto userDto) {
        User user = userMapper.userDto2User(userDto);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "create user successfully",
                userService.save(user)
        );
    }

    @GetMapping("/{email}")
    public Response getUserById(@PathVariable String email, HttpServletRequest request)
            throws Exception {
        validToken.valid(email, request);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "find user successfully",
                userService.getUserByEmail(email)
        );
    }

    @PutMapping("/{email}")
    public Response updateUser(@PathVariable String email, @RequestBody UserUpdateDto userDto, HttpServletRequest request)
            throws Exception {
        validToken.valid(email, request);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "updated user",
                userService.updateUser(email, userDto)
        );
    }


    @PostMapping("/change-password")
    public Response changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, HttpServletRequest request)
    throws Exception {
        validToken.valid(changePasswordRequest.getEmail(), request);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "change password successfully",
                userService.changePassword(changePasswordRequest)
        );
    }

    @PostMapping("/upload")
    public Response uploadAvatar(@RequestParam("avatar") MultipartFile file) throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "success",
                s3Upload.uploadFile(file)
        );
    }
}
