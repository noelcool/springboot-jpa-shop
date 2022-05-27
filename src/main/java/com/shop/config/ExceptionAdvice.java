package com.shop.config;

import com.shop.domain.dto.response.ExceptionResponse;
import com.shop.exception.NotFoundException;
import com.shop.exception.OutOfStockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ExceptionResponse> NotFoundServerException(NotFoundException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionResponse response = new ExceptionResponse(status.value(), e.getMessage());
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(OutOfStockException.class)
    protected ResponseEntity<ExceptionResponse> OutOfStockServerException(OutOfStockException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionResponse response = new ExceptionResponse(status.value(), e.getMessage());
        return ResponseEntity.status(status).body(response);
    }

}
