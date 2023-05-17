package com.devh.project.musinsa.backoffice.domain.item.dto;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
public class ItemDeleteRequest {
    @NotNull(message = "삭제할 상품 id가 잘못되었습니다.")
    @Min(value = 1, message = "삭제할 상품 id가 잘못되었습니다.")
    private long id;
}
