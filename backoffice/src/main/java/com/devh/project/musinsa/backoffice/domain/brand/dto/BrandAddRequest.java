package com.devh.project.musinsa.backoffice.domain.brand.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class BrandAddRequest {
    @NotBlank(message = "brand 명 값이 비어있습니다.")
    private String name;
}
