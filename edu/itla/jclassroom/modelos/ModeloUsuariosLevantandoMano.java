package edu.itla.jclassroom.modelos;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.AbstractListModel;

import edu.itla.jclassroom.entidades.Usuario;

public class ModeloUsuariosLevantandoMano extends AbstractListModel{
	private static final long serialVersionUID = 1L;
	ArrayList<Usuario> usuarios;
	private static ModeloUsuariosLevantandoMano instancia;
	
	public synchronized static ModeloUsuariosLevantandoMano getInstancia(){
		if (instancia == null){
			instancia = new ModeloUsuariosLevantandoMano();
		}
		return instancia;
	}
	
	private ModeloUsuariosLevantandoMano(){
		usuarios = new ArrayList<Usuario>();
	}
	public Object getElementAt(int x) {
		Collections.sort(usuarios);
		return usuarios.get(x).getNombreUsuario();
	}

	public void setUsuarios(ArrayList<Usuario> usuarios) {
		this.usuarios = usuarios;
		int tamano = usuarios.size() > 0 ? usuarios.size()-1 : 0;
		fireContentsChanged(this, 0, tamano);
	}

	public int getSize() {
		return usuarios.size();
	}
	
	public void agregar(Usuario usuario){
		usuarios.add(usuario);
		fireContentsChanged(this, usuarios.size()-1, usuarios.size()-1);
		
	}
	
	public void remover(Usuario usuario){
		int indice = buscarIndice(usuario); //indice del usuario en el ArrayList		
		if(indice != -1){
			usuarios.remove(indice);
			fireIntervalRemoved(this, indice, indice);
		}
	}

	private int buscarIndice(Usuario usuario) {
		for(int i = 0 ; i < usuarios.size() ; i++){
			if (usuarios.get(i).getNombreUsuario().equals(usuario.getNombreUsuario())){
				return i;
			}
		}
		return -1;
	}

	public ArrayList<Usuario> getUsuarios() {
		return usuarios;
	}
}
