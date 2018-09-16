package com.br.dieggocarrilho.linhas.service;

import com.br.dieggocarrilho.linhas.domain.Cliente;

public interface ClienteService {

    Cliente save(Cliente cliente);


    void desativarCliente();
}
