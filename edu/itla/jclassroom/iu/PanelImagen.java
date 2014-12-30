package edu.itla.jclassroom.iu;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class PanelImagen extends JPanel{
	private static final long serialVersionUID = 1L;
	private ImageIcon imagen ;
	private static PanelImagen instancia;	
	
	public static void setInstancia(PanelImagen instancia) {
		PanelImagen.instancia = instancia;
	}

	public synchronized static PanelImagen getInstancia(){
		if(instancia == null){
			instancia = new PanelImagen();
		}
		return instancia;
	}

	private PanelImagen(){
		imagen = new ImageIcon("bienvenido.jpg");
	}	
	
	public ImageIcon getImagen() {
		return imagen;
	}

	private void setImagen(ImageIcon imagen) {
		this.imagen = imagen;
	}
	
	public void cambiarImagen(ImageIcon imagen){
		setImagen(imagen);
		repaint();
		
	}
	

	protected void paintComponent(Graphics g) {		
		if (imagen != null){
			g.drawImage(imagen.getImage(),0,0,getSize().width, getSize().height,null);
			setOpaque(false);
		}else{
			super.paintComponent(g);
		}
	}
}

