package com.code.salesappbackend.controllers;

import com.code.salesappbackend.dtos.requests.ColorDto;
import com.code.salesappbackend.dtos.responses.Response;
import com.code.salesappbackend.dtos.responses.ResponseSuccess;
import com.code.salesappbackend.mappers.ColorMapper;
import com.code.salesappbackend.services.interfaces.ColorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/colors")
@RequiredArgsConstructor
public class ColorController {
    private final ColorService colorService;
    private final ColorMapper colorMapper;

    @PostMapping
    public Response addColor(@RequestBody @Valid ColorDto colorDto)
        throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "create color successfully",
                colorService.save(colorMapper.colorDto2Color(colorDto))
        );
    }

    @GetMapping
    public ResponseSuccess<?> getAllColors() {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "get all colors successfully",
                colorService.findAll()
        );
    }
}
