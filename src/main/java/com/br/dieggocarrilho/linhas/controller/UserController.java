package com.br.dieggocarrilho.linhas.controller;

import com.br.dieggocarrilho.linhas.constantes.Mapear;
import com.br.dieggocarrilho.linhas.exceptions.ExceptionConflict;
import com.br.dieggocarrilho.linhas.exceptions.ExceptionNotFound;
import com.br.dieggocarrilho.linhas.exceptions.Message;
import com.br.dieggocarrilho.linhas.service.ClienteService;
import com.br.dieggocarrilho.linhas.transportesdimed.api.ClienteApi;
import com.br.dieggocarrilho.linhas.transportesdimed.api.model.Cliente;
import com.br.dieggocarrilho.linhas.transportesdimed.api.model.ClienteResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController implements ClienteApi {

    private ClienteService clienteService;

    public UserController(ClienteService clienteService) {
        this.clienteService = clienteService;
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

    /*   @RequestMapping(value = "/verificar",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<String> verificarAlgo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getDetails());
        return new ResponseEntity<>(HttpStatus.OK);

    }*/
}
