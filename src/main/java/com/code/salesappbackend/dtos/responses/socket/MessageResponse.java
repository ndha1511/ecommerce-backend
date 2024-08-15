package com.code.salesappbackend.dtos.responses.socket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse<T> {
    private T data;
    private String type;
}
