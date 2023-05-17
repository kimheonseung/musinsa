package com.devh.project.musinsa.search.domain.common.exception;

import lombok.Getter;

@Getter
public class SearchException extends RuntimeException {
    private final ErrorCode errorCode;

    public SearchException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public SearchException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
