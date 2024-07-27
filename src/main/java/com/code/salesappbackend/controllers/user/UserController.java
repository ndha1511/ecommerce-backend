package com.code.salesappbackend.controllers.user;

import com.code.salesappbackend.dtos.requests.auth.ChangePasswordRequest;
import com.code.salesappbackend.dtos.requests.user.UserDto;
import com.code.salesappbackend.dtos.responses.Response;
import com.code.salesappbackend.dtos.responses.ResponseSuccess;

import com.code.salesappbackend.mappers.user.UserMapper;
import com.code.salesappbackend.models.user.User;
import com.code.salesappbackend.services.interfaces.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public Response getAllUsers() {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "get users successfully",
                userService.findAll()
        );
    }

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
    public Response getUserById(@PathVariable String email)
            throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "find user successfully",
                userService.getUserByEmail(email)
        );
    }

    @PutMapping("/{id}")
    public Response updateUser(@PathVariable Long id, @RequestBody UserDto userDto)
            throws Exception {
        User user = userMapper.userDto2User(userDto);
        user.setId(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "updated user",
                userService.update(id, user)
        );
    }

    @PatchMapping("/{id}")
    public Response updatePatchUser(@PathVariable Long id, @RequestBody Map<String, ?> data)
            throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "updated user",
                userService.updatePatch(id, data)
        );
    }

    @PostMapping("/change-password")
    public Response changePassword(@RequestBody ChangePasswordRequest changePasswordRequest)
    throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "change password successfully",
                userService.changePassword(changePasswordRequest)
        );
    }
}
