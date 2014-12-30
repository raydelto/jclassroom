package edu.itla.jclassroom.iu.interfaces;
import java.util.ArrayList;
import edu.itla.jclassroom.entidades.Aula;

public interface SeleccionDeAulasIU extends InterfazDeUsuario{
	public void listarAulas(ArrayList<Aula> aulas);	
}
