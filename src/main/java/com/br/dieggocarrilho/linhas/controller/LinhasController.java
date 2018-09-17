package com.br.dieggocarrilho.linhas.controller;

import com.br.dieggocarrilho.linhas.domain.Linhas;
import com.br.dieggocarrilho.linhas.transportesdimed.api.LinhasApi;
import com.br.dieggocarrilho.linhas.transportesdimed.api.model.LinhasPaginado;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LinhasController implements LinhasApi {


    @Override
    public ResponseEntity<LinhasPaginado> filtrarLinhas(@RequestParam(value = "nome", required = false) String nome, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "per_page", required = false) Integer perPage) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateLinhas() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> result =
                restTemplate.getForEntity("http://www.poatransporte.com.br/php/facades/process.php?a=nc&p=%&t=o",
                        String.class );

        Type listType = new TypeToken<ArrayList<Linhas>>(){}.getType();
        List<com.br.dieggocarrilho.linhas.domain.Linhas> linhas = new Gson().fromJson(result.getBody(), listType);



        return new ResponseEntity<>(HttpStatus.OK);

    }
}
