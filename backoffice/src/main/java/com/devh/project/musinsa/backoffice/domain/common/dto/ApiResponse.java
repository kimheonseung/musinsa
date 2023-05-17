package com.devh.project.musinsa.backoffice.domain.common.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ApiResponse<T> {
    private final List<T> data;

    private ApiResponse(List<T> data) {
        this.data = data;
    }

    public static <T> ApiResponse<T> create(T data) {
        return new ApiResponse<>(List.of(data));
    }

    public static <T> ApiResponse<T> create(List<T> data) {
        return new ApiResponse<>(data);
    }
}
