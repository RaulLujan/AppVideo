package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import controlador.Controlador;

public class Login extends JPanel  {//implements KeyListener{
	
	public Login(Ventana parent) {
		
		// CONF LAMINA
		setLayout(new GridBagLayout());
		setBackground(new Color(96, 96, 96));
		
		// addTitulo
		TitledBorder titulo = new TitledBorder("Login");
		titulo.setTitleColor(Color.WHITE);
		titulo.setTitleFont(new Font("Arial", Font.BOLD, 25));
		setBorder(titulo);
		
		// addEtiquetas
		addLabel("Nombre: ", 0);
		addLabel("Password: ", 1);
		
		// addCampos
		JTextField tfNombre = addTextField(false, 0);
		tfNombre.requestFocus();
		tfNombre.selectAll();
		JPasswordField pfPass = (JPasswordField) addTextField(true, 1);
		
		// addLabelErrorCampo
		JLabel errorCampo = addLabelErrorCampo();
		
		// addBotones
		// addBotonAceptar
		JButton bAceptar = addButton("Aceptar", 0, 2);
		bAceptar.addActionListener(e -> {
			// aceptar
			String pass = new String(pfPass.getPassword());
			
			boolean isOKLoginCampos = false;
			if (tfNombre.getText().trim().isEmpty()) {
				errorCampo.setText("Usuario no valido");
			} else if (pass.trim().isEmpty()) {
				errorCampo.setText("Contraseña no valida");
			} else {
				isOKLoginCampos = true;
			}
			
			if (isOKLoginCampos) {
				errorCampo.setVisible(false);
				boolean isLogin = Controlador.getInstancia().loginUsuario(
						tfNombre.getText(), pass);
			
				if (isLogin) {
					parent.setLaminaCentral("Recientes");
					parent.mostrarLaminaSuperior();
				
				} else {
					JOptionPane.showMessageDialog(this,
							"Nombre de usuario o contraseña no valido",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
		
			} else {
				errorCampo.setVisible(true);
			}
			revalidate();
			repaint();
		});
//		bAceptar.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyPressed(KeyEvent e) {
//				aceptar();
//			}
//	    });
		// addBotonCancelar
		JButton bCancelar = addButton("Cancelar", 1, 2);
		bCancelar.addActionListener(e -> {
			// cancelar
			tfNombre.setText("");
			pfPass.setText("");
		});
//		bCancelar.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyPressed(KeyEvent e) {
//				cancelar();
//			}
//	    });
		
//		addListenerTeclado();
//		addKeyListener(this);
//		requestFocus();
//		setFocusable(true);
	}

	private JLabel addLabel(String texto, int y) {
		JLabel label = new JLabel(texto);
		label.setFont(new Font("Arial", Font.BOLD, 15));
		label.setForeground((Color.WHITE));
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(20, 10, 20, 5);
		constraints.anchor = GridBagConstraints.EAST;
		constraints.gridx = 0;
		constraints.gridy = y;
		
		add(label, constraints);
		return label;
	}

	private JTextField addTextField(boolean pass, int y) {
		JTextField textField = pass ? new JPasswordField() : new JTextField();
		textField.setPreferredSize(new Dimension(200, 20));
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 5, 20, 15);
		constraints.fill = GridBagConstraints.EAST;
		constraints.gridx = 1;
		constraints.gridy = y;
		
		add(textField, constraints);
		return textField;
	}

	private JButton addButton(String text, int x, int y) {
		JButton button = new JButton(text);
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(20, 20, 20, 15);
		constraints.fill = GridBagConstraints.FIRST_LINE_START;
		constraints.gridx = x;
		constraints.gridy = y;
		
		add(button, constraints);
		return button;
	}
	
	private JLabel addLabelErrorCampo() {
		JLabel label = new JLabel();
		label.setVisible(false);
		label.setFont(new Font("Arial", Font.BOLD, 20));
		label.setForeground((Color.RED));
		label.setBackground(Color.BLUE);
		label.setBorder(new MatteBorder(2,2,2,2,Color.RED));
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 20, 20, 15);
		constraints.fill = GridBagConstraints.CENTER;
		constraints.gridx = 1;
		constraints.gridy = 3;
		
		add(label, constraints);
		return label;
	}

//	private void addListenerTeclado() {
//		addKeyListener(new KeyAdapter() {
//			
//			@Override
//			public void keyPressed(KeyEvent e) {
//				System.out.println("HOLAA");
//				switch (e.getKeyCode()) {
//				case KeyEvent.VK_ENTER:
//					aceptar();
//					break;
//				case KeyEvent.VK_ESCAPE:
//					cancelar();
//					break;
//				default:
//					break;
//				}
//			}
//		});
//	}
	
//	@Override
//	public void keyTyped(KeyEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void keyPressed(KeyEvent e) {
//		System.out.println("HOLAA");
//		switch (e.getKeyCode()) {
//		case KeyEvent.VK_ENTER:
//			aceptar();
//			break;
//		case KeyEvent.VK_ESCAPE:
//			cancelar();
//			break;
//		default:
//			break;
//		}
//		
//	}
//
//	@Override
//	public void keyReleased(KeyEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
}

