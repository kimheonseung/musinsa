package com.devh.project.musinsa.backoffice.domain.common.exception;

import lombok.Getter;

@Getter
public class ItemException extends RuntimeException {
    private final ErrorCode errorCode;

    public ItemException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ItemException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
