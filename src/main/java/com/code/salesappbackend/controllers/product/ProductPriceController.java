package com.code.salesappbackend.controllers.product;

import com.code.salesappbackend.dtos.requests.product.ProductPriceDto;
import com.code.salesappbackend.dtos.responses.Response;
import com.code.salesappbackend.dtos.responses.ResponseSuccess;
import com.code.salesappbackend.mappers.product.ProductPriceMapper;
import com.code.salesappbackend.services.interfaces.product.ProductPriceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product-prices")
public class ProductPriceController {

    private final ProductPriceService productPriceService;
    private final ProductPriceMapper productPriceMapper;

    @PostMapping
    public Response addProductPrice(
            @RequestBody @Valid ProductPriceDto productPriceDto)
    throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "create product price successfully",
                productPriceService.save(productPriceMapper
                        .productPriceDto2ProductPrice(productPriceDto))
        );
    }

    @GetMapping("/{productId}")
    public Response getProductPriceByProductId(@PathVariable Long productId) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "success",
                productPriceService.findByProductId(productId)
        );
    }

    @DeleteMapping("/{id}")
    public Response deleteProductPrice(@PathVariable Long id) {
        productPriceService.deleteById(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "success"
        );
    }

    @PutMapping("/{id}")
    public Response updateProductPrice(@PathVariable Long id, @RequestBody @Valid ProductPriceDto productPriceDto)
    throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "success",
                productPriceService.update(id,
                        productPriceMapper.productPriceDto2ProductPrice(productPriceDto))
        );
    }

}
