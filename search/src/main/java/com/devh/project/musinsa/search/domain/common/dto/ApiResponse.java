package com.devh.project.musinsa.search.domain.common.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ApiResponse<T> {
    private final LocalDateTime timestamp;
    private final Paging paging;
    private final List<T> data;

    private ApiResponse(List<T> data, Paging paging) {
        this.timestamp = LocalDateTime.now();
        this.paging = paging;
        this.data = data;
    }

    public static <T> ApiResponse<T> create(T data) {
        return new ApiResponse<>(List.of(data), Paging.create(1, 1, 1));
    }

    public static <T> ApiResponse<T> create(List<T> data) {
        return new ApiResponse<>(data, Paging.create(1, data.size(), data.size()));
    }

    public static <T> ApiResponse<T> create(List<T> data, Paging paging) {
        return new ApiResponse<>(data, paging);
    }
}
