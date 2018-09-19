package com.br.dieggocarrilho.linhas.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
public class Linhas implements Serializable {

    private static final long serialVersionUID = -4181985100899233094L;

    @Id
    private Long id;
    private String codigo;
    private String nome;

    @OneToMany(mappedBy = "linhas")
    private List<ClienteLinha> clientesLinhas;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Linhas(Long id, String codigo, String nome) {
        this.id = id;
        this.codigo = codigo;
        this.nome = nome;
    }
    public Linhas() {
    }

    public List<ClienteLinha> getClientesLinhas() {
        return clientesLinhas;
    }

    public void setClientesLinhas(List<ClienteLinha> clientesLinhas) {
        this.clientesLinhas = clientesLinhas;
    }

    @Override
    public String toString() {
        return "Linhas{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }
}
