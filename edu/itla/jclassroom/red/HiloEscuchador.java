package edu.itla.jclassroom.red;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import edu.itla.jclassroom.Sistema;
import edu.itla.jclassroom.entidades.Aula;
import edu.itla.jclassroom.entidades.Usuario;
import edu.itla.jclassroom.iu.PanelPizarra;
import edu.itla.jclassroom.iu.VentanaPrincipal;

public class HiloEscuchador extends Thread{
	private BufferedReader lector;
	private PrintWriter escritor;
	private Aula aula;
	private ArrayList<Aula> listaAulas;
	
	public Aula getAula() {
		return aula;
	}

	public void setAula(Aula aula) {
		this.aula = aula;
	}

	public HiloEscuchador(Socket socket) {
		try {
			lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			escritor = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			notificarError("Se ha perdido la conexion con el servidor");
			lector = null;
			escritor = null;
		}
	}	
	
	public void escribir(String mensaje){
		if(escritor != null){
			escritor.println(mensaje);
			escritor.flush();	
		}
	}
	
	public void run(){
		String linea = "";
		StringTokenizer st = null ;
		String comando = "";		
		while(true){
			try {
				if (linea == null){
					break;
				}
				while((linea = lector.readLine()) != null){
					st = new StringTokenizer(linea,"&");
					comando = st.nextToken();
					if(comando.equals("/mensaje") && st.countTokens() == 2){						
						Usuario usuario = new Usuario(st.nextToken());
						if (Conexion.getInstancia().getChatIU() == null){
							Conexion.getInstancia().setChatIU(VentanaPrincipal.getInstancia());
						}
						if (usuario != null){
							Conexion.getInstancia().getChatIU().recibirMensaje(st.nextToken(), usuario);
						}
					}else if(comando.equals("/dibujar")){
						if(!PanelPizarra.getInstancia().isVisible()){
							Conexion.getInstancia().getChatIU().presentarPizarra();
						}
						Conexion.getInstancia().getPizarra().recibirPuntos(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), 
									Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
	
					}else if(comando.equals("/inicioClase")){
						Conexion.getInstancia().getChatIU().iniciarClase();
						Sistema.getInstancia().getMiAula().getDocencia().setEstadoClase("iniciada");
						Conexion.getInstancia().getChatIU().recibirEstadoClase("iniciada");
						
					}else if(comando.equals("/terminoClase")){
						Conexion.getInstancia().getChatIU().terminarClase();
						Conexion.getInstancia().getChatIU().recibirEstadoClase("terminada");
						Sistema.getInstancia().getMiAula().getDocencia().setEstadoClase("terminada");
						
					}else if(comando.equals("/estadoClase")){
						String estado = st.nextToken();
						Conexion.getInstancia().getChatIU().recibirEstadoClase(estado);
						Sistema.getInstancia().getMiAula().getDocencia().setEstadoClase(estado);
						
					}else if(comando.equals("/recibirImagen")){
						String imagen = st.nextToken();
						Conexion.getInstancia().getChatIU().recibirImagen(imagen);						
					}else if(comando.equals("/usuariosLevantandoMano")){						
						st.nextToken(); //No necesitamos el nombre del aula
						ArrayList<Usuario> usuariosLevantandoMano = new ArrayList<Usuario>();
						while(st.hasMoreTokens()){
							usuariosLevantandoMano.add(new Usuario(st.nextToken()));
						}
						if(Conexion.getInstancia().getChatIU() == null){
							Conexion.getInstancia().setChatIU(VentanaPrincipal.getInstancia());
						}
							Conexion.getInstancia().getChatIU().recibirListadoUsuariosLevantandoMano(usuariosLevantandoMano);
					} else if(comando.equals("/cedidaPalabra")){
						Usuario usuario = new Usuario(st.nextToken());
						Conexion.getInstancia().getChatIU().notificarPalabraCedida(usuario);
						Conexion.getInstancia().getChatIU().recibirUsuarioConPalabra(usuario);
					}else if(comando.equals("/quitadaPalabra")){
						Usuario usuario = new Usuario(st.nextToken());
						Conexion.getInstancia().getChatIU().notificarPalabraQuitada(usuario);
					}else if(comando.equals("/usuarioConPalabra") && st.countTokens() == 1){
						Usuario usuarioConPalabra = new Usuario(st.nextToken());
						Conexion.getInstancia().getChatIU().recibirUsuarioConPalabra(usuarioConPalabra);
					}else if(comando.equals("/entro")){
						Usuario usuario = new Usuario(st.nextToken());
						Aula aula = new Aula(st.nextToken());
						if (Sistema.getInstancia().getMiUsuario() != null) {
							if(Sistema.getInstancia().getMiUsuario().getNombreUsuario().equals(usuario.getNombreUsuario())){
								Sistema.getInstancia().setMiAula(aula);	
								for (Aula miAula: listaAulas) {
									if (miAula.equals(aula)) {
										Sistema.getInstancia().getMiAula().setProfesor(miAula.getProfesor());
									}
								}
							}else{
								Conexion.getInstancia().getChatIU().entroUsuario(usuario,aula);								
							}
						}
					}else if(comando.equals("/salio")){
						Usuario usuario = new Usuario(st.nextToken());
						Aula aula = new Aula(st.nextToken());
						Conexion.getInstancia().getChatIU().salioUsuario(usuario, aula);						
						Conexion.getInstancia().solicitarEstudiantesEnLinea(Sistema.getInstancia().getMiAula().toString());
						System.out.println("El usuario " + usuario +" ha salido de " + aula);
					}else if(comando.equals("/aulas")){
						listaAulas = new ArrayList<Aula>();
						while(st.hasMoreTokens()){
							listaAulas.add(new Aula(st.nextToken(),st.nextToken()));
						}
						Conexion.getInstancia().getAulasIU().listarAulas(listaAulas);
					}else if(comando.equals("/usuariosConectados")){
						st.nextToken(); // No necesitamos el nombre del aula						
						ArrayList<Usuario> usuariosConectados = new ArrayList<Usuario>();
						
						while(st.hasMoreTokens()){
							usuariosConectados.add(new Usuario(st.nextToken()));
						}
						Conexion.getInstancia().setUsuariosEnLinea(usuariosConectados);
					}
				}
			} catch (IOException e) {
				notificarAdvertencia("Se ha perdido la conexion con el servidor");				
				break;
			}
		}
	}

	private void notificarError(String error){
		System.out.println(error);				
		if(Conexion.getInstancia().getChatIU() != null && Conexion.getInstancia().getChatIU().isVisible()){
			Conexion.getInstancia().getChatIU().recibirError(error);
		}else if(Conexion.getInstancia().getAulasIU() != null && Conexion.getInstancia().getAulasIU().isVisible()){
			Conexion.getInstancia().getAulasIU().recibirError(error);
		}
	}

	private void notificarAdvertencia(String advertencia){
		System.out.println(advertencia);				
		if(Conexion.getInstancia().getChatIU() != null && Conexion.getInstancia().getChatIU().isVisible()){
			Conexion.getInstancia().getChatIU().recibirAdvertencia(advertencia);
		}else if(Conexion.getInstancia().getAulasIU() != null && Conexion.getInstancia().getAulasIU().isVisible()){
			Conexion.getInstancia().getAulasIU().recibirAdvertencia(advertencia);
		}
	}

	
}
