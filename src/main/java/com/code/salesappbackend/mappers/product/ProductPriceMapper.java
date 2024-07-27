package com.code.salesappbackend.mappers.product;

import com.code.salesappbackend.dtos.requests.product.ProductPriceDto;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.models.product.Product;
import com.code.salesappbackend.models.product.ProductPrice;
import com.code.salesappbackend.services.interfaces.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductPriceMapper {
    private final ProductService productService;

    public ProductPrice productPriceDto2ProductPrice(ProductPriceDto productPriceDto)
            throws DataNotFoundException {
        Product product = productService.findById(productPriceDto.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        Double discountedPrice = product.getPrice() * productPriceDto.getDiscount();
        Double discountedAmount = product.getPrice() - discountedPrice;
        return ProductPrice.builder()
                .product(product)
                .note(productPriceDto.getNote())
                .expiredDate(productPriceDto.getExpiredDate())
                .discount(productPriceDto.getDiscount())
                .discountedPrice(discountedPrice)
                .discountedAmount(discountedAmount)
                .build();
    }
}
