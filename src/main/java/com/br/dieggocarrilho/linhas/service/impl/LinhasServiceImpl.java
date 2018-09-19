package com.br.dieggocarrilho.linhas.service.impl;

import com.br.dieggocarrilho.linhas.domain.Linhas;
import com.br.dieggocarrilho.linhas.repository.CoordenadasRepository;
import com.br.dieggocarrilho.linhas.repository.LinhasRepository;
import com.br.dieggocarrilho.linhas.service.LinhasService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

@Service
public class LinhasServiceImpl implements LinhasService {
    private LinhasRepository linhasRepository;
    private CoordenadasRepository coordenadasRepository;

    @Inject
    public LinhasServiceImpl(LinhasRepository linhasRepository, CoordenadasRepository coordenadasRepository) {
        this.linhasRepository = linhasRepository;
        this.coordenadasRepository = coordenadasRepository;
    }
    @Override
    public Optional<Linhas> findById(Long id) {
        return linhasRepository.findById(id);
    }

    @Override
    public Page<Linhas> findByFilter(String nome, PageRequest paginado) {

        Page<Linhas> retorno = linhasRepository.findByNomeContainingIgnoreCase(nome, paginado);

        return retorno;
    }

    @Override
    public Page<Linhas> findByRaioLatLng(Integer raio, Double lat, Double lng, PageRequest paginado) {
        System.out.println("Buscar por raio, latitude e longitude - service");
        Page<Linhas> retorno = linhasRepository.findByRaioLatLng( raio,  lat,  lng,  paginado);

        return retorno;
    }

    @Override
    public Page<Linhas> findAll(PageRequest paginado) {
        return linhasRepository.findAll(paginado);
    }
}
