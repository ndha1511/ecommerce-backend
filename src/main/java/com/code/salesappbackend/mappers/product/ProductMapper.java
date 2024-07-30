package com.code.salesappbackend.mappers.product;


import com.code.salesappbackend.dtos.requests.product.ProductDto;
import com.code.salesappbackend.dtos.requests.product.ProductUpdateDto;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.models.product.Category;
import com.code.salesappbackend.models.product.Product;
import com.code.salesappbackend.models.product.Provider;
import com.code.salesappbackend.models.enums.Status;
import com.code.salesappbackend.services.interfaces.product.CategoryService;
import com.code.salesappbackend.services.interfaces.product.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final CategoryService categoryService;
    private final ProviderService providerService;

    public Product productDto2Product(ProductDto productDto) throws DataNotFoundException {
        Category category = categoryService.findById(productDto.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("category not found"));
        Provider provider = providerService.findById(productDto.getProviderId())
                .orElseThrow(() -> new DataNotFoundException("provider not found"));
        return Product.builder()
                .productName(productDto.getProductName())
                .description(productDto.getDescription())
                .productStatus(Status.ACTIVE)
                .category(category)
                .provider(provider)
                .price(productDto.getPrice())
                .build();
    }

    public Product productDto2Product(ProductUpdateDto productDto) throws DataNotFoundException {
        Category category = categoryService.findById(productDto.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("category not found"));
        Provider provider = providerService.findById(productDto.getProviderId())
                .orElseThrow(() -> new DataNotFoundException("provider not found"));
        return Product.builder()
                .productName(productDto.getProductName())
                .description(productDto.getDescription())
                .productStatus(productDto.getStatus())
                .category(category)
                .provider(provider)
                .thumbnail(productDto.getThumbnail())
                .price(productDto.getPrice())
                .build();
    }
}
