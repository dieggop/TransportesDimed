package com.br.dieggocarrilho.linhas.configuracao;

import com.br.dieggocarrilho.linhas.domain.Linhas;
import com.br.dieggocarrilho.linhas.repository.LinhasRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class PoaTransportesServiceImpl  implements  PoaTransportesService{


    private LinhasRepository linhasRepository;

    public PoaTransportesServiceImpl(LinhasRepository linhasRepository) {
        this.linhasRepository = linhasRepository;
    }

    @Override
    public List<Linhas> findAll() {
        System.out.println("chamou");
        return linhasRepository.findAll();
    }


    @Override
    public void saveAll(List<Linhas> linhasAtualizarSistema) {
        linhasRepository.saveAll(linhasAtualizarSistema);
    }
}
