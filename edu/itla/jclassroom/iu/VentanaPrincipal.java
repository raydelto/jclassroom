package edu.itla.jclassroom.iu;


import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import edu.itla.jclassroom.Sistema;
import edu.itla.jclassroom.entidades.Aula;
import edu.itla.jclassroom.entidades.Usuario;
import edu.itla.jclassroom.iu.interfaces.ChatIU;
import edu.itla.jclassroom.iu.interfaces.PermisosIU;
import edu.itla.jclassroom.modelos.ModeloUsuariosEnLinea;
import edu.itla.jclassroom.modelos.ModeloUsuariosLevantandoMano;
import edu.itla.jclassroom.red.Conexion;
import edu.itla.jclassroom.voz.ManejadorDeVoz;



public class VentanaPrincipal extends VentanaInterfazDeUsuario implements ChatIU, PermisosIU{

	private static final long serialVersionUID = 1L;
	
	private JFileChooser cuadroDialogoSeleccionarImagen;

	private JPanel jContentPane = null;

	private JLabel lblEstadoClase = null;

	private JList listaEstOnline = null;

	private JList listaEstLevantandoMano = null;

	private JLabel lblEstudiantesLevantandoMano = null;

	private JLabel lblEstudiantesOnline = null;

	private JButton btnSeleccionarRecurso = null;

	private JButton btnCederPalabra = null;

	private JButton btnEnviarMensaje = null;

	private JTextArea txtMensaje = null;

	private JMenuBar barraDeMenu = null;

	private JMenu menuSistema = null;

	private JMenu menuAulas = null;

	private JMenuItem menuItemDesconectar = null;

	private JMenuItem menuItemSalir = null;

	private JMenuItem menuItemListarAulas = null;

	private JPanel panelListas = null;

	private JScrollPane jspMensajes = null;

	private JScrollPane jspMensaje = null;

	private JScrollPane jspListaEstOnLine = null;

	private JScrollPane jpsListaEstLevantandoMano = null;

	private JLabel lblMensaje = null;

	private JButton btnPedirPalabra = null;

	private Conexion conexion = null;

	private JEditorPane edtMensajes = null;
	
	private static VentanaPrincipal instancia;
	
	StringBuffer sbMensajes; //Aca se almacenan todos los mensajes de la ventana principal

	private JPanel panelRecurso = null;

	private JButton btnAnexarFoto = null;

	private JMenu menuClase = null;

	private JMenuItem menuItemIniciarClase = null;

	private JMenuItem menuItemTerminarClase = null;

	private JMenu menuAudio = null;

	private JMenuItem menuItemActivarPronunciacion = null;

	private JMenuItem menuItemDesactivarPronunciacion = null;

	private JLabel lblNombreUsuarioConPalabra = null;

	private JButton btnQuitarPalabra = null;

	private JMenu menuImagen = null;

	private JMenuItem menuItemCargarImagen = null;

	/**
	 * This is the default constructor
	 */
	private VentanaPrincipal() {
		super();
		String separador = System.getProperty("file.separator");
		cuadroDialogoSeleccionarImagen = new JFileChooser(new File("." + separador + "imagenes"));
		conexion = Conexion.getInstancia();
		sbMensajes = new StringBuffer();
		addWindowListener(new WindowAdapter(){			
			public void windowClosing(WindowEvent wevent) {
				System.out.println("Desconectando para cerrar ventana.");
				Conexion.getInstancia().desconectar();
			}
		});
		Conexion.getInstancia().solicitarEstudiantesEnLinea(Sistema.getInstancia().getMiAula().getNombre());
		Conexion.getInstancia().solicitarEstudiantesLevantandoMano(Sistema.getInstancia().getMiAula().getNombre());
		initialize();
	}
	public static synchronized VentanaPrincipal getInstancia(){
		if(instancia == null){
			instancia = new VentanaPrincipal();
			Conexion.getInstancia().setChatIU(instancia);
		}		
		return instancia;
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(729, 635);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setJMenuBar(getBarraDeMenu());
		this.setContentPane(getJContentPane());
		this.setTitle("JClassRoom: Usuario " + Sistema.getInstancia().getMiUsuario()+". Aula " +
				Sistema.getInstancia().getMiAula()+".");
		

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			lblNombreUsuarioConPalabra = new JLabel();
			lblNombreUsuarioConPalabra.setBounds(new Rectangle(115, 513, 504, 12));
			lblNombreUsuarioConPalabra.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNombreUsuarioConPalabra.setHorizontalTextPosition(SwingConstants.RIGHT);
			lblNombreUsuarioConPalabra.setText("");
			lblMensaje = new JLabel();
			lblMensaje.setBounds(new Rectangle(12, 505, 88, 25));
			lblMensaje.setText(" Mensaje:");
			lblEstudiantesOnline = new JLabel();
			lblEstudiantesOnline.setText("Estudiantes en linea:");
			lblEstudiantesOnline.setBounds(new Rectangle(10, 10, 247, 16));
			lblEstudiantesLevantandoMano = new JLabel();
			lblEstudiantesLevantandoMano.setText("Estudiantes Levantando Mano:");
			lblEstudiantesLevantandoMano.setBounds(new Rectangle(10, 166, 264, 28));
			lblEstadoClase = new JLabel();
			lblEstadoClase.setBounds(new Rectangle(14, 14, 270, 26));			
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(lblEstadoClase, null);
			jContentPane.add(getBtnSeleccionarRecurso(), null);
			jContentPane.add(getBtnEnviarMensaje(), null);
			jContentPane.add(getPanelListas(), null);
			jContentPane.add(getJspMensajes(), null);
			jContentPane.add(getJspMensaje(), null);
			jContentPane.add(lblMensaje, null);
			jContentPane.add(getPanelRecurso(), null);
			jContentPane.add(getBtnAnexarFoto(), null);
			jContentPane.add(lblNombreUsuarioConPalabra, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes listaEstOnline	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getListaEstOnline() {
		if (listaEstOnline == null) {
			listaEstOnline = new JList(ModeloUsuariosEnLinea.getInstancia());
		}
		return listaEstOnline;
	}

	/**
	 * This method initializes listaEstLevantandoMano	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getListaEstLevantandoMano() {
		if (listaEstLevantandoMano == null) {			
			listaEstLevantandoMano = new JList(ModeloUsuariosLevantandoMano.getInstancia());
		}
		return listaEstLevantandoMano;
	}

	/**
	 * This method initializes btnSeleccionarRecurso	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSeleccionarRecurso() {
		if (btnSeleccionarRecurso == null) {
			btnSeleccionarRecurso = new JButton();
			btnSeleccionarRecurso.setBounds(new Rectangle(292, 13, 137, 27));
			btnSeleccionarRecurso.setText("Mostrar Pizarra");
			btnSeleccionarRecurso.addActionListener(new java.awt.event.ActionListener() {   
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					if (btnSeleccionarRecurso.getText().equals("Mostrar Pizarra")){
						PanelImagen.getInstancia().setVisible(false);
						PanelPizarra.getInstancia().setVisible(true);
						btnSeleccionarRecurso.setText("Mostrar Imagen");
					}else{
						PanelPizarra.getInstancia().setVisible(false);
						PanelImagen.getInstancia().setVisible(true);
						btnSeleccionarRecurso.setText("Mostrar Pizarra");
					}
				}
			
			});
		}
		return btnSeleccionarRecurso;
	}

	/**
	 * This method initializes btnCederPalabra	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnCederPalabra() {
		if (btnCederPalabra == null) {
			btnCederPalabra = new JButton();
			btnCederPalabra.setText("Ceder");
			btnCederPalabra.setEnabled(false);
			btnCederPalabra.setBounds(new Rectangle(150, 310, 123, 27));
			btnCederPalabra.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try{
						Usuario usuario = new Usuario(listaEstLevantandoMano.getSelectedValue().toString());
						Sistema.getInstancia().getMiAula().getUsuarioConPalabra().setNombreUsuario(usuario.getNombreUsuario());
						Conexion.getInstancia().cederPalabra(usuario);
						sbMensajes.append("<font color=purple>*** Se le ha cedido la palabra a " + usuario+"</font><br>");
						edtMensajes.setText(sbMensajes.toString());
						ModeloUsuariosLevantandoMano.getInstancia().remover(usuario);
						edtMensajes.setCaretPosition(edtMensajes.getDocument().getLength() -1);
						recibirUsuarioConPalabra(usuario);
						btnQuitarPalabra.setEnabled(true);
					}catch(NullPointerException npe){
						JOptionPane.showMessageDialog(VentanaPrincipal.this, "Debe de seleccionar un estudiante","Error",JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
		return btnCederPalabra;
	}

	/**
	 * This method initializes btnEnviarMensaje	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnEnviarMensaje() {
		if (btnEnviarMensaje == null) {
			btnEnviarMensaje = new JButton();
			btnEnviarMensaje.setBounds(new Rectangle(636, 554, 69, 18));
			btnEnviarMensaje.setEnabled(false);
			btnEnviarMensaje.setText("Enviar");
			btnEnviarMensaje.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					enviarMensaje();
					txtMensaje.setText("");
				}
			});
		}
		return btnEnviarMensaje;
	}
	
	private void enviarMensaje(){		
		sbMensajes.append("<b><u>" + Sistema.getInstancia().getMiUsuario().getNombreUsuario() + " Dice:</u></b> " + txtMensaje.getText()+"<br>");
		edtMensajes.setText(sbMensajes.toString());
		conexion.enviarMensaje(txtMensaje.getText(), Sistema.getInstancia().getMiUsuario());
		txtMensaje.setText("");		
	}

	/**
	 * This method initializes txtMensaje	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getTxtMensaje() {
		if (txtMensaje == null) {
			txtMensaje = new JTextArea();
			txtMensaje.addKeyListener(new java.awt.event.KeyAdapter() {   
				public void keyReleased(java.awt.event.KeyEvent e) {    
					if (txtMensaje.getText().length() == 0) {
						btnEnviarMensaje.setEnabled(false);
					}else {
						btnEnviarMensaje.setEnabled(true);
					}					
				}   
				public void keyTyped(java.awt.event.KeyEvent e) {					
					if (e.getKeyChar() == KeyEvent.VK_ENTER){
						if(btnEnviarMensaje.isEnabled()){
							enviarMensaje();
						}
						else{
							txtMensaje.setText("");
						}
					}
				}
			});
		}
		return txtMensaje;
	}

	/**
	 * This method initializes barraDeMenu	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getBarraDeMenu() {
		if (barraDeMenu == null) {
			barraDeMenu = new JMenuBar();
			barraDeMenu.add(getMenuSistema());
			barraDeMenu.add(getMenuAulas());
			barraDeMenu.add(getMenuClase());
			barraDeMenu.add(getMenuAudio());
			barraDeMenu.add(getMenuImagen());
		}
		return barraDeMenu;
	}

	/**
	 * This method initializes menuSistema	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMenuSistema() {
		if (menuSistema == null) {
			menuSistema = new JMenu();
			menuSistema.setText("Sistema");
			menuSistema.add(getMenuItemDesconectar());
			menuSistema.add(getMenuItemSalir());
		}
		return menuSistema;
	}

	/**
	 * This method initializes menuAulas	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMenuAulas() {
		if (menuAulas == null) {
			menuAulas = new JMenu();
			menuAulas.setText("Aulas");
			menuAulas.add(getMenuItemListarAulas());
		}
		return menuAulas;
	}

	/**
	 * This method initializes menuItemDesconectar	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMenuItemDesconectar() {
		if (menuItemDesconectar == null) {
			menuItemDesconectar = new JMenuItem();
			menuItemDesconectar.setText("Desconectar");
			menuItemDesconectar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Conexion.getInstancia().desconectar();
					setInstancia(null);
					VentanaAulas.setInstancia(null);
					VentanaAutenticacion.getInstancia().setVisible(true);
					dispose();
				}
			});
		}
		return menuItemDesconectar;
	}

	/**
	 * This method initializes menuItemSalir	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMenuItemSalir() {
		if (menuItemSalir == null) {
			menuItemSalir = new JMenuItem();
			menuItemSalir.setText("Salir");
			menuItemSalir.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Conexion.getInstancia().desconectar();
					System.exit(0);
				}
			});
		}
		return menuItemSalir;
	}

	/**
	 * This method initializes menuItemListarAulas	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMenuItemListarAulas() {
		if (menuItemListarAulas == null) {
			menuItemListarAulas = new JMenuItem();
			menuItemListarAulas.setText("Listar Aulas");
			menuItemListarAulas.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setInstancia(null); //Elimina la instancia actual de la ventana principal
					dispose();
					Conexion.getInstancia().salir(Sistema.getInstancia().getMiAula());
					Sistema.getInstancia().setMiAula(null);					
					VentanaAulas.getInstancia().setVisible(true);
				}
			});
		}
		return menuItemListarAulas;
	}

	
	private void setInstancia(VentanaPrincipal instancia){
		VentanaPrincipal.instancia = instancia;
	}
	/**
	 * This method initializes panelListas	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelListas() {
		if (panelListas == null) {
			panelListas = new JPanel();
			panelListas.setLayout(null);
			panelListas.setBounds(new Rectangle(438, 14, 279, 345));
			panelListas.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			panelListas.add(lblEstudiantesOnline, null);
			panelListas.add(lblEstudiantesLevantandoMano, null);
			panelListas.add(getBtnCederPalabra(), null);
			panelListas.add(getJspListaEstOnLine(), null);
			panelListas.add(getJpsListaEstLevantandoMano(), null);
			panelListas.add(getBtnPedirPalabra(), null);
			panelListas.add(getBtnQuitarPalabra(), null);
		}
		return panelListas;
	}

	/**
	 * This method initializes jspMensajes	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJspMensajes() {
		if (jspMensajes == null) {
			jspMensajes = new JScrollPane();
			jspMensajes.setBounds(new Rectangle(12, 371, 706, 135));
			jspMensajes.setViewportView(getEdtMensajes());
		}
		return jspMensajes;
	}

	/**
	 * This method initializes jspMensaje	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJspMensaje() {
		if (jspMensaje == null) {
			jspMensaje = new JScrollPane();
			jspMensaje.setBounds(new Rectangle(12, 530, 608, 45));
			jspMensaje.setViewportView(getTxtMensaje());
		}
		return jspMensaje;
	}

	/**
	 * This method initializes jspListaEstOnLine	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJspListaEstOnLine() {
		if (jspListaEstOnLine == null) {
			jspListaEstOnLine = new JScrollPane();
			jspListaEstOnLine.setBounds(new Rectangle(10, 30, 266, 131));
			jspListaEstOnLine.setViewportView(getListaEstOnline());
		}
		return jspListaEstOnLine;
	}

	/**
	 * This method initializes jpsListaEstLevantandoMano	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJpsListaEstLevantandoMano() {
		if (jpsListaEstLevantandoMano == null) {
			jpsListaEstLevantandoMano = new JScrollPane();
			jpsListaEstLevantandoMano.setBounds(new Rectangle(10, 202, 266, 101));
			jpsListaEstLevantandoMano.setViewportView(getListaEstLevantandoMano());
		}
		return jpsListaEstLevantandoMano;
	}

	/**
	 * This method initializes btnPedirPalabra	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnPedirPalabra() {
		if (btnPedirPalabra == null) {
			btnPedirPalabra = new JButton();
			btnPedirPalabra.setBounds(new Rectangle(11, 311, 123, 26));
			btnPedirPalabra.setText("Levantar Mano");
			btnPedirPalabra.setVisible(false);
			btnPedirPalabra.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Conexion.getInstancia().levantarMano();	
					btnPedirPalabra.setEnabled(false);
				}
			});
		}
		return btnPedirPalabra;
	}

	/**
	 * This method initializes edtMensajes	
	 * 	
	 * @return javax.swing.JEditorPane	
	 */
	private JEditorPane getEdtMensajes() {
		if (edtMensajes == null) {
			edtMensajes = new JEditorPane();
			edtMensajes.setContentType("text/html");
			edtMensajes.setEditable(false);
			edtMensajes.setText("");
		}
		return edtMensajes;
	}

	public void recibirMensaje(String mensaje, Usuario usuario) {				
		sbMensajes.append("<b><u>"+ usuario.getNombreUsuario()+" Dice:</u></b> " +mensaje + "<br>");
		edtMensajes.setText(sbMensajes.toString());
		edtMensajes.setCaretPosition(edtMensajes.getDocument().getLength() -1);

		//Pronunciar texto si la opcion esta habilitada
		if(!menuItemActivarPronunciacion.isEnabled()){
			if(mensaje.indexOf("<img") == -1){
				ManejadorDeVoz.getInstancia().decir(mensaje);
			}
		}
	}
	public void notificarPalabraCedida(Usuario usuario) {
		sbMensajes.append("<font color=purple>*** Se le ha cedido la palabra a " + usuario+"</font><br>");
		edtMensajes.setText(sbMensajes.toString());
		edtMensajes.setCaretPosition(edtMensajes.getDocument().getLength() -1);
		ModeloUsuariosLevantandoMano.getInstancia().remover(usuario);
		if (Sistema.getInstancia().getMiUsuario().equals(usuario)) {
			txtMensaje.setEnabled(true);
		}
		
	}
	
	public void notificarPalabraQuitada(Usuario usuario) {
		sbMensajes.append("<font color=purple>*** Se le ha quitado la palabra a " + usuario+"</font><br>");
		edtMensajes.setText(sbMensajes.toString());
		edtMensajes.setCaretPosition(edtMensajes.getDocument().getLength() -1);
		lblNombreUsuarioConPalabra.setText("");
		if (Sistema.getInstancia().getMiUsuario().equals(usuario)) {
			btnPedirPalabra.setEnabled(true);
			txtMensaje.setEnabled(false);
		}
	}
	
	public void entroUsuario(Usuario usuario, Aula aula) {
		if(aula.equals(Sistema.getInstancia().getMiAula())){
			ModeloUsuariosEnLinea.getInstancia().agregar(usuario);
			sbMensajes.append("<font color = blue><b><i>*** "+ usuario.getNombreUsuario()+" Ha entrado al aula.</i></b></font><br>");
			edtMensajes.setText(sbMensajes.toString());
			edtMensajes.setCaretPosition(edtMensajes.getDocument().getLength() -1);
			
			//Pronunciar texto si la opcion esta habilitada
			if(!menuItemActivarPronunciacion.isEnabled()){				
				ManejadorDeVoz.getInstancia().decir(usuario + " has joined this classroom");				
			}
		}
	}
	
	public void salioUsuario(Usuario usuario, Aula aula) {
		System.out.println("Salio "+ usuario + " de " + aula);
		if(aula.equals(Sistema.getInstancia().getMiAula())){
			ModeloUsuariosEnLinea.getInstancia().remover(usuario);
			ModeloUsuariosLevantandoMano.getInstancia().remover(usuario);
			sbMensajes.append("<font color = purple><b><i>*** "+ usuario.getNombreUsuario()+" Ha salido del aula.</i></b></font><br>");
			edtMensajes.setText(sbMensajes.toString());
			edtMensajes.setCaretPosition(edtMensajes.getDocument().getLength() -1);
			
			//Pronunciar texto si la opcion esta habilitada
			if(!menuItemActivarPronunciacion.isEnabled()){				
				ManejadorDeVoz.getInstancia().decir(usuario + " has left this classroom");				
			}			
		}	
	}
	/**
	 * This method initializes panelRecurso	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelRecurso() {
		if (panelRecurso == null) {
			panelRecurso = new JPanel();
			panelRecurso.setLayout(null);			
			panelRecurso.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			PanelPizarra.getInstancia().setBounds(new Rectangle(0, 0, 415, 316));
			PanelImagen.setInstancia(null);
			PanelImagen.getInstancia().setBounds(new Rectangle(0, 0, 415, 316));
			PanelPizarra.getInstancia().setVisible(false);
			panelRecurso.add(PanelPizarra.getInstancia());			
			panelRecurso.add(PanelImagen.getInstancia());			
			panelRecurso.setBounds(new Rectangle(14, 43, 415, 316));			
		}
		return panelRecurso;
	}
	public void recibirListadoUsuariosLevantandoMano(ArrayList<Usuario> usuarios) {
		ModeloUsuariosLevantandoMano.getInstancia().setUsuarios(usuarios);
	}
	public void levantarMano(Usuario usuario) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * This method initializes btnAnexarFoto	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnAnexarFoto() {
		if (btnAnexarFoto == null) {
			btnAnexarFoto = new JButton();
			btnAnexarFoto.setBounds(new Rectangle(636, 531, 69, 18));
			btnAnexarFoto.setText("Imagen");
			btnAnexarFoto.setEnabled(true);
			btnAnexarFoto.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String imagen = JOptionPane.showInputDialog(VentanaPrincipal.this,
							"Inserte direccio URL de la foto:\nEjemplo: http://www.raydelto.org/images/rayalpha.gif",
							"Direccion", JOptionPane.QUESTION_MESSAGE);	
					if(!imagen.equals("")){
						sbMensajes.append("<img src=\""+imagen+"\"/><br>");
						edtMensajes.setText("<b><i>Mostrando imagen(" + imagen + "): </b></i><br>"+ sbMensajes.toString());					
						conexion.enviarMensaje(sbMensajes.toString(), Sistema.getInstancia().getMiUsuario());
					}

				}
			});
		}
		return btnAnexarFoto;
	}
	/**
	 * This method initializes menuClase	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMenuClase() {
		if (menuClase == null) {
			menuClase = new JMenu();
			menuClase.setText("Clase");
			menuClase.add(getMenuItemIniciarClase());
			menuClase.add(getMenuItemTerminarClase());
		}
		return menuClase;
	}
	/**
	 * This method initializes menuItemIniciarClase	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMenuItemIniciarClase() {
		if (menuItemIniciarClase == null) {
			menuItemIniciarClase = new JMenuItem();
			menuItemIniciarClase.setText("Iniciar clase");
			menuItemIniciarClase.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Conexion.getInstancia().iniciarClase(Sistema.getInstancia().getMiUsuario());
					recibirEstadoClase("iniciada");
					menuItemIniciarClase.setEnabled(false);
					menuItemTerminarClase.setEnabled(true);
					btnCederPalabra.setEnabled(true);
				}
			});
		}
		return menuItemIniciarClase;
	}
	/**
	 * This method initializes menuItemTerminarClase	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMenuItemTerminarClase() {
		if (menuItemTerminarClase == null) {
			menuItemTerminarClase = new JMenuItem();
			menuItemTerminarClase.setText("Terminar clase");
			menuItemTerminarClase.setEnabled(false);
			menuItemTerminarClase.setMnemonic(KeyEvent.VK_UNDEFINED);
			menuItemTerminarClase.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Conexion.getInstancia().terminarClase(Sistema.getInstancia().getMiUsuario());
					recibirEstadoClase("terminada");
					menuItemIniciarClase.setEnabled(true);
					menuItemTerminarClase.setEnabled(false);
					btnCederPalabra.setEnabled(false);
					btnQuitarPalabra.setEnabled(false);
				}
			});
		}
		return menuItemTerminarClase;
	}
	/**
	 * This method initializes menuAudio	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMenuAudio() {
		if (menuAudio == null) {
			menuAudio = new JMenu();
			menuAudio.setText("Audio");
			menuAudio.add(getMenuItemActivarPronunciacion());
			menuAudio.add(getMenuItemDesactivarPronunciacion());
		}
		return menuAudio;
	}
	/**
	 * This method initializes menuItemActivarPronunciacion	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMenuItemActivarPronunciacion() {
		if (menuItemActivarPronunciacion == null) {
			menuItemActivarPronunciacion = new JMenuItem();
			menuItemActivarPronunciacion.setText("Activar Pronunciacion");
			menuItemActivarPronunciacion
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							menuItemActivarPronunciacion.setEnabled(false);
							menuItemDesactivarPronunciacion.setEnabled(true);
						}
					});
		}
		return menuItemActivarPronunciacion;
	}
	/**
	 * This method initializes menuItemDesactivarPronunciacion	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMenuItemDesactivarPronunciacion() {
		if (menuItemDesactivarPronunciacion == null) {
			menuItemDesactivarPronunciacion = new JMenuItem();
			menuItemDesactivarPronunciacion.setEnabled(false);
			menuItemDesactivarPronunciacion.setText("Desactivar Pronunciacion");
			menuItemDesactivarPronunciacion
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							menuItemDesactivarPronunciacion.setEnabled(false);
							menuItemActivarPronunciacion.setEnabled(true);						
						}
					});
		}
		return menuItemDesactivarPronunciacion;
	}
	public void presentarPizarra() {
		getBtnSeleccionarRecurso().setText("Mostrar Imagen");
		PanelPizarra.getInstancia().setVisible(true);
		PanelImagen.getInstancia().setVisible(false);	
	}
	
	public void recibirUsuarioConPalabra(Usuario usuario) {
		if (usuario.getNombreUsuario() == null) {
			return;
		}
		if (usuario.getNombreUsuario().equals("")) {
			lblNombreUsuarioConPalabra.setText("");	
		} else {
			lblNombreUsuarioConPalabra.setText(usuario.getNombreUsuario() + " tiene la palabra");
			getBtnQuitarPalabra().setEnabled(true); //Solo es visible para los profesores
		}
	}
	public void iniciarClase() {
		VentanaPrincipal.getInstancia().getTxtMensaje().setEnabled(false);
		
	}
	public void terminarClase() {
		VentanaPrincipal.getInstancia().getTxtMensaje().setEnabled(true);
		
	}
	/**
	 * This method initializes btnQuitarPalabra	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnQuitarPalabra() {
		if (btnQuitarPalabra == null) {
			btnQuitarPalabra = new JButton();
			btnQuitarPalabra.setBounds(new Rectangle(11, 311, 123, 27));
			btnQuitarPalabra.setText("Quitar Palabra");
			btnQuitarPalabra.setEnabled(false);
			btnQuitarPalabra.setVisible(true);
			btnQuitarPalabra.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Conexion.getInstancia().quitarPalabra();
					sbMensajes.append("<font color=purple>*** Se le ha quitado la palabra a " + Sistema.getInstancia().getMiAula().getUsuarioConPalabra()+"</font><br>");
					edtMensajes.setText(sbMensajes.toString());
					edtMensajes.setCaretPosition(edtMensajes.getDocument().getLength() -1);
					lblNombreUsuarioConPalabra.setText("");
					btnQuitarPalabra.setEnabled(false);
				}
			});
		}
		return btnQuitarPalabra;
	}
	public void recibirEstadoClase(String estado) {
		lblEstadoClase.setText("Estado clase: " + estado);
		if(estado.equals("iniciada") ){
			if (!Sistema.getInstancia().getMiAula().getProfesor().equals(Sistema.getInstancia().getMiUsuario().getNombreUsuario())){
				VentanaPrincipal.getInstancia().getTxtMensaje().setEnabled(false);
			}else{
				getMenuItemTerminarClase().setEnabled(true);
				getMenuItemIniciarClase().setEnabled(false);
				getBtnCederPalabra().setEnabled(true);	
			}
		}
	}
	public void habilitarPermisos(Usuario usuario) {
		if (!usuario.isProfesor() || (usuario.isProfesor() && !Sistema.getInstancia().getMiAula().getProfesor().equals(usuario.getNombreUsuario()))) {
			Conexion.getInstancia().solicitarEstadoClase();
			getMenuClase().setVisible(false);
			getBtnCederPalabra().setVisible(false);
			getBtnQuitarPalabra().setVisible(false);
			getBtnPedirPalabra().setVisible(true);
			getMenuImagen().setVisible(false);
			System.out.println(Sistema.getInstancia().getMiAula().getDocencia().getEstadoClase());						
		}
		
	}

	public void recibirAdvertencia(String advertencia) {
		super.recibirAdvertencia(advertencia);
		if(advertencia.equals("Se ha perdido la conexion con el servidor")){
			setInstancia(null);
			dispose();
			Sistema.getInstancia().setMiAula(null);
			Sistema.getInstancia().setMiUsuario(null);
			VentanaAulas.setInstancia(null);
			VentanaAutenticacion.getInstancia().setVisible(true);
		}
	}
	/**
	 * This method initializes menuImagen	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMenuImagen() {
		if (menuImagen == null) {
			menuImagen = new JMenu();
			menuImagen.setText("Imagen");
			menuImagen.add(getMenuItemCargarImagen());
		}
		return menuImagen;
	}
	/**
	 * This method initializes menuItemCargarImagen	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMenuItemCargarImagen() {
		if (menuItemCargarImagen == null) {
			menuItemCargarImagen = new JMenuItem();
			menuItemCargarImagen.setText("Cargar Imagen");
			menuItemCargarImagen.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cuadroDialogoSeleccionarImagen.showOpenDialog(VentanaPrincipal.this);
					File imagenSeleccionada = cuadroDialogoSeleccionarImagen.getSelectedFile();					
					if(imagenSeleccionada != null){
						PanelImagen.getInstancia().cambiarImagen(new ImageIcon(imagenSeleccionada.getPath()));
						Conexion.getInstancia().enviarImagen(imagenSeleccionada.getName());
					}

				}
			});
		}
		return menuItemCargarImagen;
	}
	public void recibirImagen(String imagen) {
		String separador = System.getProperty("file.separator");
		PanelImagen.getInstancia().cambiarImagen(new ImageIcon("." +separador + "imagenes" + separador + imagen));
		
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
