package com.br.dieggocarrilho.linhas.configuracao;

import com.br.dieggocarrilho.linhas.domain.Coordenadas;
import com.br.dieggocarrilho.linhas.domain.Intinerario;
import com.br.dieggocarrilho.linhas.domain.Linhas;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.inject.Named;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Named
public class PoaTransportes {

    static Logger logger = LoggerFactory.getLogger(PoaTransportes.class);

    public static PoaTransportesService poaTransportesService;

    public PoaTransportes(PoaTransportesService poaTransportesService) {
        this.poaTransportesService = poaTransportesService;
    }

    public static void atualizarLinhasNoSistema() {
        logger.info("Atualizando Linhas no sistema");
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> result =
                restTemplate.getForEntity("http://www.poatransporte.com.br/php/facades/process.php?a=nc&p=%&t=o",
                        String.class );

        Type listType = new TypeToken<ArrayList<Linhas>>(){}.getType();
        List<Linhas> linhas = new Gson().fromJson(result.getBody(), listType);
        List<Linhas> linhasNoSistema = poaTransportesService.findAll();
        List<Linhas> linhasAtualizarSistema = new ArrayList<>();

        if (!linhasNoSistema.isEmpty()) {

            linhas.forEach(linhas1 -> {

                Optional<Linhas> first = linhasNoSistema.stream().filter(o -> o.getId().equals(linhas1.getId())).findFirst();
                Linhas encontrado = null;

                if (first.isPresent()) {
                    encontrado = first.get();
                    int diferente = 0;
                    if (!linhas1.getCodigo().equals(encontrado.getCodigo())) {
                        logger.info("codigo " + linhas1.getCodigo() + " diferente de  " + encontrado.getCodigo());
                        diferente++;
                    }
                    if (!linhas1.getNome().equals(encontrado.getNome())) {
                        logger.info("nome " + linhas1.getNome() + " diferente de  " + encontrado.getNome());
                        diferente++;
                    }

                    if (diferente > 0) {
                        logger.info("diferenças encontradas");
                        logger.info("atualizando linha de codigo " + linhas1.getCodigo());
                        encontrado.setCodigo(linhas1.getCodigo());
                        encontrado.setNome(linhas1.getNome());
                        linhasAtualizarSistema.add(encontrado);
                        diferente = 0;
                    }
                }

                if (encontrado == null) {

                    logger.info("adicionou novo " + linhas1.getCodigo());
                    linhasAtualizarSistema.add(linhas1);

                }


            });
        }

        logger.info("Atualizados " + linhasAtualizarSistema.size());
        poaTransportesService.saveAll(linhasAtualizarSistema);
    }

    public static void atualizarIntinerariosNoSistema() {
        logger.info("Atualizando Intinerarios no sistema");
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://www.poatransporte.com.br/php/facades/process.php?a=il&p=";

        List<Linhas> linhasNoSistema = poaTransportesService.findAll();

        linhasNoSistema.get(0).getId();

        ResponseEntity<String> result =
                restTemplate.getForEntity(url + linhasNoSistema.get(0).getId(),
                        String.class );
        System.out.println(result.getBody());


        Type listType = new TypeToken<ArrayList<Coordenadas>>(){}.getType();

        List<Coordenadas> array = new Gson().fromJson(result.getBody(), listType);

        System.out.println(array);


//        return lst;
//
//            Object intinerarios = new Gson().fromJson(result.getBody(), Intinerario.class);
//        System.out.println(intinerarios);


/*        linhasNoSistema.forEach(linhas -> {
            ResponseEntity<String> result =
                    restTemplate.getForEntity(url + linhas.getId(),
                            String.class );
            System.out.println(result.getBody());
//            Type listType = new TypeToken<ArrayList<Intinerario>>(){}.getType();
//            List<Intinerario> intinerarios = new Gson().fromJson(result.getBody(), listType);
//            System.out.println(intinerarios.toString());
        });*/


    }


}
