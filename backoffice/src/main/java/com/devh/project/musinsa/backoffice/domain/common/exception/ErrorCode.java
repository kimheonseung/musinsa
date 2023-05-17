package com.devh.project.musinsa.backoffice.domain.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    CATEGORY_ERROR(800, "카테고리 관련 에러입니다."),
    CATEGORY_DUPLICATE_ERROR(801, "이미 존재하는 카테고리입니다."),
    CATEGORY_NOT_FOUND_ERROR(802, "대상 카테고리을 찾을 수 없습니다."),
    BRAND_ERROR(810, "브랜드 관련 에러입니다."),
    BRAND_DUPLICATE_ERROR(811, "이미 존재하는 브랜드입니다."),
    BRAND_NOT_FOUND_ERROR(812, "대상 브랜드를 찾을 수 없습니다."),
    ITEM_ERROR(820, "상품 관련 에러입니다."),
    ITEM_DUPLICATE_ERROR(821, "이미 존재하는 상품입니다."),
    ITEM_NOT_FOUND_ERROR(822, "대상 상품을 찾을 수 없습니다."),
    ITEM_UPDATE_ERROR(823, "변경할 상품 필드를 입력하지 않았습니다."),
    DATA_INTEGRITY_ERROR(830, "데이터 무결성에 위배된 요청입니다."),
    REQUEST_ERROR(900, "잘못된 요청입니다. 요청 값을 확인해주세요."),
    UNKNOWN_ERROR(555, "알 수 없는 에러입니다.");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
