package com.code.salesappbackend.controllers.product;

import com.code.salesappbackend.dtos.requests.product.ProviderDto;
import com.code.salesappbackend.dtos.responses.Response;
import com.code.salesappbackend.dtos.responses.ResponseSuccess;
import com.code.salesappbackend.mappers.product.ProviderMapper;
import com.code.salesappbackend.models.product.Provider;
import com.code.salesappbackend.services.interfaces.product.ProviderService;
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
    public Response getProviders() {
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
