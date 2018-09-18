package com.br.dieggocarrilho.linhas.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Coordenadas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long idLinha;

    Double lat;
    Double lng;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Coordenadas(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Coordenadas() {
    }

    public Long getIdLinha() {
        return idLinha;
    }

    public void setIdLinha(Long idLinha) {
        this.idLinha = idLinha;
    }

    @Override
    public String toString() {
        return "Coordenadas{" +
                "id=" + id +
                ", idLinha=" + idLinha +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
