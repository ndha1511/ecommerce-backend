package com.code.salesappbackend.mappers.product;

import com.code.salesappbackend.dtos.requests.product.ProductDetailDto;
import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.models.product.Color;
import com.code.salesappbackend.models.product.Product;
import com.code.salesappbackend.models.product.ProductDetail;
import com.code.salesappbackend.models.product.Size;
import com.code.salesappbackend.services.interfaces.product.ColorService;
import com.code.salesappbackend.services.interfaces.product.ProductDetailService;
import com.code.salesappbackend.services.interfaces.product.ProductService;
import com.code.salesappbackend.services.interfaces.product.SizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductDetailMapper {
    private final ProductService productService;
    private final ColorService colorService;
    private final SizeService sizeService;
    private final ProductDetailService productDetailService;

    public ProductDetail productDetailDto2ProductDetail(ProductDetailDto productDetailDto) throws DataNotFoundException, DataExistsException {
        Product product = productService.findById(productDetailDto.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        Size size = sizeService.findById(productDetailDto.getSizeId())
                .orElseThrow(() -> new DataNotFoundException("Size not found"));
        Color color = colorService.findById(productDetailDto.getColorId())
                .orElseThrow(() -> new DataNotFoundException("Color not found"));
        productDetailService.existByAll(color, size, product);
        return  ProductDetail.builder()
                .size(size)
                .product(product)
                .color(color)
                .quantity(productDetailDto.getQuantity())
                .build();
    }
}
