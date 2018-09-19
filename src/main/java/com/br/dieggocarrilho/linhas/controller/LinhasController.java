package com.br.dieggocarrilho.linhas.controller;

import com.br.dieggocarrilho.linhas.configuracao.PoaTransportes;
import com.br.dieggocarrilho.linhas.domain.Linhas;
import com.br.dieggocarrilho.linhas.service.LinhasService;
import com.br.dieggocarrilho.linhas.transportesdimed.api.LinhasApi;
import com.br.dieggocarrilho.linhas.transportesdimed.api.model.IntinerarioPaginado;
import com.br.dieggocarrilho.linhas.transportesdimed.api.model.LinhasPaginado;
import com.br.dieggocarrilho.linhas.utils.MontagemPageRequest;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

@RestController
public class LinhasController implements LinhasApi {

    LinhasService linhasService;

    public LinhasController(LinhasService linhasService) {
        this.linhasService = linhasService;
    }

    @Override
    public ResponseEntity<LinhasPaginado> filtrarLinhas(@RequestParam(value = "nome", required = false) String nome,
                                                        @RequestParam(value = "raio", required = false) Integer raio,
                                                        @RequestParam(value = "lat", required = false) Double lat,
                                                        @RequestParam(value = "lng", required = false) Double lng,
                                                        @RequestParam(value = "page", required = false) Integer page,
                                                        @RequestParam(value = "per_page", required = false) Integer perPage) {

        PageRequest paginado = MontagemPageRequest.getPageRequest(page, perPage);

        Page<Linhas> linhasPage = linhasService.findByFilter(nome, paginado);

        LinhasPaginado linhasPaginado = new LinhasPaginado();
        linhasPaginado.setPage(Integer.toUnsignedLong(paginado.getPageNumber()+1));
        linhasPaginado.setPerPage(Integer.toUnsignedLong(paginado.getPageSize()));
        linhasPaginado.total(linhasPage.getTotalElements());
        linhasPaginado.pages(new Long(linhasPage.getTotalPages()));
        linhasPaginado.setLinhas(linhasPage.stream().map(linhas -> {
            com.br.dieggocarrilho.linhas.transportesdimed.api.model.Linhas l = new com.br.dieggocarrilho.linhas.transportesdimed.api.model.Linhas();
            l.setNome(linhas.getNome());
            l.setId(linhas.getId());
            l.setCodigo(linhas.getCodigo());
            return l;
        }).collect(Collectors.toList()));
        return new ResponseEntity<>(linhasPaginado, HttpStatus.OK);
    }



    @Override
    public ResponseEntity<Void> atualizarLinhas() {

        PoaTransportes.atualizarLinhasNoSistema();

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<IntinerarioPaginado> filtrarIntinerarios(Long idUt, @NotNull Integer raio, @NotNull Double lat, @NotNull Double lng, Integer page, Integer perPage) {
        return null;
    }



}
