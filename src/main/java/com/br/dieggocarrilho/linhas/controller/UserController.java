package com.br.dieggocarrilho.linhas.controller;

import com.br.dieggocarrilho.linhas.service.ClienteService;
import com.br.dieggocarrilho.linhas.transportesdimed.api.ClienteApi;
import com.br.dieggocarrilho.linhas.transportesdimed.api.model.Cliente;
import com.br.dieggocarrilho.linhas.transportesdimed.api.model.ClienteResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.security.Principal;

@RestController
public class UserController implements ClienteApi {

    private ClienteService clienteService;

    public UserController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Override
    public ResponseEntity<ClienteResponse> createCliente(@Valid @RequestBody Cliente user) {
        com.br.dieggocarrilho.linhas.domain.Cliente cliente = new com.br.dieggocarrilho.linhas.domain.Cliente();
        cliente.setEmail(user.getEmail());
        cliente.setName(user.getName());
        cliente.setPassword(user.getPassword());
        cliente.setUsername(user.getUsername());
        System.out.println(user);
        com.br.dieggocarrilho.linhas.domain.Cliente retorno = clienteService.save(cliente);

        if (retorno == null) {
            return new ResponseEntity<ClienteResponse>(HttpStatus.BAD_REQUEST);
        }
        ClienteResponse clienteResponse = new ClienteResponse();
        clienteResponse.setEmail(retorno.getEmail());
        clienteResponse.setId(retorno.getId());
        clienteResponse.setUsername(retorno.getUsername());
        clienteResponse.setName(retorno.getName());
        return new ResponseEntity<ClienteResponse>(clienteResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteCliente(Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateCliente(Long id, @Valid Cliente body) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/verificar",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<String> verificarAlgo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getDetails());
        return new ResponseEntity<>(HttpStatus.OK);

    }
 }
