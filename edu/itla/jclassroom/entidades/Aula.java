package edu.itla.jclassroom.entidades;

import java.io.Serializable;
import java.util.ArrayList;

public class Aula implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int aulaId;
	private String nombre;
	private Usuario usuarioConPalabra;
	private Docencia docencia;
	private String profesor;
	private ArrayList<Usuario> usuariosEnLinea;
	private ArrayList<Usuario> usuariosLevantandoMano;
	
	public Aula(int aulaId, String nombre, String profesor) {		
		this.aulaId = aulaId;
		this.nombre = nombre;
		this.profesor = profesor;
		usuariosEnLinea = new ArrayList<Usuario>();
		usuariosLevantandoMano = new ArrayList<Usuario>();
		usuarioConPalabra = new Usuario("");
		this.docencia = new Docencia();
	}
	
	public Aula(String nombre, String profesor) {
		super();
		this.nombre = nombre;
		this.profesor = profesor;
	}

	public Aula(String nombre) {
		this.nombre = nombre;
		usuariosEnLinea = new ArrayList<Usuario>();
		usuariosLevantandoMano = new ArrayList<Usuario>();
		usuarioConPalabra = new Usuario("");
		this.docencia = new Docencia();
	}

	public int getAulaId() {
		return aulaId;
	}

	public String getNombre() {
		return nombre;
	}

	public String getProfesor() {
		return profesor;
	}

	public void setAulaId(int aulaId) {
		this.aulaId = aulaId;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setProfesor(String profesor) {
		this.profesor = profesor;
	}
	public String aulaDatos(){
		return getAulaId()+"\t"+getNombre()+"\t\t\t\t"+getProfesor();
	}
	public String toString(){
		return nombre;
	}

	public ArrayList<Usuario> getUsuariosEnLinea() {
		return usuariosEnLinea;
	}

	public void setUsuariosEnLinea(ArrayList<Usuario> usuariosEnLinea) {
		this.usuariosEnLinea = usuariosEnLinea;
	}
		
	public boolean equals(Object object){
		if(object instanceof Aula && ((Aula) object).getNombre().equals(getNombre())){
			return true;
		}
		return false;
	}

	public ArrayList<Usuario> getUsuariosLevantandoMano() {
		return usuariosLevantandoMano;
	}

	public Usuario getUsuarioConPalabra() {
		return usuarioConPalabra;
	}

	public Docencia getDocencia() {
		return docencia;
	}
	
	
}
