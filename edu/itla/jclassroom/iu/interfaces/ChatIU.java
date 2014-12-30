package edu.itla.jclassroom.iu.interfaces;

import java.util.ArrayList;

import edu.itla.jclassroom.entidades.Aula;
import edu.itla.jclassroom.entidades.Usuario;

public interface ChatIU extends InterfazDeUsuario{
	public void recibirMensaje(String mensaje, Usuario usuario);
	public void iniciarClase();
	public void terminarClase();
	public void levantarMano(Usuario usuario);
	public void notificarPalabraCedida(Usuario usuario);
	public void notificarPalabraQuitada(Usuario usuario);
	public void entroUsuario(Usuario usuario, Aula aula);
	public void salioUsuario(Usuario usuario, Aula aula);	
	public void recibirListadoUsuariosLevantandoMano(ArrayList<Usuario> usuarios);
	public void presentarPizarra();
	public void recibirUsuarioConPalabra(Usuario usuario);
	public void recibirEstadoClase(String estado);
	public void recibirImagen(String imagen);
}
