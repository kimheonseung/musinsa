package com.devh.project.musinsa.backoffice.domain.item.dto;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
public class ItemUpdateRequest {
    @NotNull(message = "변경할 상품 id를 입력하지 않았습니다.")
    @Min(value = 1, message = "변경할 상품 id가 잘못되었습니다.")
    private long id;
    private String brandName;
    private String categoryName;
    private String itemName;
    private Long price;
}
