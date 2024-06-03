package com.code.salesappbackend.dtos.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryDto {
    @NotBlank(message = "category name must be not blank")
    private String categoryName;

    @JsonCreator
    public CategoryDto(@JsonProperty("categoryName") String categoryName) {
        this.categoryName = categoryName;

    }
}
