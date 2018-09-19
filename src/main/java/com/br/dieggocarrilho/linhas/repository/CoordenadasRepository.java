package com.br.dieggocarrilho.linhas.repository;

import com.br.dieggocarrilho.linhas.domain.Coordenadas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoordenadasRepository extends JpaRepository<Coordenadas, Long> {
    void deleteByIdLinha(Long id);

    List<Coordenadas> findByIdLinha(Long id);

    Page<Coordenadas> findByIdLinha(Long id, Pageable pageable);
}
