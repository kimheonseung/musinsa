package com.devh.project.musinsa.search.domain.common.dto;

import com.devh.project.musinsa.search.domain.common.exception.ErrorCode;
import com.devh.project.musinsa.search.domain.common.exception.RequestException;
import com.devh.project.musinsa.search.domain.common.exception.SearchException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse<T extends Throwable> {
    private final LocalDateTime timestamp;
    @JsonIgnore
    private final T throwable;
    private final ErrorCode errorCode;
    private final String exception;
    private final String message;

    private ErrorResponse(T throwable) {
        this.timestamp = LocalDateTime.now();
        this.throwable = throwable;

        if (throwable instanceof SearchException) {
            SearchException searchException = (SearchException) throwable;
            this.errorCode = searchException.getErrorCode();
        } else if (throwable instanceof RequestException) {
            RequestException requestException = (RequestException) throwable;
            this.errorCode = requestException.getErrorCode();
        }  else {
            this.errorCode = ErrorCode.UNKNOWN_ERROR;
        }

        this.exception = throwable.getClass().getSimpleName();
        this.message = throwable.getMessage();
    }

    public static <T extends Throwable> ErrorResponse<T> create(T throwable) {
        return new ErrorResponse<>(throwable);
    }
}
