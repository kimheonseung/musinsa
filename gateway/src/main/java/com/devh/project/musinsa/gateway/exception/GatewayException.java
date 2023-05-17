package com.devh.project.musinsa.gateway.exception;

public class GatewayException extends RuntimeException {
    private ErrorCode errorCode;

    public GatewayException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public GatewayException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
