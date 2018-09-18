package com.br.dieggocarrilho.linhas.repository;

import com.br.dieggocarrilho.linhas.domain.Linhas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinhasRepository extends JpaRepository<Linhas, Long> {

    Page<Linhas> findByNomeContainingIgnoreCase(String nome, Pageable paginado);
}
