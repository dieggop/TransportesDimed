package com.br.dieggocarrilho.linhas.configuracao;

import com.br.dieggocarrilho.linhas.domain.Coordenadas;
import com.br.dieggocarrilho.linhas.domain.Linhas;

import java.util.List;

public interface PoaTransportesService {

    List<Linhas> findAll();

    List<Linhas> saveAll(List<Linhas> linhasAtualizarSistema);

    void saveAllCoordenadas(List<Coordenadas> coordenadasDasLinhas);

    void deleteAllFromIdLinha(Long id);
}
