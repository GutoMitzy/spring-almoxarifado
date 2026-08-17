package com.br.almoxarifado.almoxarifado.controller;

import com.br.almoxarifado.almoxarifado.dto.LoginRequestDto;
import com.br.almoxarifado.almoxarifado.dto.RegisterRequestDto;
import com.br.almoxarifado.almoxarifado.dto.TokenResponseDto;
import com.br.almoxarifado.almoxarifado.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/almoxarifado/auth")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        authenticationService.registerAccount(registerRequestDto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponseDto loginAccount(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return authenticationService.loginAccount(loginRequestDto);
    }

    @PatchMapping("/role/{empresa_id}")
    @ResponseStatus(HttpStatus.OK)
    public void changeEmpresaRole(@PathVariable Integer empresa_id,
                                  @Valid @RequestBody String role) {
        authenticationService.changeEmpresaRole(empresa_id, role);
    }
}
