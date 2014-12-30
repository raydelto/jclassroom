package edu.itla.jclassroom.iu;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import edu.itla.jclassroom.iu.interfaces.InterfazDeUsuario;

public abstract class VentanaInterfazDeUsuario extends JFrame implements InterfazDeUsuario{

	private static final long serialVersionUID = 1L;
	
	{
	//Bloque estatico (Se ejecuta primero que el constructor)
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public VentanaInterfazDeUsuario(){
		setIconImage(new ImageIcon("iconojc.jpg").getImage());
	}

	public void recibirAdvertencia(String advertencia) {
		try{
			JOptionPane.showMessageDialog(this,advertencia,"Advertencia",JOptionPane.INFORMATION_MESSAGE);
		}catch(InternalError ie){
			System.out.println(advertencia);
		}catch(NullPointerException npe){
			System.out.println(advertencia);
		}catch(Exception e){
			System.out.println(advertencia);
		}
	}

	public void recibirError(String error) {
		JOptionPane.showMessageDialog(this,error,"Error",JOptionPane.ERROR_MESSAGE);
	}

}
