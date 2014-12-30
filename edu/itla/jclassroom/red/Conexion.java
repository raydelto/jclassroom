package edu.itla.jclassroom.red;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import edu.itla.jclassroom.Sistema;
import edu.itla.jclassroom.entidades.Aula;
import edu.itla.jclassroom.entidades.Usuario;
import edu.itla.jclassroom.iu.interfaces.AutenticacionIU;
import edu.itla.jclassroom.iu.interfaces.ChatIU;
import edu.itla.jclassroom.iu.interfaces.PizarraIU;
import edu.itla.jclassroom.iu.interfaces.RegistroUsuariosIU;
import edu.itla.jclassroom.iu.interfaces.SeleccionDeAulasIU;
import edu.itla.jclassroom.modelos.ModeloUsuariosEnLinea;
import edu.itla.jclassroom.modelos.ModeloUsuariosLevantandoMano;

public class Conexion {
	private boolean conectado;
	private String localidad; //a donde nos estamos conectando
	private Socket flujoTexto;
	private PrintWriter escritor;
	private BufferedReader lector;
	private RegistroUsuariosIU registroUsuarioIU;
	private AutenticacionIU loginIU; //referencia a la clase que implementa interfaz de login
	private ChatIU chatIU; //referencia a la clase que implementa interfaz de chat
	private SeleccionDeAulasIU aulasIU; //referencia a la clase que implementa interfaz de seleccion de Aulas
	private PizarraIU pizarra; //referencia a la clase que implementa interfaz de pizarra	
	private static Conexion instancia = null;
	
	public SeleccionDeAulasIU getAulasIU() {
		return aulasIU;
	}
	public void setAulasIU(SeleccionDeAulasIU aulasIU) {
		this.aulasIU = aulasIU;
	}
	public ChatIU getChatIU() {
		return chatIU;
	}
	public String getLocalidad() {
		return localidad;
	}
	public boolean isConectado() {
		return conectado;
	}
	public void setLoginIU(AutenticacionIU loginIU) {
		this.loginIU = loginIU;
	}
	
	public void setRegistroUsuarioIU(RegistroUsuariosIU registroUsuarioIU) {
		this.registroUsuarioIU = registroUsuarioIU;
	}
	private Conexion(){		
	}
	public synchronized static Conexion getInstancia(){
		if(instancia == null){
			instancia = new Conexion();
		}
		return instancia;
	}
	
	public void registrar(String servidor, Usuario usuario) {
		try {
			flujoTexto = new Socket(servidor,4444);
			escritor = new PrintWriter(flujoTexto.getOutputStream());
			lector = new BufferedReader(new InputStreamReader(flujoTexto.getInputStream()));			
			escritor.println("/registrar&"+usuario.getNombre()+"&"+usuario.getApellido()+"&"+usuario.getNombreUsuario()+"&"+usuario.getClave());
			escritor.flush();
			String respuesta = "";
			while((respuesta = lector.readLine())!= null){
				if(respuesta.equals("/registradoConExito")){
					registroUsuarioIU.recibirConfirmacionRegistro("La cuenta " + usuario.getNombreUsuario() + " ha sido creada exitosamente");
				}else if (respuesta.equals("/usuarioYaExiste")){
					registroUsuarioIU.recibirError("Usuario no pudo ser creado, el nombre de usuario: \"" + usuario.getNombreUsuario()+ "\" ya existe");					
				}
				break;
			}
		} catch (UnknownHostException e) {
			registroUsuarioIU.recibirError("No se pudo encontrar esa direccion");			
		} catch (IOException e) {
			registroUsuarioIU.recibirError("Error de entrada / Salida");
		}catch(Exception e){
			registroUsuarioIU.recibirError("Error inesperado");			
		}
		
	}
	public void conectar(String servidor, String usuario, String clave){
		try {
			flujoTexto = new Socket(servidor,4444);
			escritor = new PrintWriter(flujoTexto.getOutputStream());
			lector = new BufferedReader(new InputStreamReader(flujoTexto.getInputStream()));			
			escritor.println("/login&"+usuario+"&"+clave);
			escritor.flush();
			String respuesta = "";
			while((respuesta = lector.readLine())!= null){
				if(respuesta.equals("/bienvenido&estudiante") || respuesta.equals("/bienvenido&profesor") ){
					conectado = true;
					System.out.println("conectado");
					HiloEscuchador escuchador = new HiloEscuchador(flujoTexto);	
					Sistema.getInstancia().setMiUsuario(new Usuario(usuario));
					loginIU.autenticado();					
					escuchador.start();
					if(respuesta.equals("/bienvenido&profesor")){
						Sistema.getInstancia().getMiUsuario().setProfesor(true);
					} else if(respuesta.equals("/bienvenido&estudiante")){
						Sistema.getInstancia().getMiUsuario().setProfesor(false);
					}
					
				}else {
					if(respuesta.equals("/denegado")){
						//flujoTexto.close();
						loginIU.recibirError("Nombre de usuario / clave es incorrecto");
					}
					if(respuesta.equals("/denegadoUsuarioConectado")){
						loginIU.recibirError("acceso denegado, "+usuario +" ya esta conectado");
					}
				}
				break;
			}
			
			
		} catch (UnknownHostException e) {
			loginIU.recibirError("No se pudo encontrar esa direccion");			
		} catch (IOException e) {
			loginIU.recibirError("El servidor no acepta conexiones en este momento.");			
		}catch(Exception e){
			loginIU.recibirError("Error inesperado");			
		}
	}
	
	public void entrar(Aula aula){		
		escribir("/entrar&" + aula.getNombre());
	}
	public void solicitarEstudiantesEnLinea(String nombreAula) {
		escribir("/solicitarUsuariosConectados&"+nombreAula);
	}
	public void iniciarClase(Usuario usuario){
		escribir("/iniciarClase&" + usuario);
	}
	public void terminarClase(Usuario usuario){
		escribir("/terminarClase&" + usuario);
	}
	public void solicitarEstadoClase() {
		escribir("/solicitarEstadoClase");
	}
	
	public void salir(Aula aula){		
		escribir("/salir&" + aula.getNombre());
	}
	
	public void solicitarAulas(){
		escribir("/solicitarAulas");
	}
	
	public void dibujarPunto(int xInicio, int yInicio, int xFin, int yFin){
		escribir("/dibujar&" + xInicio+"&" + yInicio + "&" + xFin + "&" + yFin);
	}
	
	private void escribir(String mensaje){
		if (escritor != null){			
			mensaje = mensaje.replace((char)10, '\0'); //No manda el enter al final del mensaje			
			escritor.println(mensaje);
			escritor.flush();
		}
	}
	public void solicitarEstudiantesLevantandoMano(String nombreAula){
		escribir("/solicitarUsuariosLevantandoMano&"+nombreAula);
	}

	public void setUsuariosEnLinea(ArrayList<Usuario> usuariosEnLinea) {
		ModeloUsuariosEnLinea.getInstancia().setUsuarios(usuariosEnLinea);
	}
	
	public void desconectar(){
		if(flujoTexto != null){
			if(Sistema.getInstancia().getMiUsuario() != null){
				escribir("/desconectar&" + Sistema.getInstancia().getMiUsuario());
			}
			try {				
				flujoTexto.close();
				lector.close();
				escritor.close();
				
				flujoTexto = null;
				lector = null;
				escritor = null;
				System.gc();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	public void levantarMano(){
		Usuario usuario = Sistema.getInstancia().getMiUsuario();
		escribir("/levantarMano&"+usuario);
		ModeloUsuariosLevantandoMano.getInstancia().agregar(usuario);
		Conexion.getInstancia().solicitarEstudiantesLevantandoMano(Sistema.getInstancia().getMiAula().toString());
	}
	
	public void enviarMensaje(String mensaje, Usuario usuario) {
		if(usuario != null){
			escribir("/mensaje&" + usuario.getNombreUsuario()+"&" + mensaje);
		}

	}
	public static void main(String[] args) {
		new Conexion();
	}
	public void setChatIU(ChatIU chatIU) {		
		this.chatIU = chatIU;		
	}
	public PizarraIU getPizarra() {
		return pizarra;
	}
	public void setPizarra(PizarraIU pizarra) {
		this.pizarra = pizarra;
	}
	
	public void cederPalabra(Usuario usuario){
		escribir("/cederPalabra&"+usuario);
	}
	
	public void quitarPalabra(){
		escribir("/quitarPalabra");
	}
	
	public void solicitarUsuarioConPalabra() {
		escribir("/solicitarUsuarioConPalabra");
	}
	
	public void enviarImagen(String imagen){
		escribir("/enviarImagen&" +  imagen); 
	}
	
	
}
