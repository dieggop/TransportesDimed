package com.br.dieggocarrilho.linhas.service.impl;

import com.br.dieggocarrilho.linhas.domain.Cliente;
import com.br.dieggocarrilho.linhas.exceptions.ExceptionConflict;
import com.br.dieggocarrilho.linhas.exceptions.ExceptionNotFound;
import com.br.dieggocarrilho.linhas.repository.ClienteRepository;
import com.br.dieggocarrilho.linhas.service.ClienteService;
import com.br.dieggocarrilho.linhas.transportesdimed.api.model.ClienteResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import java.util.Optional;

import static com.br.dieggocarrilho.linhas.constantes.SecurityConstants.HEADER_STRING;
import static com.br.dieggocarrilho.linhas.constantes.SecurityConstants.TOKEN_PREFIX;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ClienteRepository clienteRepository;

    public ClienteServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, ClienteRepository clienteRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.clienteRepository = clienteRepository;
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
}
