package edu.itla.jclassroom.voz;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import edu.itla.jclassroom.red.Conexion;
public class ManejadorDeVoz {
	private VoiceManager administradorDeVoz;
	private Voice voz;
	private String texto;
	private static ManejadorDeVoz instancia;
	
	public synchronized static ManejadorDeVoz getInstancia(){
		if (instancia == null){
			instancia = new ManejadorDeVoz();
		}
		return instancia;
	}
	
	private String getTexto() {
		return texto;
	}

	private void setTexto(String texto) {
		this.texto = texto;
	}

	private ManejadorDeVoz(){
		administradorDeVoz = VoiceManager.getInstance();
        voz = administradorDeVoz.getVoice("kevin16");
        if (voz == null) {
        	String error = "Error al cargar la voz.";
        	Conexion.getInstancia().getChatIU().recibirError(error);
            System.err.println(error);
            return;
        }
        voz.allocate();        
	}

	protected void finalize() throws Throwable {
		super.finalize();
		voz.deallocate();		
	}
	
	public void decir(String texto){
		setTexto(texto);
		Thread hilo = new Thread(new Runnable(){
			public void run() {
				voz.speak(getTexto());				
			}			
		});
		hilo.start();
	}	
}
