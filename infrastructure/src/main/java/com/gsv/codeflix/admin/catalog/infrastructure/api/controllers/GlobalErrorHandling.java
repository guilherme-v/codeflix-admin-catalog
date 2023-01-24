package com.gsv.codeflix.admin.catalog.infrastructure.api.controllers;

import com.gsv.codeflix.admin.catalog.domain.exceptions.DomainException;
import com.gsv.codeflix.admin.catalog.domain.exceptions.NotFoundException;
import com.gsv.codeflix.admin.catalog.domain.validation.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalErrorHandling {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(final NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError.from(ex));
    }

    @ExceptionHandler(value = DomainException.class)
    ResponseEntity<?> handleDomainException(DomainException e) {
        return ResponseEntity.unprocessableEntity().body(ApiError.from(e));
    }

    record ApiError(String message, List<Error> errors) {
        static ApiError from(DomainException e) {
            return new ApiError(e.getMessage(), e.getErrors());
        }
    }
}
