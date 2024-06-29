package com.code.salesappbackend.dtos.responses.products;

import com.code.salesappbackend.models.Product;
import com.code.salesappbackend.models.ProductDetail;
import com.code.salesappbackend.models.ProductImage;
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
