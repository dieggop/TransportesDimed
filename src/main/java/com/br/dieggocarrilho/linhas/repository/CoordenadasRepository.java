package com.br.dieggocarrilho.linhas.repository;

import com.br.dieggocarrilho.linhas.domain.Coordenadas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CoordenadasRepository extends JpaRepository<Coordenadas, Long> {
    void deleteByIdLinha(Long id);

    List<Coordenadas> findByIdLinha(Long id);

    Page<Coordenadas> findByIdLinha(Long id, Pageable pageable);

     /*SELECT *,(RAIO_TERRESTRE *
        acos(
         cos(radians(PARAMETRO_LATITUDE)) *
         cos(radians(COLUNA_LATITUDE)) *
         cos(radians(PARAMETRO_LONGITUDE) - radians(COLUNA_LONGITUDE)) +
         sin(radians(PARAMETRO_LATITUDE)) *
         sin(radians(COLUNA_LATITUDE))
      )) AS CAMPOLATITUDE
FROM TABELA HAVING CAMPOLATITUDE <= KM*/

    @Query( value = "SELECT  * FROM coordenadas c where (6371 * " +
            "                         acos(" +
            "                           cos(radians(?3)) * " +
            "                           cos(radians(c.lat)) * " +
            "                           cos(radians(?4) - radians(c.lng)) + " +
            "                           sin(radians(?3)) * " +
            "                           sin(radians(c.lat)) " +
            "                             )) <= ?2 and c.id_linha=?1",
            nativeQuery = true)
    Page<Coordenadas> findByUtRaioLatLng(Long idUt, Integer raio, Double lat, Double lng, Pageable paginado);
}
