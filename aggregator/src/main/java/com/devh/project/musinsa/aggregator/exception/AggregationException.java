package com.devh.project.musinsa.aggregator.exception;

import lombok.Getter;

@Getter
public class AggregationException extends RuntimeException {
    private ErrorCode errorCode;

    public AggregationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public AggregationException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
