package edu.itla.jclassroom.entidades;

import java.io.Serializable;

public class Usuario implements Serializable, Comparable<Usuario>{
	private static final long serialVersionUID = 1L;
	private int usuarioId;
	private String nombre;
	private String apellido;
	private String nombreUsuario;
	private String clave;
	private boolean profesor;
	private boolean tiempoParaHablar;
	
	public Usuario(String nombreUsuario) {
		super();
		this.nombreUsuario = nombreUsuario;
	}	
	
	public Usuario(String nombre, String apellido, String nombreUsuario, String clave) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.nombreUsuario = nombreUsuario;
		this.clave = clave;
	}


	public Usuario(int usuarioId, String nombre, String apellido, String nombreUsuario, String clave) {
		super();
		this.usuarioId = usuarioId;
		this.nombre = nombre;
		this.apellido = apellido;
		this.nombreUsuario = nombreUsuario;
		this.clave = clave;
	}
	public String getApellido() {
		return apellido;
	}
	public String getClave() {
		return clave;
	}
	public String getNombre() {
		return nombre;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public int getUsuarioId() {
		return usuarioId;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public boolean isProfesor() {
		return profesor;
	}
	public boolean isTiempoParaHablar() {
		return tiempoParaHablar;
	}
	public void setProfesor(boolean profesor) {
		this.profesor = profesor;
	}
	public void setTiempoParaHablar(boolean tiempoParaHablar) {
		this.tiempoParaHablar = tiempoParaHablar;
	}
	
	public String usuarioDatos(){
		return getUsuarioId()+"\t"+ getNombre()+"\t"+ getApellido() +"\t"+ getNombreUsuario();
	}
	
	public String toString(){
		return nombreUsuario;
	}
	@Override
	public boolean equals(Object obj) {
		if ((obj instanceof Usuario) && ((Usuario)obj).getNombreUsuario().equals(this.getNombreUsuario())) {
			return true;
		} else {
			return false;
		}
	}

	public int compareTo(Usuario usuario) {
		return  usuario.getNombreUsuario().compareTo(this.getNombreUsuario())  * (-1); //Organizando en forma ascendente. 
	}
}
