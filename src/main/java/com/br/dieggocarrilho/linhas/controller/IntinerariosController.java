package com.br.dieggocarrilho.linhas.controller;

import com.br.dieggocarrilho.linhas.configuracao.PoaTransportes;
import com.br.dieggocarrilho.linhas.domain.Coordenadas;
import com.br.dieggocarrilho.linhas.domain.Linhas;
import com.br.dieggocarrilho.linhas.exceptions.Message;
import com.br.dieggocarrilho.linhas.service.IntinerariosService;
import com.br.dieggocarrilho.linhas.service.LinhasService;
import com.br.dieggocarrilho.linhas.transportesdimed.api.IntinerariosApi;
import com.br.dieggocarrilho.linhas.transportesdimed.api.model.IntinerarioPaginado;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
public class IntinerariosController implements IntinerariosApi {
    LinhasService linhasService;
    IntinerariosService intinerariosService;

    public IntinerariosController(LinhasService linhasService, IntinerariosService intinerariosService) {
        this.intinerariosService = intinerariosService;
        this.linhasService = linhasService;
    }

    @Override
    public ResponseEntity<IntinerarioPaginado> filtrarIntinerarios(@NotNull @RequestParam(value = "idUt", required = true) Long idUt,  @RequestParam(value = "page", required = false) Integer page,   @RequestParam(value = "per_page", required = false) Integer perPage) {
        List<Coordenadas> coordenadas = intinerariosService.listarCoordenadas(idUt);
        System.out.println(idUt);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
