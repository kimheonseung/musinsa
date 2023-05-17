package com.devh.project.musinsa.aggregator.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    AGGREGATION_ERROR(840, "집계 데이터 처리 관련 에러입니다.");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
