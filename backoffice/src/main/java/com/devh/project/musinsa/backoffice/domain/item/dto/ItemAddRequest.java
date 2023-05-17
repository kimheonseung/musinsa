package com.devh.project.musinsa.backoffice.domain.item.dto;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class ItemAddRequest {
    @NotBlank(message = "brand 명이 비어있습니다.")
    private String brandName;
    @NotBlank(message = "category 명이 비어있습니다.")
    private String categoryName;
    @NotBlank(message = "상품 명이 비어있습니다.")
    private String itemName;
    @NotNull(message = "잘못된 가격입니다.")
    @Min(value = 1, message = "잘못된 가격입니다.")
    private Long price;
}
