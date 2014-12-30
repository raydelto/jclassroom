package edu.itla.jclassroom.iu;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

import edu.itla.jclassroom.iu.interfaces.PizarraIU;
import edu.itla.jclassroom.red.Conexion;

public class PanelPizarra extends JPanel implements PizarraIU{

	private static final long serialVersionUID = 1L;
	private int xInicial; //posicion de X al iniciar a dibujar  
	private int yInicial; //posicion de Y al iniciar a dibujar
	private static PanelPizarra instancia;
	
	public synchronized static PanelPizarra getInstancia(){
		if(instancia == null){
			instancia = new PanelPizarra();
			Conexion.getInstancia().setPizarra(instancia);
		}
		return instancia;
	}
	private PanelPizarra(){
		setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		setBackground(new Color(19, 106, 37)); //Poner fondo Color verde pizarra		
        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent ev) {
                xInicial=ev.getX();
                yInicial=ev.getY();
            }        	
        });
        addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseDragged(MouseEvent ev) {
                int x = ev.getX(); //Posicion actual de x
                int y = ev.getY(); //Posicion actual de y
                Graphics g = getGraphics();
                g.setColor(Color.lightGray);
                g.drawLine(xInicial, yInicial, x, y);
                Conexion.getInstancia().dibujarPunto(xInicial, yInicial, x, y);
                xInicial = x;   
                yInicial = y;
                g.dispose();
            }
        });
	}

	public void recibirPuntos(int xInicio, int yInicio, int xFin, int yFin) {
        Graphics g = getGraphics();
        g.setColor(Color.white);
        g.drawLine(xInicio, yInicio, xFin, yFin);
        g.dispose();
	}
}
