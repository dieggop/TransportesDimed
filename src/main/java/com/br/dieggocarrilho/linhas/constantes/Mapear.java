package com.br.dieggocarrilho.linhas.constantes;

import com.br.dieggocarrilho.linhas.domain.Coordenadas;
import com.br.dieggocarrilho.linhas.transportesdimed.api.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.stream.Collectors;

public class Mapear {

    public static com.br.dieggocarrilho.linhas.domain.Cliente clienteModelClienteDomain(Cliente model) {
        com.br.dieggocarrilho.linhas.domain.Cliente cliente = new com.br.dieggocarrilho.linhas.domain.Cliente();
        cliente.setEmail(model.getEmail());
        cliente.setName(model.getName());
        cliente.setPassword(model.getPassword());
        cliente.setUsername(model.getUsername());
        cliente.setContato(model.getContato());
        cliente.setId(model.getId());
        return cliente;
    }


    public static ClienteResponse clienteDomainClienteResponseModel(com.br.dieggocarrilho.linhas.domain.Cliente domain) {
        ClienteResponse clienteResponse = new ClienteResponse();
        clienteResponse.setEmail(domain.getEmail());
        clienteResponse.setId(domain.getId());
        clienteResponse.setUsername(domain.getUsername());
        clienteResponse.setName(domain.getName());
        clienteResponse.setContato(domain.getContato());

        return clienteResponse;
    }

    public static Linhas linhaDomainLinhaModel(com.br.dieggocarrilho.linhas.domain.Linhas linhasDomain) {
        Linhas linhas = new Linhas();
        linhas.setCodigo(linhasDomain.getCodigo());
        linhas.setId(linhasDomain.getId());
        linhas.setNome(linhasDomain.getNome());

        return linhas;
    }


    public static LinhasPaginado linhasDomainLinhasPaginadoModel(PageRequest paginado, Page<com.br.dieggocarrilho.linhas.domain.Linhas> linhasPage) {
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
        return linhasPaginado;
    }

    public static LinhasPaginado clienteLinhasDomainLinhasPaginadoModel(PageRequest paginado, Page<com.br.dieggocarrilho.linhas.domain.ClienteLinha> clienteLinhas) {
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
        return linhasPaginado;
    }

    public static IntinerarioPaginado coordenadasDomainIntinerarioPaginadoModel(PageRequest paginado, Page<Coordenadas> coordenadas) {
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
