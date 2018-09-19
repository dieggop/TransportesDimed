package com.br.dieggocarrilho.linhas.repository;

import com.br.dieggocarrilho.linhas.domain.Cliente;
import com.br.dieggocarrilho.linhas.domain.ClienteLinha;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteLinhaRepository  extends JpaRepository<ClienteLinha, Long> {
    Page<ClienteLinha> findByCliente(Cliente retorno, Pageable paginado);

    Optional<ClienteLinha> findByClienteAndLinhasId(Cliente retorno, Long idUt);

    Optional<ClienteLinha> findByClienteIdAndLinhasId(Long id, Long idUt);
}
