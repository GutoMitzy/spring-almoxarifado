package com.br.almoxarifado.almoxarifado.service;

import com.br.almoxarifado.almoxarifado.config.TokenProvider;
import com.br.almoxarifado.almoxarifado.database.model.EmpresaModel;
import com.br.almoxarifado.almoxarifado.database.model.RoleModel;
import com.br.almoxarifado.almoxarifado.database.repository.IEmpresaRepository;
import com.br.almoxarifado.almoxarifado.database.repository.IRolesRepository;
import com.br.almoxarifado.almoxarifado.dto.LoginRequestDto;
import com.br.almoxarifado.almoxarifado.dto.RegisterRequestDto;
import com.br.almoxarifado.almoxarifado.dto.TokenResponseDto;
import com.br.almoxarifado.almoxarifado.enums.RolesEnum;
import com.br.almoxarifado.almoxarifado.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final IRolesRepository rolesRepository;
    private final IEmpresaRepository empresaRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public void registerAccount(RegisterRequestDto dto) throws BadRequestException {
        EmpresaModel empresa = empresaRepository.findByNome(dto.getNome())
                .orElse(null);

        if(empresa != null){
            throw new BadRequestException("Não foi possível cadastrar a empresa!");
        }

        RoleModel role = rolesRepository.findByNome(RolesEnum.ROLE_CLIENTE.name())
                .orElseGet(() -> rolesRepository.save(RoleModel.builder()
                        .nome(RolesEnum.ROLE_CLIENTE.name())
                        .build()
                ));

        empresaRepository.save(EmpresaModel.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .endereco(dto.getEndereco())
                .telefone(dto.getTelefone())
                .tipo(dto.getTipo())
                .roles(Set.of(role))
                .senha(passwordEncoder.encode(dto.getSenha()))
                .build());
    }

    public TokenResponseDto loginAccount(LoginRequestDto loginRequestDto) throws BadRequestException {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken((loginRequestDto.getNome()), loginRequestDto.getSenha()));
            String token = tokenProvider.gerarToken(authentication);
            return new TokenResponseDto(token, expirationTime);
        } catch(BadCredentialsException bce)  {
            throw new BadRequestException("Credenciais inválidas!");
        } catch(Exception e) {
            throw e;
        }
    }
}
