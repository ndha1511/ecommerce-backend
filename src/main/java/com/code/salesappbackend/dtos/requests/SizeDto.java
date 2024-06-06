package com.code.salesappbackend.dtos.requests;

import com.code.salesappbackend.models.enums.SizeType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SizeDto {
    private Short numberSize;
    private String textSize;
    private SizeType sizeType;
}
