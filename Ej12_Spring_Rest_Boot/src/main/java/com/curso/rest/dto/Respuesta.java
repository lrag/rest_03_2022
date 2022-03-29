package com.curso.rest.dto;

public class Respuesta {

	private String status;
	private Error error;
	private Datos datos;

	public Respuesta() {
		super();
	}

	public Respuesta(String status, Error error, Datos datos) {
		super();
		this.status = status;
		this.error = error;
		this.datos = datos;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	public Datos getDatos() {
		return datos;
	}

	public void setDatos(Datos datos) {
		this.datos = datos;
	}

}
