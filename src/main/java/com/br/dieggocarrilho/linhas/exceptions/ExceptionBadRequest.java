package com.br.dieggocarrilho.linhas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Bad Request!")
public class ExceptionBadRequest extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public ExceptionBadRequest(String msg){
		super(msg);
	}
}