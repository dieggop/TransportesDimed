package com.br.dieggocarrilho.linhas.controller;

import com.br.dieggocarrilho.linhas.service.ClienteService;
import com.br.dieggocarrilho.linhas.transportesdimed.api.ClienteApi;
import com.br.dieggocarrilho.linhas.transportesdimed.api.model.Cliente;
import com.br.dieggocarrilho.linhas.transportesdimed.api.model.ClienteResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class UserController  {

    private ClienteService clienteService;

    public UserController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ClienteResponse> createCliente(@Valid Cliente user) {
        com.br.dieggocarrilho.linhas.domain.Cliente cliente = new com.br.dieggocarrilho.linhas.domain.Cliente();
        cliente.setEmail(user.getEmail());
        cliente.setName(user.getName());
        cliente.setPassword(user.getPassword());
        cliente.setUsername(user.getUsername());
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

    @GetMapping("/verificar")
    @PermitAll
    public ResponseEntity<String> verificarAlgo() {
        return new ResponseEntity<>("Teste", HttpStatus.OK);

    }
 }
