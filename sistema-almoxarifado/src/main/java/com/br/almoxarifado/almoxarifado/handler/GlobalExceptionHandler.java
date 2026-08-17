package com.br.almoxarifado.almoxarifado.handler;

import com.br.almoxarifado.almoxarifado.exception.BadRequestException;
import com.br.almoxarifado.almoxarifado.exception.ErrorResponse;
import com.br.almoxarifado.almoxarifado.exception.EstoqueCheioException;
import com.br.almoxarifado.almoxarifado.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler (NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        ErrorResponse response = ErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler (BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException bre) {
        ErrorResponse response = ErrorResponse.builder()
                .message(bre.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler (EstoqueCheioException.class)
    public ResponseEntity<ErrorResponse> handleEstoqueCheioException(EstoqueCheioException ece) {
        ErrorResponse response = ErrorResponse.builder()
                .message(ece.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
