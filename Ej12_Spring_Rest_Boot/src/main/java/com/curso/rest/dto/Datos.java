package com.curso.rest.dto;

public class Datos {

	private String nombre;
	private Object valor;

	public Datos() {
		super();
	}

	public Datos(String nombre, Object valor) {
		super();
		this.nombre = nombre;
		this.valor = valor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Object getValor() {
		return valor;
	}

	public void setValor(Object valor) {
		this.valor = valor;
	}

}
