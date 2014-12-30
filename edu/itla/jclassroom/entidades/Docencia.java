package edu.itla.jclassroom.entidades;

import java.io.Serializable;

public class Docencia implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String estadoClase; //este atributo puede tomar los valores de: iniciada, no iniciada y terminada

	public String getEstadoClase() {
		return estadoClase;
	}

	public void setEstadoClase(String estado) {
		this.estadoClase = estado;
	}

	public Docencia() {
		super();
		this.estadoClase = "no iniciada";
	}
	
	
	
	
}
