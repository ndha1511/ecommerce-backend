package com.code.salesappbackend.dtos.responses;

import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
    private int pageNo;
    private long totalPage;
    private int totalElement;
    private T data;
}
