package com.br.dieggocarrilho.linhas.configuracao;

import com.br.dieggocarrilho.linhas.domain.Coordenadas;
import com.br.dieggocarrilho.linhas.domain.Linhas;
import com.br.dieggocarrilho.linhas.repository.CoordenadasRepository;
import com.br.dieggocarrilho.linhas.repository.LinhasRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class PoaTransportesServiceImpl  implements  PoaTransportesService{


    private LinhasRepository linhasRepository;
    private CoordenadasRepository coordenadasRepository;

    public PoaTransportesServiceImpl(LinhasRepository linhasRepository, CoordenadasRepository coordenadasRepository) {
        this.linhasRepository = linhasRepository;
        this.coordenadasRepository = coordenadasRepository;
    }

    @Override
    public List<Linhas> findAll() {
        System.out.println("chamou");
        return linhasRepository.findAll();
    }


    @Override
    public List<Linhas> saveAll(List<Linhas> linhasAtualizarSistema) {
        return linhasRepository.saveAll(linhasAtualizarSistema);
    }

    @Override
    public void saveAllCoordenadas(List<Coordenadas> coordenadasDasLinhas) {
        coordenadasRepository.saveAll(coordenadasDasLinhas);
    }

    @Override
    public void deleteAllFromIdLinha(Long id) {
        coordenadasRepository.deleteByIdLinha(id);
    }
}
