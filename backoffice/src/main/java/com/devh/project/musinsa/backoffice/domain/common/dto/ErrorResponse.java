package com.devh.project.musinsa.backoffice.domain.common.dto;

import com.devh.project.musinsa.backoffice.domain.common.exception.BrandException;
import com.devh.project.musinsa.backoffice.domain.common.exception.CategoryException;
import com.devh.project.musinsa.backoffice.domain.common.exception.ErrorCode;
import com.devh.project.musinsa.backoffice.domain.common.exception.ItemException;
import com.devh.project.musinsa.backoffice.domain.common.exception.RequestException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.dao.DataIntegrityViolationException;

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

        if (throwable instanceof BrandException) {
            BrandException brandException = (BrandException) throwable;
            this.errorCode = brandException.getErrorCode();
        } else if (throwable instanceof CategoryException) {
            CategoryException categoryException = (CategoryException) throwable;
            this.errorCode = categoryException.getErrorCode();
        } else if (throwable instanceof ItemException) {
            ItemException itemException = (ItemException) throwable;
            this.errorCode = itemException.getErrorCode();
        } else if (throwable instanceof DataIntegrityViolationException) {
            this.errorCode = ErrorCode.DATA_INTEGRITY_ERROR;
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
