package com.br.dieggocarrilho.linhas.controller;

import com.br.dieggocarrilho.linhas.configuracao.PoaTransportes;
import com.br.dieggocarrilho.linhas.domain.Coordenadas;
import com.br.dieggocarrilho.linhas.domain.Linhas;
import com.br.dieggocarrilho.linhas.exceptions.Message;
import com.br.dieggocarrilho.linhas.service.IntinerariosService;
import com.br.dieggocarrilho.linhas.service.LinhasService;
import com.br.dieggocarrilho.linhas.transportesdimed.api.CoordenadasApi;
import com.br.dieggocarrilho.linhas.transportesdimed.api.model.Intinerario;
import com.br.dieggocarrilho.linhas.transportesdimed.api.model.IntinerarioPaginado;
import com.br.dieggocarrilho.linhas.utils.MontagemPageRequest;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class IntinerariosController implements CoordenadasApi {
    LinhasService linhasService;
    IntinerariosService intinerariosService;

    public IntinerariosController(LinhasService linhasService, IntinerariosService intinerariosService) {
        this.intinerariosService = intinerariosService;
        this.linhasService = linhasService;
    }

    @Override
    public ResponseEntity<IntinerarioPaginado> filtrarIntinerarios(@NotNull @RequestParam(value = "idUt", required = true) Long idUt,  @RequestParam(value = "page", required = false) Integer page,   @RequestParam(value = "per_page", required = false) Integer perPage) {
        Optional<Linhas> byId = linhasService.findById(idUt);

        if (!byId.isPresent()) {
            return new ResponseEntity(new Message("Não existe esta linha"), HttpStatus.NOT_FOUND);
        }
        PageRequest paginado = MontagemPageRequest.getPageRequest(page,perPage);

        Page<Coordenadas> coordenadas = intinerariosService.listarCoordenadasPaginado(byId.get().getId(), paginado);

        IntinerarioPaginado intinerarioPaginado = getIntinerarioPaginado(paginado, coordenadas);

        return new ResponseEntity<IntinerarioPaginado>(intinerarioPaginado, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<IntinerarioPaginado> baixarIntinerarios(@NotNull Long idUt) {
        System.out.println("entrou aqui");
        Optional<Linhas> byId = linhasService.findById(idUt);

        if (!byId.isPresent()) {
            return new ResponseEntity(new Message("Não existe esta linha"), HttpStatus.NOT_FOUND);
        }
        System.out.println(byId.toString());
        PoaTransportes.baixarAtualizarIntinerariosDaLinhaNoSistema(byId.get().getId());

        PageRequest paginado = MontagemPageRequest.getPageRequest(1,200);

        Page<Coordenadas> coordenadas = intinerariosService.listarCoordenadasPaginado(byId.get().getId(), paginado);

        IntinerarioPaginado intinerarioPaginado = getIntinerarioPaginado(paginado, coordenadas);

        return new ResponseEntity<IntinerarioPaginado>(intinerarioPaginado, HttpStatus.OK);
    }


    private IntinerarioPaginado getIntinerarioPaginado(PageRequest paginado, Page<Coordenadas> coordenadas) {
        IntinerarioPaginado intinerarioPaginado = new IntinerarioPaginado();
        intinerarioPaginado.setPage(Integer.toUnsignedLong(paginado.getPageNumber() + 1));
        intinerarioPaginado.setPerPage(Integer.toUnsignedLong(paginado.getPageSize()));
        intinerarioPaginado.total(coordenadas.getTotalElements());
        intinerarioPaginado.pages(new Long(coordenadas.getTotalPages()));
        intinerarioPaginado.setIntinerarios(
                coordenadas.getContent().stream().map(coordenadas1 -> {
                    Intinerario i = new Intinerario();
                    i.setIdlinha(coordenadas1.getIdLinha());
                    i.setLat(coordenadas1.getLat());
                    i.setLng(coordenadas1.getLng());
                    return i;
                }).collect(Collectors.toList()));
        return intinerarioPaginado;
    }
}
