package edu.itla.jclassroom.iu;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import edu.itla.jclassroom.entidades.Usuario;
import edu.itla.jclassroom.iu.interfaces.RegistroUsuariosIU;
import edu.itla.jclassroom.red.Conexion;

public class VentanaCuentas extends VentanaInterfazDeUsuario implements RegistroUsuariosIU{

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JLabel lblNombre = null;

	private JLabel lblApellido = null;

	private JLabel lblNombreUsuario = null;

	private JLabel lbClave = null;

	private JLabel lblConfirmacionClave = null;

	private JTextField txtNombre = null;

	private JTextField txtApellido = null;

	private JTextField txtNombreUsuario = null;

	private JPasswordField pswClave = null;

	private JPasswordField pswConfirmacionClave = null;

	private JButton btnCrear = null;

	private JButton btnRegresar = null;
	
	private static VentanaCuentas instancia;

	private JLabel lblHost = null;

	private JTextField txtServidor = null;

	/**
	 * This is the default constructor
	 */
	private VentanaCuentas() {
		super();
		initialize();
	}

	public static synchronized VentanaCuentas getInstancia(){
		if(instancia == null){
			instancia = new VentanaCuentas();
		}
		return instancia;
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(357, 352);
		this.setResizable(false);
		this.setContentPane(getJContentPane());
		this.setTitle("JClassRoom-Cuentas de Usuario");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			lblHost = new JLabel();
			lblHost.setBounds(new Rectangle(11, 27, 97, 25));
			lblHost.setText("Servidor:");
			lblConfirmacionClave = new JLabel();
			lblConfirmacionClave.setBounds(new Rectangle(11, 215, 97, 25));
			lblConfirmacionClave.setText("Confirmar Clave:");
			lbClave = new JLabel();
			lbClave.setBounds(new Rectangle(11, 177, 97, 25));
			lbClave.setText("Clave:");
			lblNombreUsuario = new JLabel();
			lblNombreUsuario.setBounds(new Rectangle(11, 139, 97, 25));
			lblNombreUsuario.setText("Nombre Usuario:");
			lblApellido = new JLabel();
			lblApellido.setBounds(new Rectangle(11, 101, 97, 25));
			lblApellido.setText("Apellido:");
			lblNombre = new JLabel();
			lblNombre.setBounds(new Rectangle(11, 63, 97, 25));
			lblNombre.setText("Nombre:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(lblNombre, null);
			jContentPane.add(lblNombre, null);
			jContentPane.add(lblApellido, null);
			jContentPane.add(lblNombreUsuario, null);
			jContentPane.add(lbClave, null);
			jContentPane.add(lblConfirmacionClave, null);
			jContentPane.add(getTxtNombre(), null);
			jContentPane.add(getTxtApellido(), null);
			jContentPane.add(getTxtNombreUsuario(), null);
			jContentPane.add(getPswClave(), null);
			jContentPane.add(getPswConfirmacionClave(), null);
			jContentPane.add(getBtnCrear(), null);
			jContentPane.add(getBtnRegresar(), null);
			jContentPane.add(lblHost, null);
			jContentPane.add(getTxtServidor(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes txtNombre	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtNombre() {
		if (txtNombre == null) {
			txtNombre = new JTextField();
			txtNombre.setBounds(new Rectangle(126, 63, 207, 25));
			txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					if (e.getKeyChar() == KeyEvent.VK_ENTER){
						getTxtApellido().requestFocus();
					}
				}
			});
		}
		return txtNombre;
	}

	/**
	 * This method initializes txtApellido	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtApellido() {
		if (txtApellido == null) {
			txtApellido = new JTextField();
			txtApellido.setBounds(new Rectangle(126, 101, 207, 25));
			txtApellido.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					if (e.getKeyChar() == KeyEvent.VK_ENTER){
						getTxtNombreUsuario().requestFocus();
					}
				}
			});
		}
		return txtApellido;
	}

	/**
	 * This method initializes txtNombreUsuario	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtNombreUsuario() {
		if (txtNombreUsuario == null) {
			txtNombreUsuario = new JTextField();
			txtNombreUsuario.setBounds(new Rectangle(126, 139, 207, 25));
			txtNombreUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					if (e.getKeyChar() == KeyEvent.VK_ENTER){
						getPswClave().requestFocus();
					}
				}
			});
		}
		return txtNombreUsuario;
	}

	/**
	 * This method initializes pswClave	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getPswClave() {
		if (pswClave == null) {
			pswClave = new JPasswordField();
			pswClave.setBounds(new Rectangle(126, 177, 207, 25));
			pswClave.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					if (e.getKeyChar() == KeyEvent.VK_ENTER){
						getPswConfirmacionClave().requestFocus();
					}
				}
			});
		}
		return pswClave;
	}

	/**
	 * This method initializes pswConfirmacionClave	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getPswConfirmacionClave() {
		if (pswConfirmacionClave == null) {
			pswConfirmacionClave = new JPasswordField();
			pswConfirmacionClave.setBounds(new Rectangle(126, 215, 207, 25));
			pswConfirmacionClave.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					if (e.getKeyChar() == KeyEvent.VK_ENTER){
						if (isInformacionCompleta()) {
							Conexion.getInstancia().setRegistroUsuarioIU(VentanaCuentas.this);
							Conexion.getInstancia().registrar(txtServidor.getText(), new Usuario(txtNombre.getText(),txtApellido.getText(),txtNombreUsuario.getText(),new String (pswClave.getPassword())));	
						}
					}
				}
			});
		}
		return pswConfirmacionClave;
	}

	/**
	 * This method initializes btnCrear	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnCrear() {
		if (btnCrear == null) {
			btnCrear = new JButton();
			btnCrear.setBounds(new Rectangle(49, 260, 113, 32));
			btnCrear.setText("Crear Cuenta");
			btnCrear.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {									
					if (isInformacionCompleta()) {
						Conexion.getInstancia().setRegistroUsuarioIU(VentanaCuentas.this);
						Conexion.getInstancia().registrar(txtServidor.getText(), new Usuario(txtNombre.getText(),txtApellido.getText(),txtNombreUsuario.getText(),new String (pswClave.getPassword())));
					}
				}
			});
		}
		return btnCrear;
	}

	/**
	 * This method initializes btnRegresar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnRegresar() {
		if (btnRegresar == null) {
			btnRegresar = new JButton();
			btnRegresar.setBounds(new Rectangle(173, 260, 113, 32));
			btnRegresar.setText("Regresar");
			btnRegresar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					VentanaAutenticacion.getInstancia().setVisible(true);
					VentanaCuentas.this.dispose();
				}
			});
		}
		return btnRegresar;
	}

	public void recibirConfirmacionRegistro(String mensaje) {
		txtServidor.setText("");
		txtApellido.setText("");
		txtNombre.setText("");
		txtNombreUsuario.setText("");
		pswClave.setText("");
		pswConfirmacionClave.setText("");
		JOptionPane.showMessageDialog(this, mensaje,"Confirmación",JOptionPane.INFORMATION_MESSAGE);		
	}
	
	private boolean isCampoVacio(Object campo, String mensajeError) {
		if (campo instanceof JTextField) {
			JTextField txt = (JTextField) campo;
			if (txt.getText().length()==0) {
				VentanaCuentas.this.recibirError(mensajeError);
				txt.requestFocus();
				return true;
			}
			
		}else if (campo instanceof JPasswordField) {
			VentanaCuentas.this.recibirError(mensajeError);
			JPasswordField txt = (JPasswordField) campo;
			if (new String(txt.getPassword()).length()==0) {
				VentanaCuentas.this.recibirError(mensajeError);
				txt.requestFocus();
				return true;
			}
		}
		return false;
	}

	/**
	 * This method initializes txtServidor	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtServidor() {
		if (txtServidor == null) {
			txtServidor = new JTextField();
			txtServidor.setBounds(new Rectangle(126, 27, 207, 25));
			txtServidor.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					if (e.getKeyChar() == KeyEvent.VK_ENTER){
						getTxtNombre().requestFocus();
					}
				}
			});
		}
		return txtServidor;
	}
	private boolean isInformacionCompleta() {
		if (isCampoVacio(txtServidor, "Todos los campos son requeridos, inserte el nombre del servidor") || 
				isCampoVacio(txtNombre, "Todos los campos son requeridos, inserte su nombre") || 
				isCampoVacio(txtApellido,"Todos los campos son requeridos, inserte su apellido") || 
				isCampoVacio(txtNombreUsuario, "Todos los campos son requeridos, inserte su nombre de usuario") ||
				isCampoVacio(pswClave, "Todos los campos son requeridos, inserte su clave")) {
			return false;
		}						
		
		if (!new String(pswClave.getPassword()).equals(new String(pswConfirmacionClave.getPassword()))) {
			VentanaCuentas.this.recibirError("La clave y la confirmación nos son iguales, pruebe de nuevo");
			pswClave.requestFocus();
			return false;
		}
		return true;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
