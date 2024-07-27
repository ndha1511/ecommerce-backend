package com.code.salesappbackend.dtos.requests.product;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ColorDto {
    @NotBlank(message = "color name must be not blank")
    private String colorName;
    @JsonCreator
    public ColorDto(String colorName) {
        this.colorName = colorName;
    }

}
