package com.code.salesappbackend.controllers;

import com.code.salesappbackend.dtos.requests.ProviderDto;
import com.code.salesappbackend.dtos.responses.ResponseSuccess;
import com.code.salesappbackend.mappers.ProviderMapper;
import com.code.salesappbackend.models.Provider;
import com.code.salesappbackend.services.interfaces.ProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/providers")
@RequiredArgsConstructor
public class ProviderController {
    private final ProviderService providerService;
    private final ProviderMapper providerMapper;

    @GetMapping
    public ResponseSuccess<?> getProviders() {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "get providers successfully",
                providerService.findAll()
        );
    }

    @PostMapping
    public ResponseSuccess<?> addProvider(@RequestBody @Valid ProviderDto providerDto)
        throws Exception {
        Provider provider = providerMapper.providerDto2Provider(providerDto);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "create provider successfully",
                providerService.save(provider)
        );
    }
}
