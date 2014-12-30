package edu.itla.jclassroom.iu;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.itla.jclassroom.Sistema;
import edu.itla.jclassroom.entidades.Aula;
import edu.itla.jclassroom.iu.interfaces.PermisosIU;
import edu.itla.jclassroom.iu.interfaces.SeleccionDeAulasIU;
import edu.itla.jclassroom.red.Conexion;

public class VentanaAulas extends VentanaInterfazDeUsuario implements SeleccionDeAulasIU {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JList listaAulas = null;

	private JButton btnEntrar = null;

	private JButton btnSalir = null;

	private JLabel lblListaDeAulas = null;
	
	private PermisosIU permisosIU;

	private JScrollPane jspListar = null;

	private static VentanaAulas instancia = null;
	/**
	 * This is the default constructor
	 */
	private VentanaAulas() {
		super();
		Conexion.getInstancia().solicitarAulas();
		initialize();
	}
	public synchronized static VentanaAulas getInstancia(){
		if(instancia == null){
			instancia = new VentanaAulas();
		}
		return instancia;
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(458, 478);
		this.setResizable(false);
		this.setContentPane(getJContentPane());
		this.setTitle("JClassRoom-Selección de Aulas");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			lblListaDeAulas = new JLabel();
			lblListaDeAulas.setBounds(new Rectangle(14, 22, 165, 25));
			lblListaDeAulas.setFont(new Font("Dialog", Font.BOLD, 18));
			lblListaDeAulas.setText("Lista de Aulas: ");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getBtnEntrar(), null);
			jContentPane.add(getBtnSalir(), null);
			jContentPane.add(lblListaDeAulas, null);
			jContentPane.add(getJspListar(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes listaAulas	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getListaAulas() {
		if (listaAulas == null) {
			listaAulas = new JList();
			listaAulas.setToolTipText("Seleccione el aula al que desee entrar");
			listaAulas.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if(e.getClickCount() == 2 ){
						entrar();
					}					
				}
			});
		}
		return listaAulas;
	}

	/**
	 * This method initializes btnEntrar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnEntrar() {
		if (btnEntrar == null) {
			btnEntrar = new JButton();
			btnEntrar.setBounds(new Rectangle(310, 392, 122, 38));
			btnEntrar.setText("Entrar");
			btnEntrar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					entrar();				
				}
			});
		}
		return btnEntrar;
	}
	
	private void entrar(){
		try{
			Sistema.getInstancia().setMiAula(new Aula(listaAulas.getSelectedValue().toString()) );
			Conexion.getInstancia().entrar(Sistema.getInstancia().getMiAula());
			VentanaPrincipal.getInstancia().setVisible(true);
			Conexion.getInstancia().solicitarEstadoClase();
			Conexion.getInstancia().solicitarUsuarioConPalabra();
			this.setPermisosIU(VentanaPrincipal.getInstancia());
			this.getPermisosIU().habilitarPermisos(Sistema.getInstancia().getMiUsuario());
			
			dispose();			
			
		}catch (NullPointerException npe) {
			JOptionPane.showMessageDialog(VentanaAulas.this, "Debe de seleccionar un Aula","Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * This method initializes btnSalir	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSalir() {
		if (btnSalir == null) {
			btnSalir = new JButton();
			btnSalir.setBounds(new Rectangle(14, 392, 123, 38));
			btnSalir.setText("Desconectar");
			btnSalir.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Conexion.getInstancia().desconectar();
					VentanaAutenticacion.getInstancia().setVisible(true);
					VentanaAulas.this.dispose();
				}
			});
		}
		return btnSalir;
	}

	/**
	 * This method initializes jspListar	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJspListar() {
		if (jspListar == null) {
			jspListar = new JScrollPane();
			jspListar.setBounds(new Rectangle(14, 61, 418, 313));
			jspListar.setViewportView(getListaAulas());
		}
		return jspListar;
	}
	public void listarAulas(ArrayList<Aula> aulas) {
		listaAulas.setListData(aulas.toArray());		
	}
	public PermisosIU getPermisosIU() {
		return permisosIU;
	}
	public void setPermisosIU(PermisosIU permisosIU) {
		this.permisosIU = permisosIU;
	}
	public static void setInstancia(VentanaAulas instancia) {
		VentanaAulas.instancia = instancia;
	}	

}  //  @jve:decl-index=0:visual-constraint="10,10"
