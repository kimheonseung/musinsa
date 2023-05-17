package com.devh.project.musinsa.gateway.dto;

import com.devh.project.musinsa.gateway.exception.ErrorCode;
import com.devh.project.musinsa.gateway.exception.GatewayException;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ErrorResponse<T extends Throwable> {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS", timezone = "Asia/Seoul")
    private final LocalDateTime timestamp;
    private final T throwable;
    private final ErrorCode errorCode;
    private final String exception;
    private final String message;

    private ErrorResponse(T throwable) {
        this.timestamp = LocalDateTime.now();
        this.throwable = throwable;

        if (throwable instanceof GatewayException) {
            GatewayException gatewayException = (GatewayException) throwable;
            this.errorCode = gatewayException.getErrorCode();
        } else {
            this.errorCode = ErrorCode.UNKNOWN_ERROR;
        }

        this.exception = throwable.getClass().getSimpleName();
        this.message = throwable.getMessage();
    }

    public static <T extends Throwable> ErrorResponse<T> create(T throwable) {
        return new ErrorResponse<>(throwable);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getException() {
        return exception;
    }

    public String getMessage() {
        return message;
    }
}
