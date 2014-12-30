package edu.itla.jclassroom.modelos;

import java.util.ArrayList;
import javax.swing.AbstractListModel;
import edu.itla.jclassroom.entidades.Aula;


public class ModeloListaAulas extends AbstractListModel{

	private static final long serialVersionUID = 1L;
	private ArrayList<Aula> aulas;
	private static ModeloListaAulas instancia;
	
	private ModeloListaAulas() {
		aulas = new ArrayList<Aula>();
	}
	public synchronized static ModeloListaAulas getInstancia(){
		if(instancia == null){
			instancia = new ModeloListaAulas();
		}
		return instancia;
	}
	public Object getElementAt(int x) {
		return aulas.get(x).getNombre();
	}

	public int getSize() {
		return aulas.size();
	}
	public void agregar(Aula aula){
		aulas.add(aula);
		fireContentsChanged(this, aulas.size()-1, aulas.size()-1);		
	}
	
	public void remover(Aula aula){
		int indice = buscarIndice(aula);		
		if(indice != -1){
			aulas.remove(indice);
			fireIntervalRemoved(this, indice, indice);
		}
	}

	private int buscarIndice(Aula aula) {
		for(int i = 0 ; i < aulas.size() ; i++){
			if (aulas.get(i).getNombre().equals(aula.getNombre())){
				return i;
			}
		}
		return -1;
	}
}
