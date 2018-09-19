package com.br.dieggocarrilho.linhas.controller;

import com.br.dieggocarrilho.linhas.constantes.Mapear;
import com.br.dieggocarrilho.linhas.domain.ClienteLinha;
import com.br.dieggocarrilho.linhas.exceptions.ExceptionConflict;
import com.br.dieggocarrilho.linhas.exceptions.ExceptionNotFound;
import com.br.dieggocarrilho.linhas.exceptions.Message;
import com.br.dieggocarrilho.linhas.service.ClienteService;
import com.br.dieggocarrilho.linhas.service.LinhasService;
import com.br.dieggocarrilho.linhas.transportesdimed.api.ClienteApi;
import com.br.dieggocarrilho.linhas.transportesdimed.api.model.Cliente;
import com.br.dieggocarrilho.linhas.transportesdimed.api.model.ClienteResponse;
import com.br.dieggocarrilho.linhas.transportesdimed.api.model.Linhas;
import com.br.dieggocarrilho.linhas.transportesdimed.api.model.LinhasPaginado;
import com.br.dieggocarrilho.linhas.utils.MontagemPageRequest;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController implements ClienteApi {

    private ClienteService clienteService;
    private LinhasService linhasService;

    public UserController(ClienteService clienteService,LinhasService linhasService) {
        this.clienteService = clienteService;
        this.linhasService = linhasService;
    }

    @Override
    public ResponseEntity<ClienteResponse> createCliente(@Valid @RequestBody Cliente user) {
        com.br.dieggocarrilho.linhas.domain.Cliente retorno = null;
        try {
            retorno = clienteService.save(Mapear.clienteModelClienteDomain(user));
        } catch (ExceptionNotFound | ExceptionConflict e) {
            return new ResponseEntity(new Message(e.getMessage()), HttpStatus.CONFLICT);
        }

        ClienteResponse clienteResponse = Mapear.clienteDomainClienteResponseModel(retorno);
        return new ResponseEntity<>(clienteResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteCliente() {

        try {
            clienteService.desativarCliente();
        } catch (ExceptionConflict | ExceptionNotFound e) {
            return new ResponseEntity(new Message(e.getMessage()), HttpStatus.CONFLICT);

        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClienteResponse> updateCliente(@PathVariable("id") Long id, @Valid @RequestBody Cliente body) {
        com.br.dieggocarrilho.linhas.domain.Cliente retorno = null;
        if (id != body.getId()) {
            return new ResponseEntity(new Message("Erro de Payload"), HttpStatus.BAD_REQUEST);
        }


        try {
            retorno = clienteService.save(Mapear.clienteModelClienteDomain(body));
        } catch (ExceptionNotFound | ExceptionConflict e) {
            return new ResponseEntity(new Message(e.getMessage()), HttpStatus.CONFLICT);
        }

        ClienteResponse clienteResponse = Mapear.clienteDomainClienteResponseModel(retorno);

        return new ResponseEntity<>(clienteResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClienteResponse> recuperarCliente() {
        try {
            ClienteResponse clienteResponse = Mapear.clienteDomainClienteResponseModel(clienteService.recuperarCliente());

            return new ResponseEntity<>(clienteResponse, HttpStatus.OK);

        } catch (ExceptionNotFound e) {
            return new ResponseEntity(new Message(e.getMessage()), HttpStatus.NOT_FOUND);

        }
    }

    @Override
    public ResponseEntity<LinhasPaginado> clienteLinhas(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "per_page", required = false) Integer perPage) {
        PageRequest paginado = MontagemPageRequest.getPageRequest(page, perPage);

        Page<ClienteLinha> clienteLinhas = clienteService.listarLinhasDoCliente(paginado);

        LinhasPaginado linhasPaginado = new LinhasPaginado();
        linhasPaginado.setPage(Integer.toUnsignedLong(paginado.getPageNumber()+1));
        linhasPaginado.setPerPage(Integer.toUnsignedLong(paginado.getPageSize()));
        linhasPaginado.total(clienteLinhas.getTotalElements());
        linhasPaginado.pages(new Long(clienteLinhas.getTotalPages()));
        linhasPaginado.setLinhas(clienteLinhas.stream().map(clienteLinha -> {
            com.br.dieggocarrilho.linhas.transportesdimed.api.model.Linhas l = new com.br.dieggocarrilho.linhas.transportesdimed.api.model.Linhas();
            l.setNome(clienteLinha.getLinhas().getNome());
            l.setId(clienteLinha.getLinhas().getId());
            l.setCodigo(clienteLinha.getLinhas().getCodigo());
            return l;
        }).collect(Collectors.toList()));

        return new ResponseEntity<>(linhasPaginado, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> clienteLinhasCadastrar( @Valid @RequestBody Linhas body) {

        Optional<com.br.dieggocarrilho.linhas.domain.Linhas> byId = linhasService.findById(body.getId());

        if (!byId.isPresent()) {
            return new ResponseEntity(new Message("Não existe esta linha"), HttpStatus.NOT_FOUND);
        }

        try {
            clienteService.cadastrarLinhaCliente(byId.get());

        } catch (ExceptionConflict e ) {
            return new ResponseEntity(new Message(e.getMessage()), HttpStatus.CONFLICT);

        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> clienteLinhasRemover( @PathVariable("idUt") Long idUt ) {

        try {
            clienteService.removerLinhaCliente(idUt);
        } catch (ExceptionNotFound e ){
            return new ResponseEntity(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
