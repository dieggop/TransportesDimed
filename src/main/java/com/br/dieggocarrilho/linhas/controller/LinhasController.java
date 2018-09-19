package com.br.dieggocarrilho.linhas.controller;

import com.br.dieggocarrilho.linhas.configuracao.PoaTransportes;
import com.br.dieggocarrilho.linhas.constantes.Mapear;
import com.br.dieggocarrilho.linhas.domain.Coordenadas;
import com.br.dieggocarrilho.linhas.domain.Linhas;
import com.br.dieggocarrilho.linhas.service.ItinerariosService;
import com.br.dieggocarrilho.linhas.service.LinhasService;
import com.br.dieggocarrilho.linhas.transportesdimed.api.LinhasApi;
import com.br.dieggocarrilho.linhas.transportesdimed.api.model.Itinerario;
import com.br.dieggocarrilho.linhas.transportesdimed.api.model.ItinerarioPaginado;
import com.br.dieggocarrilho.linhas.transportesdimed.api.model.LinhasPaginado;
import com.br.dieggocarrilho.linhas.utils.MontagemPageRequest;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

@RestController
public class LinhasController implements LinhasApi {

    LinhasService linhasService;
    ItinerariosService itinerariosService;

    @Inject
    public LinhasController(LinhasService linhasService, ItinerariosService itinerariosService) {
        this.linhasService = linhasService;
        this.itinerariosService = itinerariosService;
    }

    @Override
    public ResponseEntity<LinhasPaginado> filtrarLinhas(@RequestParam(value = "nome", required = false) String nome,
                                                        @RequestParam(value = "raio", required = false) Integer raio,
                                                        @RequestParam(value = "lat", required = false) Double lat,
                                                        @RequestParam(value = "lng", required = false) Double lng,
                                                        @RequestParam(value = "page", required = false) Integer page,
                                                        @RequestParam(value = "per_page", required = false) Integer perPage) {

        PageRequest paginado = MontagemPageRequest.getPageRequest(page, perPage);

        Page<Linhas> linhasPage = null;
        if (!StringUtils.isEmpty(nome)) {
            linhasPage = linhasService.findByFilter(nome, paginado);
        } else if (raio != null && lat != null && lng != null) {
            System.out.println("Buscar por raio, latitude e longitude");
            linhasPage = linhasService.findByRaioLatLng(raio, lat, lng, paginado);
        }
        LinhasPaginado linhasPaginado = Mapear.linhasDomainLinhasPaginadoModel(paginado, linhasPage);
        return new ResponseEntity<>(linhasPaginado, HttpStatus.OK);
    }




    @Override
    public ResponseEntity<Void> atualizarLinhas() {

        PoaTransportes.atualizarLinhasNoSistema();

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<ItinerarioPaginado> filtrarItinerariosCliente(@PathVariable(value = "idUt", required = true) Long idUt,
                                                                   @RequestParam(value = "raio", required = true) Integer raio,
                                                                   @RequestParam(value = "lat", required = true) Double lat,
                                                                   @RequestParam(value = "lng", required = true) Double lng,
                                                                   @RequestParam(value = "page", required = false) Integer page,
                                                                   @RequestParam(value = "per_page", required = false) Integer perPage) {
        PageRequest paginado = MontagemPageRequest.getPageRequest(page,perPage);

        Page<Coordenadas> coordenadas = itinerariosService.listarCoordenadasPorRaioPaginado(idUt, raio, lat, lng, paginado);

        ItinerarioPaginado itinerarioPaginado = Mapear.coordenadasDomainItinerarioPaginadoModel(paginado, coordenadas);

        return new ResponseEntity<>(itinerarioPaginado, HttpStatus.OK);

    }



}
