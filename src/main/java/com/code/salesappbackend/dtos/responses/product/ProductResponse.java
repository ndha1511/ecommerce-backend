package com.code.salesappbackend.dtos.responses.product;

import com.code.salesappbackend.models.product.Product;
import com.code.salesappbackend.models.product.ProductDetail;
import com.code.salesappbackend.models.product.ProductImage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class ProductResponse {
    private Product product;
    private List<ProductDetail> productDetails;
    private List<ProductImage> productImages;
}
