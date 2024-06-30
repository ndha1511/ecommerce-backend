package com.code.salesappbackend.controllers;

import com.code.salesappbackend.dtos.requests.SizeDto;
import com.code.salesappbackend.dtos.responses.Response;
import com.code.salesappbackend.dtos.responses.ResponseSuccess;
import com.code.salesappbackend.mappers.SizeMapper;
import com.code.salesappbackend.services.interfaces.SizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sizes")
@RequiredArgsConstructor
public class SizeController {
    private final SizeService sizeService;
    private final SizeMapper sizeMapper;

    @PostMapping
    public Response addSize(@RequestBody SizeDto sizeDto) throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "create size successfully",
                sizeService.save(sizeMapper.sizeDto2Size(sizeDto))
        );
    }

    @GetMapping
    public ResponseSuccess<?> getAllSizes() {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "get size successfully",
                sizeService.findAll()
        );
    }
}
