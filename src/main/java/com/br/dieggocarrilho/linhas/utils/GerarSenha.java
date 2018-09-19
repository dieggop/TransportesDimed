package com.br.dieggocarrilho.linhas.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class GerarSenha {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public final static String cripSenha(String senha) {
		return new BCryptPasswordEncoder().encode(senha);
	}
	public static void main(String[] args) {

		System.out.println(new BCryptPasswordEncoder().encode("123456"));

//		String senha = "123456";

//		System.out.print(new String(Base64.getEncoder().encodeToString(senha.getBytes())));



	}
}
