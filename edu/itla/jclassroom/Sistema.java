package edu.itla.jclassroom;
import edu.itla.jclassroom.entidades.Aula;
import edu.itla.jclassroom.entidades.Usuario;
import edu.itla.jclassroom.iu.VentanaAutenticacion;
public class Sistema {
	private Aula miAula;
	private Usuario miUsuario;
	private static Sistema instancia;
	
	public synchronized static Sistema getInstancia(){
		if(instancia == null){
			instancia = new Sistema();
		}
		return instancia;
	}
	
	private Sistema(){
		
	}
	public Aula getMiAula() {
		return miAula;
	}
	public void setMiAula(Aula miAula) {
		this.miAula = miAula;
	}
	public Usuario getMiUsuario() {
		return miUsuario;
	}
	public void setMiUsuario(Usuario miUsuario) {
		this.miUsuario = miUsuario;
	}
	public static void main(String[] args) {
		VentanaAutenticacion.getInstancia().setVisible(true);
	}
}
