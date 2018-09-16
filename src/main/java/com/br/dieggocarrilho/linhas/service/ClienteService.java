package com.br.dieggocarrilho.linhas.service;

import com.br.dieggocarrilho.linhas.domain.Cliente;
import com.br.dieggocarrilho.linhas.transportesdimed.api.model.ClienteResponse;

public interface ClienteService {

    Cliente save(Cliente cliente);


    void desativarCliente();

    Cliente recuperarCliente();
}
