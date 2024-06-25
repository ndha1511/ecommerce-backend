package com.code.salesappbackend.controllers;

import com.code.salesappbackend.dtos.requests.ProductDto;
import com.code.salesappbackend.dtos.responses.PageResponse;
import com.code.salesappbackend.dtos.responses.ResponseSuccess;
import com.code.salesappbackend.models.Product;
import com.code.salesappbackend.services.interfaces.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseSuccess<?> addProduct(@Valid @ModelAttribute ProductDto productDto)
            throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "create product successfully",
                productService.save(productDto)
        );
    }

    @GetMapping
    public ResponseSuccess<?> getAllProducts() {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "get products successfully",
                productService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseSuccess<?> getProductById(@PathVariable Long id) throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "get product successfully",
                productService.findProductById(id)
        );
    }

    @GetMapping("/page-product")
    public ResponseSuccess<?> pageProduct(@RequestParam(defaultValue = "1") int pageNo,
                                          @RequestParam(defaultValue = "10") int pageSize,
                                          @RequestParam(required = false) String[] sort,
                                          @RequestParam(required = false) String[] search)  {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "get product page",
                productService.getPageData(pageNo, pageSize, search, sort)
        );

    }

    @GetMapping("/test-criteria")
    public ResponseSuccess<?> testCriteria(@RequestParam(defaultValue = "1") int pageNo,
                                           @RequestParam(defaultValue = "10") int pageSize,
                                           @RequestParam(required = false) String sort,
                                           @RequestParam(required = false) String... search) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "get product page",
                null
        );

    }


}
