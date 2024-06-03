package com.code.salesappbackend.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * DTO for {@link com.code.salesappbackend.models.Product}
 */
@Getter
@Builder
public class ProductDto {
    @NotBlank(message = "product name must be not blank")
    private String productName;
    @NotNull(message = "price must be not null")
    private Double price;
    private String description;
    private String thumbnail;
    @NotNull(message = "category must be not null")
    private Long categoryId;
    @NotNull(message = "provider must be not null")
    private Long providerId;
    private List<MultipartFile> images;

}