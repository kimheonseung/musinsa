package com.devh.project.musinsa.backoffice.domain.common.advice;

import com.devh.project.musinsa.backoffice.domain.common.dto.ErrorResponse;
import com.devh.project.musinsa.backoffice.domain.common.exception.BrandException;
import com.devh.project.musinsa.backoffice.domain.common.exception.CategoryException;
import com.devh.project.musinsa.backoffice.domain.common.exception.ErrorCode;
import com.devh.project.musinsa.backoffice.domain.common.exception.ItemException;
import com.devh.project.musinsa.backoffice.domain.common.exception.RequestException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ErrorResponse<RequestException> handleRequestError(Exception e) {
        return ErrorResponse.create(new RequestException(ErrorCode.REQUEST_ERROR, e.getMessage()));
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ErrorResponse<RequestException> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ErrorResponse.create(new RequestException(ErrorCode.REQUEST_ERROR, e.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorResponse<RequestException> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors =  e.getBindingResult().getFieldErrors();
        FieldError fieldError = fieldErrors.get(fieldErrors.size() - 1);
        return ErrorResponse.create(new RequestException(ErrorCode.REQUEST_ERROR, fieldError.getDefaultMessage()));
    }

    @ExceptionHandler({BrandException.class})
    public ErrorResponse<BrandException> handleBrandError(BrandException e) {
        return ErrorResponse.create(e);
    }

    @ExceptionHandler({CategoryException.class})
    public ErrorResponse<CategoryException> handleCategoryError(CategoryException e) {
        return ErrorResponse.create(e);
    }

    @ExceptionHandler({ItemException.class})
    public ErrorResponse<ItemException> handleItemError(ItemException e) {
        return ErrorResponse.create(e);
    }

    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class})
    public ErrorResponse<ItemException> handleDataIntegrityViolationError(Exception e) {
        return ErrorResponse.create(new ItemException(ErrorCode.DATA_INTEGRITY_ERROR));
    }
}
