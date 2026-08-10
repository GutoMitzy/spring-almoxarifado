package com.br.almoxarifado.spring_almoxarifado.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
