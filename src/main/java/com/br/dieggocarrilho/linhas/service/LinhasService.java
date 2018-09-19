package com.br.dieggocarrilho.linhas.service;

import com.br.dieggocarrilho.linhas.domain.Linhas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface LinhasService {


    Optional<Linhas> findById(Long id);

    Page<Linhas> findByFilter(String nome, PageRequest paginado);

    Page<Linhas> findByRaioLatLng(Integer raio, Double lat, Double lng, PageRequest paginado);
}
