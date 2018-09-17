package com.br.dieggocarrilho.linhas.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.google.gson.internal.LinkedHashTreeMap;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Intinerario implements Serializable {

    private static final long serialVersionUID = -4181985100899233094L;

    Long idlinha;

    String codigo;
    String nome;

    Map<String, Object> coordenadas = new HashMap<String, Object>();


    public Intinerario() {
    }

     

    public Long getIdlinha() {
        return idlinha;
    }

    public void setIdlinha(Long idlinha) {
        this.idlinha = idlinha;
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



	@Override
	public String toString() {
		return "Intinerario [idlinha=" + idlinha + ", codigo=" + codigo + ", nome=" + nome + ", coordenadas="
				+ coordenadas + "]";
	}
 
}
