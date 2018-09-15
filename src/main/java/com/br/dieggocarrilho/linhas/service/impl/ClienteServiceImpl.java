package com.br.dieggocarrilho.linhas.service.impl;

import com.br.dieggocarrilho.linhas.domain.Cliente;
import com.br.dieggocarrilho.linhas.repository.ClienteRepository;
import com.br.dieggocarrilho.linhas.service.ClienteService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ClienteRepository clienteRepository;

    public ClienteServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, ClienteRepository clienteRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente save(Cliente user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return clienteRepository.save(user);
    }
}
