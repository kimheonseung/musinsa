package com.devh.project.musinsa.search.domain.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    SEARCH_PRICE_NOT_FOUND_ERROR(830, "관련 집계 데이터가 존재하지 않습니다. 잠시 후 다시 시도해주세요."),
    REQUEST_ERROR(900, "잘못된 요청입니다. 요청 값을 확인해주세요."),
    UNKNOWN_ERROR(555, "알 수 없는 에러입니다.");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
