package com.br.dieggocarrilho.linhas.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
public class Intinerario implements Serializable {

    private static final long serialVersionUID = -4181985100899233094L;

    @Id
    private Long id;
    Long idlinha;

    String codigo;
    String nome;
    Double latitude;
    Double longitude;

    @OneToMany
    List<Coordenadas> coordenadas;

    public Intinerario() {
    }

    public Intinerario(Long idlinha, String codigo, String nome, Double latitude, Double longitude, List<Coordenadas> coordenadas) {
        this.idlinha = idlinha;
        this.codigo = codigo;
        this.nome = nome;
        this.latitude = latitude;
        this.longitude = longitude;
        this.coordenadas = coordenadas;
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

    public List<Coordenadas> getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(List<Coordenadas> coordenadas) {
        this.coordenadas = coordenadas;
    }

    public Long getIdLinha() {
        return idlinha;
    }

    public void setIdLinha(Long idLinha) {
        this.idlinha = idLinha;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Intinerario{" +
                "id=" + id +
                ", idlinha=" + idlinha +
                ", codigo='" + codigo + '\'' +
                ", nome='" + nome + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", coordenadas=" + coordenadas +
                '}';
    }
}
