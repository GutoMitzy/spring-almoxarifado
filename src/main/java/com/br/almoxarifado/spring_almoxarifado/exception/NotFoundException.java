package com.br.almoxarifado.spring_almoxarifado.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String mensagem) { super(mensagem); }
}
