package com.br.dieggocarrilho.linhas.configuracao;

import com.br.dieggocarrilho.linhas.domain.Coordenadas;
import com.br.dieggocarrilho.linhas.domain.Linhas;
import com.br.dieggocarrilho.linhas.exceptions.ExceptionBadRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Named
public class PoaTransportes {

    static Logger logger = LoggerFactory.getLogger(PoaTransportes.class);

    public static PoaTransportesService poaTransportesService;

    @Inject
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
        if (!linhasAtualizarSistema.isEmpty())
        {
            List<Linhas> retornoSalvo = poaTransportesService.saveAll(linhasAtualizarSistema);
            if (retornoSalvo.isEmpty())  throw new ExceptionBadRequest("Não foi possível atualizar as linhas");
            atualizarItinerariosNoSistema(retornoSalvo);
        }


    }

    public static void atualizarItinerariosNoSistema(List<Linhas> retornoSalvo) {
        logger.info("Atualizando Itinerarios no sistema");

        List<Linhas> linhasNoSistema =retornoSalvo;

        linhasNoSistema.forEach(linhas -> {
           baixarAtualizarItinerariosDaLinhaNoSistema(linhas.getId());

        });

    }

    public static void baixarAtualizarItinerariosDaLinhaNoSistema(Long id) {
        List<Coordenadas> coordenadasDasLinhas = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://www.poatransporte.com.br/php/facades/process.php?a=il&p=";

        ResponseEntity<String> result =
                restTemplate.getForEntity(url + id,
                        String.class );

        Type type = new TypeToken<Map<Object, Object>>(){}.getType();
        Map<String, String> myMap = new Gson().fromJson(result.getBody(), type);
        for(Map.Entry<String, String> key:  myMap.entrySet()){
            try {
                Integer.valueOf(key.getKey());
                Object value = key.getValue();
                Coordenadas co = new Gson().fromJson(value.toString(), Coordenadas.class);
                co.setIdLinha(id);
                coordenadasDasLinhas.add(co);
            } catch (Exception e) {
//					e.printStackTrace();
                logger.warn("Encontrado chaves que não são coordenadas");
            }
        }

        logger.info("Removendo intinerários desta linha");
        poaTransportesService.deleteAllFromIdLinha(id);
        logger.info("Atualizando Itinerarios desta linha no sistema");
        poaTransportesService.saveAllCoordenadas(coordenadasDasLinhas);


    }





}
