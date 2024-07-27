package com.code.salesappbackend.mappers.product;

import com.code.salesappbackend.dtos.requests.product.ColorDto;
import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.models.product.Color;
import com.code.salesappbackend.services.interfaces.product.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ColorMapper {
    private final ColorService colorService;
    public Color colorDto2Color(ColorDto colorDto) throws DataExistsException {
        colorService.existsByColorName(colorDto.getColorName());
        Color color = new Color();
        color.setColorName(colorDto.getColorName());
        return color;
    }
}
