package com.code.salesappbackend.controllers;

import com.code.salesappbackend.dtos.requests.ProductDetailDto;
import com.code.salesappbackend.dtos.responses.Response;
import com.code.salesappbackend.dtos.responses.ResponseSuccess;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.mappers.ProductDetailMapper;
import com.code.salesappbackend.services.interfaces.ProductDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/product-details")
@RequiredArgsConstructor
public class ProductDetailController {
    private final ProductDetailService productDetailService;
    private final ProductDetailMapper productDetailMapper;

    @PostMapping
    public Response addProductDetail(
            @RequestBody @Valid ProductDetailDto productDetailDto)
     throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "create product detail successfully",
                productDetailService.save(productDetailMapper
                        .productDetailDto2ProductDetail(productDetailDto))
        );
    }

    @PatchMapping("/{id}")
    public Response updateProductDetail(@PathVariable Long id, @RequestBody Map<String, ?> data)
            throws DataNotFoundException {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "updated address",
                productDetailService.updatePatch(id, data)
        );
    }

    @DeleteMapping("/{id}")
    public Response deleteProductDetail(@PathVariable Long id) {
        productDetailService.deleteById(id);
        return  new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "deleted product detail with id: " + id
        );
    }

    @GetMapping("/page-product-detail")
    public Response pageProduct(@RequestParam(defaultValue = "1") int pageNo,
                                          @RequestParam(defaultValue = "10") int pageSize,
                                          @RequestParam(required = false) String[] sort,
                                          @RequestParam(required = false) String[] search)  throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "get product page",
                productDetailService.getPageData(pageNo, pageSize, search, sort)
        );

    }
}
