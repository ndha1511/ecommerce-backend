package com.code.salesappbackend.controllers;

import com.code.salesappbackend.dtos.requests.ProductPriceDto;
import com.code.salesappbackend.dtos.responses.ResponseSuccess;
import com.code.salesappbackend.mappers.ProductPriceMapper;
import com.code.salesappbackend.services.interfaces.ProductPriceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/productPrices")
public class ProductPriceController {

    private final ProductPriceService productPriceService;
    private final ProductPriceMapper productPriceMapper;

    @PostMapping
    public ResponseSuccess<?> addProductPrice(
            @RequestBody @Valid ProductPriceDto productPriceDto)
    throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "create product price successfully",
                productPriceService.save(productPriceMapper
                        .productPriceDto2ProductPrice(productPriceDto))
        );
    }
}
