package com.code.salesappbackend.dtos.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PageResponse {
    private int pageNo;
    private long totalPage;
    private int totalElement;
    private Object data;
}
