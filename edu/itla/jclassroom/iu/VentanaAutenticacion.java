package edu.itla.jclassroom.iu;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import edu.itla.jclassroom.Sistema;
import edu.itla.jclassroom.entidades.Usuario;
import edu.itla.jclassroom.iu.interfaces.AutenticacionIU;
import edu.itla.jclassroom.red.Conexion;

public class VentanaAutenticacion extends  VentanaInterfazDeUsuario  implements AutenticacionIU{

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JLabel lblBienvenida = null;

	private JLabel lblServidor = null;

	private JLabel lblUsuario = null;

	private JLabel lblClave = null;

	private JPasswordField pswClave = null;

	private JTextField txtServidor = null;

	private JTextField txtUsuario = null;

	private JButton btnConectar = null;

	private JButton btnCrearCuenta = null;
	
	private Conexion conexion = null;
	
	private static VentanaAutenticacion instancia;

	/**
	 * This is the default constructor
	 */
	private VentanaAutenticacion() {
		super();
		initialize();
	}
	public static synchronized VentanaAutenticacion getInstancia(){
		if(instancia == null){
			instancia = new VentanaAutenticacion();
		}
		return instancia;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(402, 361);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("JClassRoom");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			lblClave = new JLabel();
			lblClave.setBounds(new Rectangle(14, 187, 69, 26));
			lblClave.setFont(new Font("Dialog", Font.BOLD, 12));
			lblClave.setText("Clave:");
			lblUsuario = new JLabel();
			lblUsuario.setBounds(new Rectangle(14, 144, 69, 26));
			lblUsuario.setFont(new Font("Dialog", Font.BOLD, 12));
			lblUsuario.setText("Usuario:");
			lblServidor = new JLabel();
			lblServidor.setBounds(new Rectangle(14, 101, 69, 26));
			lblServidor.setFont(new Font("Dialog", Font.BOLD, 12));
			lblServidor.setText("Servidor:");
			lblBienvenida = new JLabel();
			lblBienvenida.setBounds(new Rectangle(65, 24, 258, 36));
			lblBienvenida.setFont(new Font("Dialog", Font.BOLD, 18));
			lblBienvenida.setText("Bienvenido a JClassRoom");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setFont(new Font("Dialog", Font.PLAIN, 12));
			jContentPane.add(lblBienvenida, null);
			jContentPane.add(lblServidor, null);
			jContentPane.add(lblUsuario, null);
			jContentPane.add(lblClave, null);
			jContentPane.add(getPswClave(), null);
			jContentPane.add(getTxtServidor(), null);
			jContentPane.add(getTxtUsuario(), null);
			jContentPane.add(getBtnConectar(), null);
			jContentPane.add(getBtnCrearCuenta(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes pswClave	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getPswClave() {
		if (pswClave == null) {
			pswClave = new JPasswordField();
			pswClave.setBounds(new Rectangle(117, 187, 259, 29));
			pswClave.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					if (e.getKeyChar() == KeyEvent.VK_ENTER){
						conectar();
					}
				}
			});
		}
		return pswClave;
	}

	/**
	 * This method initializes txtServidor	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtServidor() {
		if (txtServidor == null) {
			txtServidor = new JTextField();
			txtServidor.setBounds(new Rectangle(117, 101, 259, 29));
			txtServidor.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					if (e.getKeyChar() == KeyEvent.VK_ENTER){
						getTxtUsuario().requestFocus();
					}
				}
			});
		}
		return txtServidor;
	}

	/**
	 * This method initializes txtUsuario	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtUsuario() {
		if (txtUsuario == null) {
			txtUsuario = new JTextField();
			txtUsuario.setBounds(new Rectangle(117, 144, 259, 29));
			txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					if (e.getKeyChar() == KeyEvent.VK_ENTER){
						getPswClave().requestFocus();
					}
				}
			});
		}
		return txtUsuario;
	}
	public String getNombreUsuario(){
		return txtUsuario.getText().toString();
	}
	/**
	 * This method initializes btnConectar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnConectar() {
		if (btnConectar == null) {
			btnConectar = new JButton();
			btnConectar.setBounds(new Rectangle(60, 252, 119, 36));
			btnConectar.setText("Conectar");
			btnConectar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					conectar();
				}
			});
		}
		return btnConectar;
	}
	
	private void conectar() {
		conexion = Conexion.getInstancia();
		conexion.setLoginIU(VentanaAutenticacion.this);
		conexion.conectar(txtServidor.getText(), txtUsuario.getText(), new String(pswClave.getPassword()));	
	}

	/**
	 * This method initializes btnCrearCuenta	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnCrearCuenta() {
		if (btnCrearCuenta == null) {
			btnCrearCuenta = new JButton();
			btnCrearCuenta.setBounds(new Rectangle(197, 251, 119, 36));
			btnCrearCuenta.setText("Crear Cuenta");
			btnCrearCuenta.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					VentanaCuentas.getInstancia().setVisible(true);
					VentanaAutenticacion.this.dispose();
				}
			});
		}
		return btnCrearCuenta;
	}

	public void autenticado() {
		Sistema.getInstancia().setMiUsuario(new Usuario(txtUsuario.getText()));
		VentanaAulas.getInstancia().setVisible(true);
		Conexion.getInstancia().setAulasIU(VentanaAulas.getInstancia());		
		dispose();
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
