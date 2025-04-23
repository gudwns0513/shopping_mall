package com.shop.shoppingmall.global.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class SliceResponse<T> {

    private final List<T> content;

    private final int currentPage;

    private final int pageSize;

    private final boolean hasNextPage;

}
