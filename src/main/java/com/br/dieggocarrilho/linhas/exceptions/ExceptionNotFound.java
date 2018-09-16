package com.br.dieggocarrilho.linhas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Not found!")
public class ExceptionNotFound extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public ExceptionNotFound(String msg){
		super(msg);
	}
}