package com.devh.project.musinsa.search.domain.common.exception;

import lombok.Getter;

@Getter
public class RequestException extends RuntimeException {
    private final ErrorCode errorCode;

    public RequestException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public RequestException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
