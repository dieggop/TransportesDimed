package com.br.dieggocarrilho.linhas.configuracao;

import com.br.dieggocarrilho.linhas.domain.Linhas;

import java.util.List;

public interface PoaTransportesService {

    List<Linhas> findAll();

    void saveAll(List<Linhas> linhasAtualizarSistema);
}
