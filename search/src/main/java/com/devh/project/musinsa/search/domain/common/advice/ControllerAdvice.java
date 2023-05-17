package com.devh.project.musinsa.search.domain.common.advice;

import com.devh.project.musinsa.search.domain.common.dto.ErrorResponse;
import com.devh.project.musinsa.search.domain.common.exception.ErrorCode;
import com.devh.project.musinsa.search.domain.common.exception.RequestException;
import com.devh.project.musinsa.search.domain.common.exception.SearchException;
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

    @ExceptionHandler({SearchException.class})
    public ErrorResponse<SearchException> handleSearchException(SearchException e) {
        return ErrorResponse.create(e);
    }
}
