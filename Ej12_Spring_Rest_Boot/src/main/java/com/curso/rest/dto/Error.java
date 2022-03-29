package com.curso.rest.dto;

public class Error {

	private String codigoError;
	private String mensaje;
	private Object detalles;

	public Error() {
		super();
	}

	public Error(String codigoError, String mensaje) {
		super();
		this.codigoError = codigoError;
		this.mensaje = mensaje;
	}

	public Error(String codigoError, String mensaje, Object detalles) {
		super();
		this.codigoError = codigoError;
		this.mensaje = mensaje;
		this.detalles = detalles;
	}

	public String getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Object getDetalles() {
		return detalles;
	}

	public void setDetalles(Object detalles) {
		this.detalles = detalles;
	}

}
