package com.devh.project.musinsa.backoffice.domain.common.exception;

import lombok.Getter;

@Getter
public class CategoryException extends RuntimeException {
    private ErrorCode errorCode;

    public CategoryException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CategoryException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
