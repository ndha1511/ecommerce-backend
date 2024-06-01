package com.code.salesappbackend.controllers;

import com.code.salesappbackend.dtos.requests.UserDto;
import com.code.salesappbackend.dtos.responses.ResponseSuccess;

import com.code.salesappbackend.mappers.UserMapper;
import com.code.salesappbackend.models.User;
import com.code.salesappbackend.services.interfaces.UserService;
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
    public ResponseSuccess<?> getAllUsers() {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "get users successfully",
                userService.findAll()
        );
    }

    @PostMapping
    public ResponseSuccess<?> createUser(@RequestBody UserDto userDto) {
        User user = userMapper.userDto2User(userDto);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "create user successfully",
                userService.save(user)
        );
    }

    @GetMapping("/{id}")
    public ResponseSuccess<?> getUserById(@PathVariable Long id) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "find user successfully",
                userService.findById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseSuccess<?> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User user = userMapper.userDto2User(userDto);
        user.setId(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "updated user",
                userService.update(id, user)
        );
    }

    @PatchMapping("/{id}")
    public ResponseSuccess<?> updatePatchUser(@PathVariable Long id, @RequestBody Map<String, ?> data) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "updated user",
                userService.updatePatch(id, data)
        );
    }
}
