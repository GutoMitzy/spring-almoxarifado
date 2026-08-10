package com.br.almoxarifado.spring_almoxarifado.controller;

import com.br.almoxarifado.spring_almoxarifado.dto.LoginRequestDto;
import com.br.almoxarifado.spring_almoxarifado.dto.RegisterRequestDto;
import com.br.almoxarifado.spring_almoxarifado.dto.TokenResponseDto;
import com.br.almoxarifado.spring_almoxarifado.exception.BadRequestException;
import com.br.almoxarifado.spring_almoxarifado.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/almoxarifado/auth")
@Validated
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TokenResponseDto login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return authenticationService.login(loginRequestDto);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid RegisterRequestDto registerRequestDto) throws BadRequestException {
        authenticationService.register(registerRequestDto);
    }
}
