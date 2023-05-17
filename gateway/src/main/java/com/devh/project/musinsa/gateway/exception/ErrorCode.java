package com.devh.project.musinsa.gateway.exception;

public enum ErrorCode {

    GATEWAY_ERROR(999, "요청 자원에 도달하지 못했습니다."),
    UNKNOWN_ERROR(555, "알 수 없는 에러입니다.");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
