package com.br.dieggocarrilho.linhas.service;

import com.br.dieggocarrilho.linhas.domain.Coordenadas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IntinerariosService {

    List<Coordenadas> listarCoordenadas(Long id);
    Page<Coordenadas> listarCoordenadasPaginado(Long id, PageRequest pageable);

    Page<Coordenadas> listarCoordenadasPorRaioPaginado(Long idUt, Integer raio, Double lat, Double lng, PageRequest paginado);
}
