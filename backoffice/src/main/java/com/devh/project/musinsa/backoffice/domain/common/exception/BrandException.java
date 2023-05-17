package com.devh.project.musinsa.backoffice.domain.common.exception;

import lombok.Getter;

@Getter
public class BrandException extends RuntimeException {
    private final ErrorCode errorCode;

    public BrandException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    public BrandException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
