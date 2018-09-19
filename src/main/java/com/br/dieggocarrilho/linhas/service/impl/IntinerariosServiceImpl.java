package com.br.dieggocarrilho.linhas.service.impl;

import com.br.dieggocarrilho.linhas.domain.Coordenadas;
import com.br.dieggocarrilho.linhas.repository.CoordenadasRepository;
import com.br.dieggocarrilho.linhas.repository.LinhasRepository;
import com.br.dieggocarrilho.linhas.service.IntinerariosService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class IntinerariosServiceImpl implements IntinerariosService {
    private LinhasRepository linhasRepository;
    private CoordenadasRepository coordenadasRepository;

    @Inject
    public IntinerariosServiceImpl(LinhasRepository linhasRepository, CoordenadasRepository coordenadasRepository) {
        this.linhasRepository = linhasRepository;
        this.coordenadasRepository = coordenadasRepository;
    }
    @Override
    public List<Coordenadas> listarCoordenadas(Long id) {
        List<Coordenadas> byIdLinha = coordenadasRepository.findByIdLinha(id);
        return byIdLinha;
    }

    @Override
    public Page<Coordenadas> listarCoordenadasPaginado(Long id, PageRequest pageable) {
        return coordenadasRepository.findByIdLinha(id, pageable);
    }

    @Override
    public Page<Coordenadas> listarCoordenadasPorRaioPaginado(Long idUt, Integer raio, Double lat, Double lng, PageRequest paginado) {
        return coordenadasRepository.findByUtRaioLatLng(idUt, raio, lat, lng, paginado);
    }
}
