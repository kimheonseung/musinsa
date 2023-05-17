package com.devh.project.musinsa.backoffice.domain.brand.dto;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class BrandUpdateRequest {
    @NotNull(message = "잘못된 브랜드 id 입니다.")
    @Min(value = 1, message = "잘못된 브랜드 id 입니다.")
    private Long id;
    @NotBlank(message = "brand 명 값이 비어있습니다.")
    private String name;
}
