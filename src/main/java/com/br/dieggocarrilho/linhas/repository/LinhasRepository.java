package com.br.dieggocarrilho.linhas.repository;

import com.br.dieggocarrilho.linhas.domain.Linhas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LinhasRepository extends JpaRepository<Linhas, Long> {

    Page<Linhas> findByNomeContainingIgnoreCase(String nome, Pageable paginado);


    @Query( value = "select * from linhas where id in (SELECT distinct id_linha FROM coordenadas c where (6371 * acos( cos(radians(?2)) * cos(radians(c.lat)) * cos(radians(?3) - radians(c.lng)) + sin(radians(?2)) * sin(radians(c.lat)) )) <= ?1)",
    nativeQuery = true)
    Page<Linhas> findByRaioLatLng(Integer raio, Double lat, Double lng, Pageable paginado);
}
