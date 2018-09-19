package com.br.dieggocarrilho.linhas.domain;

import javax.persistence.*;

@Entity
public class ClienteLinha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Linhas linhas;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Linhas getLinhas() {
        return linhas;
    }

    public void setLinhas(Linhas linhas) {
        this.linhas = linhas;
    }


}
