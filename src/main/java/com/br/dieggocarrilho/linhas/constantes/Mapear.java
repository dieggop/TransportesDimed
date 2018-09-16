package com.br.dieggocarrilho.linhas.constantes;

import com.br.dieggocarrilho.linhas.transportesdimed.api.model.Cliente;
import com.br.dieggocarrilho.linhas.transportesdimed.api.model.ClienteResponse;

public class Mapear {

    public static com.br.dieggocarrilho.linhas.domain.Cliente clienteModelClienteDomain(Cliente model) {
        com.br.dieggocarrilho.linhas.domain.Cliente cliente = new com.br.dieggocarrilho.linhas.domain.Cliente();
        cliente.setEmail(model.getEmail());
        cliente.setName(model.getName());
        cliente.setPassword(model.getPassword());
        cliente.setUsername(model.getUsername());
        cliente.setContato(model.getContato());
        cliente.setId(model.getId());
        return cliente;
    }


    public static ClienteResponse clienteDomainClienteResponseModel(com.br.dieggocarrilho.linhas.domain.Cliente domain) {
        ClienteResponse clienteResponse = new ClienteResponse();
        clienteResponse.setEmail(domain.getEmail());
        clienteResponse.setId(domain.getId());
        clienteResponse.setUsername(domain.getUsername());
        clienteResponse.setName(domain.getName());
        clienteResponse.setContato(domain.getContato());

        return clienteResponse;
    }
}
