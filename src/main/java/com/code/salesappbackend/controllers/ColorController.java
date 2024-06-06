package com.code.salesappbackend.controllers;

import com.code.salesappbackend.dtos.requests.ColorDto;
import com.code.salesappbackend.dtos.responses.ResponseSuccess;
import com.code.salesappbackend.mappers.ColorMapper;
import com.code.salesappbackend.services.interfaces.ColorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/colors")
@RequiredArgsConstructor
public class ColorController {
    private final ColorService colorService;
    private final ColorMapper colorMapper;

    @PostMapping
    public ResponseSuccess<?> addColor(@RequestBody @Valid ColorDto colorDto)
        throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "create color successfully",
                colorService.save(colorMapper.colorDto2Color(colorDto))
        );
    }
}
