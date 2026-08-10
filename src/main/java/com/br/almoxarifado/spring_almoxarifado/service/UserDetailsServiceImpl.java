package com.br.almoxarifado.spring_almoxarifado.service;

import com.br.almoxarifado.spring_almoxarifado.database.repository.IEmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final IEmpresaRepository empresaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return empresaRepository.findByNome(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
    }
}
