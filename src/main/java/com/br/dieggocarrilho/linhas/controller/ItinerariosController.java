package com.br.dieggocarrilho.linhas.controller;

import com.br.dieggocarrilho.linhas.configuracao.PoaTransportes;
import com.br.dieggocarrilho.linhas.constantes.Mapear;
import com.br.dieggocarrilho.linhas.domain.Coordenadas;
import com.br.dieggocarrilho.linhas.domain.Linhas;
import com.br.dieggocarrilho.linhas.exceptions.Message;
import com.br.dieggocarrilho.linhas.service.ItinerariosService;
import com.br.dieggocarrilho.linhas.service.LinhasService;
import com.br.dieggocarrilho.linhas.transportesdimed.api.CoordenadasApi;
import com.br.dieggocarrilho.linhas.transportesdimed.api.model.ItinerarioPaginado;
import com.br.dieggocarrilho.linhas.utils.MontagemPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
public class ItinerariosController implements CoordenadasApi {
    LinhasService linhasService;
    ItinerariosService itinerariosService;

    @Inject
    public ItinerariosController(LinhasService linhasService, ItinerariosService itinerariosService) {
        this.itinerariosService = itinerariosService;
        this.linhasService = linhasService;
    }

    @Override
    public ResponseEntity<ItinerarioPaginado> filtrarItinerarios(@NotNull @RequestParam(value = "idUt", required = true) Long idUt,  @RequestParam(value = "page", required = false) Integer page,   @RequestParam(value = "per_page", required = false) Integer perPage) {
        Optional<Linhas> byId = linhasService.findById(idUt);

        if (!byId.isPresent()) {
            return new ResponseEntity(new Message("Não existe esta linha"), HttpStatus.NOT_FOUND);
        }
        PageRequest paginado = MontagemPageRequest.getPageRequest(page,perPage);

        Page<Coordenadas> coordenadas = itinerariosService.listarCoordenadasPaginado(byId.get().getId(), paginado);

        ItinerarioPaginado itinerarioPaginado = Mapear.coordenadasDomainItinerarioPaginadoModel(paginado, coordenadas);

        return new ResponseEntity<ItinerarioPaginado>(itinerarioPaginado, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<ItinerarioPaginado> baixarItinerarios(@NotNull Long idUt) {
        System.out.println("entrou aqui");
        Optional<Linhas> byId = linhasService.findById(idUt);

        if (!byId.isPresent()) {
            return new ResponseEntity(new Message("Não existe esta linha"), HttpStatus.NOT_FOUND);
        }
        System.out.println(byId.toString());
        PoaTransportes.baixarAtualizarItinerariosDaLinhaNoSistema(byId.get().getId());

        PageRequest paginado = MontagemPageRequest.getPageRequest(1,200);

        Page<Coordenadas> coordenadas = itinerariosService.listarCoordenadasPaginado(byId.get().getId(), paginado);

        ItinerarioPaginado itinerarioPaginado = Mapear.coordenadasDomainItinerarioPaginadoModel(paginado, coordenadas);

        return new ResponseEntity<ItinerarioPaginado>(itinerarioPaginado, HttpStatus.OK);
    }


}
