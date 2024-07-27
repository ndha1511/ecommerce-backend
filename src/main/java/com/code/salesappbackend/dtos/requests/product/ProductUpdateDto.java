package com.code.salesappbackend.dtos.requests.product;

import com.code.salesappbackend.models.enums.Status;
import com.code.salesappbackend.models.product.ProductPrice;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductUpdateDto {
    @NotBlank(message = "product name must be not blank")
    private String name;
    private String description;
    private Status status;
    @NotNull(message = "price must be not null")
    private Double price;
    @NotNull(message = "categoryId must be not null")
    private Long categoryId;
    @NotNull(message = "providerId must be not null")
    private Long providerId;
    private Integer thumbnail;
    private List<ProductDetailDto> productDetailsDelete;
    private List<ProductDetailDto> productDetailsUpdate;
    private List<String> imagesDelete;
    private List<MultipartFile> imagesInsert;
    private List<ProductPrice> productPriceDelete;
    private List<ProductPrice> productPriceUpdate;
}
