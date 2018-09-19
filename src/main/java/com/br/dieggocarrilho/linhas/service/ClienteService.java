package com.br.dieggocarrilho.linhas.service;

import com.br.dieggocarrilho.linhas.domain.Cliente;
import com.br.dieggocarrilho.linhas.domain.ClienteLinha;
import com.br.dieggocarrilho.linhas.transportesdimed.api.model.Linhas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface ClienteService {

    Cliente save(Cliente cliente);


    void desativarCliente();

    Cliente recuperarCliente();

    Page<ClienteLinha> listarLinhasDoCliente(PageRequest paginado);

    void removerLinhaCliente(Long idUt);

    void cadastrarLinhaCliente(com.br.dieggocarrilho.linhas.domain.Linhas byId);
}
