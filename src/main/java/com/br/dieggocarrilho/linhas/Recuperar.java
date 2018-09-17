package com.br.dieggocarrilho.linhas;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.br.dieggocarrilho.linhas.domain.Coordenadas;
import com.br.dieggocarrilho.linhas.domain.Intinerario;
import com.br.dieggocarrilho.linhas.domain.Linhas;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Recuperar {

	public static void main(String[] args) {
		  RestTemplate restTemplate = new RestTemplate();

	        String url = "http://www.poatransporte.com.br/php/facades/process.php?a=il&p=";

	        ResponseEntity<String> result =
	                restTemplate.getForEntity(url + "5566",
	                        String.class );
	        System.out.println(result.getBody());


	        Type listType = new TypeToken<Intinerario>(){}.getType();
	        Intinerario array = new Gson().fromJson(result.getBody(), listType);
	        System.out.println(array);

System.out.println("---------------'---");

			Type type = new TypeToken<Map<Object, Object>>(){}.getType();
			Map<String, String> myMap = new Gson().fromJson(result.getBody(), type);
			for(Object key:  new HashSet<>(myMap.keySet())){
		        if (key.equals("idlinha") || key.equals("codigo") || key.equals("nome")) {    
		        	myMap.remove(key);
		        }   
			  }
			
			for (Map.Entry<String, String> entry : myMap.entrySet()) {
			    Object value = entry.getValue();
			    Coordenadas co = new Gson().fromJson(value.toString(), Coordenadas.class);
			    System.out.println(co);
			}
	}

}
