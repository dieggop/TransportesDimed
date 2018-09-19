package com.br.dieggocarrilho.linhas.service.impl;

import com.br.dieggocarrilho.linhas.domain.Cliente;
import com.br.dieggocarrilho.linhas.domain.ClienteLinha;
import com.br.dieggocarrilho.linhas.domain.Linhas;
import com.br.dieggocarrilho.linhas.exceptions.ExceptionConflict;
import com.br.dieggocarrilho.linhas.exceptions.ExceptionNotFound;
import com.br.dieggocarrilho.linhas.repository.ClienteLinhaRepository;
import com.br.dieggocarrilho.linhas.repository.ClienteRepository;
import com.br.dieggocarrilho.linhas.service.ClienteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ClienteRepository clienteRepository;
    private ClienteLinhaRepository clienteLinhaRepository;

    public ClienteServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, ClienteRepository clienteRepository, ClienteLinhaRepository clienteLinhaRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.clienteRepository = clienteRepository;
        this.clienteLinhaRepository = clienteLinhaRepository;
    }

    @Override
    public Cliente save(Cliente user) {
        user.setStatus(Boolean.TRUE);
        System.out.println(user);
        if (user.getId() != null && clienteRepository.findByUsername(user.getUsername()) == null) {
            throw new ExceptionNotFound("Não foi possível editar suas informações");
        } else if (user.getId() == null && clienteRepository.findByUsername(user.getUsername()) != null) {
            throw new ExceptionConflict("Este username já esta sendo utilizado. Tente outro");
        } else if (user.getId() != null  && clienteRepository.findByUsername(user.getUsername()) != null) {
            if (StringUtils.isEmpty(user.getPassword())) {
                Optional<Cliente> byId = clienteRepository.findById(user.getId());
                user.setPassword(byId.get().getPassword());
            } else {
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            }
        } else if (user.getId() == null  && clienteRepository.findByUsername(user.getUsername()) == null) {
            if (StringUtils.isEmpty(user.getPassword())) {
                throw new ExceptionConflict("Você deve escolher uma senha");
            }
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getEmail())) {
            throw new ExceptionConflict("Os campos de username ou email estão em branco");
        }

        return clienteRepository.save(user);
    }

    @Override
    public void desativarCliente() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Cliente byUsername = clienteRepository.findByUsername(auth.getName());
        if (byUsername == null) {
            throw new ExceptionNotFound("Este usuário não existe");
        } else if (byUsername.getStatus() == false) {
            throw new ExceptionConflict("Não foi possível editar suas informações");
        }

        byUsername.setStatus(Boolean.FALSE);
        clienteRepository.save(byUsername);

        SecurityContextHolder.getContext().setAuthentication(null);


    }

    @Override
    public Cliente recuperarCliente() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Cliente retorno = clienteRepository.findByUsername(auth.getName());
        if (retorno == null) {
            throw new ExceptionNotFound("Este usuário não existe");
        }
        return retorno;
    }

    @Override
    public Page<ClienteLinha> listarLinhasDoCliente(PageRequest paginado) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Cliente retorno = clienteRepository.findByUsername(auth.getName());
        return clienteLinhaRepository.findByCliente(retorno,paginado);
    }

    @Override
    public void removerLinhaCliente(Long idUt) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Cliente retorno = clienteRepository.findByUsername(auth.getName());

        Optional<ClienteLinha> byClienteAndLinhasId = clienteLinhaRepository.findByClienteIdAndLinhasId(retorno.getId(), idUt);

        if (byClienteAndLinhasId.isPresent()) {
            clienteLinhaRepository.delete(byClienteAndLinhasId.get());
        } else {
            throw new ExceptionNotFound("Esta linha não esta cadastrada para o cliente");
        }
    }

    @Override
    public void cadastrarLinhaCliente(Linhas body) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Cliente retorno = clienteRepository.findByUsername(auth.getName());

        ClienteLinha clienteLinha = new ClienteLinha();
        clienteLinha.setCliente(retorno);
        clienteLinha.setLinhas(body);


        Optional<ClienteLinha> byClienteAndLinhasId = clienteLinhaRepository.findByClienteIdAndLinhasId(retorno.getId(), body.getId());

        if (byClienteAndLinhasId.isPresent()) {
            throw new ExceptionConflict("Cadastro Duplicado");
        }

        clienteLinhaRepository.save(clienteLinha);
    }
}
