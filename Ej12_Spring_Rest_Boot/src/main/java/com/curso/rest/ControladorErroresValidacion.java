package com.curso.rest;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.curso.rest.dto.Error;
import com.curso.rest.dto.Respuesta;

@ControllerAdvice
public class ControladorErroresValidacion {

	public ControladorErroresValidacion() {
		super();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseEntity<Respuesta> handleValidationException(MethodArgumentNotValidException e) {
		
		System.out.println("//////////////////////////////");
		
		Map<String, String> errores = e
			.getBindingResult()
			.getFieldErrors()
			.stream()
			.collect(Collectors.toMap( 
						fe -> fe.getField(), 
						fe -> fe.getDefaultMessage()
					));
		Respuesta respuesta = new Respuesta();
		respuesta.setStatus("400");
		respuesta.setError(new Error("400","Datos inválidos",errores));
		return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);		
	}

}
