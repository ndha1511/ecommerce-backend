package com.code.salesappbackend.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ResponseError implements Response {
    private int status;
    private List<String> errors;
}
