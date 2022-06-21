package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import controlador.Controlador;

public class Login extends JPanel  {//implements KeyListener{
	private JTextField tfNombre;
	private JPasswordField pfPass;

	public Login() {
		confLamina();
	}

	private void confLamina() {
		setLayout(new GridBagLayout());
		setBackground(new Color(96, 96, 96));
		TitledBorder titulo = new TitledBorder("Login");
		titulo.setTitleColor(Color.WHITE);
		titulo.setTitleFont(new Font("Arial", Font.BOLD, 25));
		setBorder(titulo);
		addFormulario();
		addBotonesForm();
		//addListenerTeclado();
//		addKeyListener(this);
//		requestFocus();
//		setFocusable(true);
	}

	private void addFormulario() {
		JLabel lNombre = new JLabel("Nombre:");
		lNombre.setFont(new Font("Arial", Font.BOLD, 15));
		lNombre.setForeground((Color.WHITE));
		tfNombre = new JTextField();
		tfNombre.setPreferredSize(new Dimension(200, 20));

		JLabel lPass = new JLabel("Password:");
		lPass.setFont(new Font("Arial", Font.BOLD, 15));
		lPass.setForeground((Color.WHITE));
		pfPass = new JPasswordField();
		pfPass.setPreferredSize(new Dimension(200, 20));
		
		GridBagConstraints constraintsLNombre = new GridBagConstraints();
		constraintsLNombre.insets = new Insets(20, 10, 20, 5);
		constraintsLNombre.anchor = GridBagConstraints.EAST;
		constraintsLNombre.gridx = 0;
		constraintsLNombre.gridy = 0;
		
		GridBagConstraints constraintsTNombre = new GridBagConstraints();
		constraintsTNombre.insets = new Insets(20, 5, 20, 15);
		constraintsTNombre.anchor = GridBagConstraints.EAST;
		constraintsTNombre.gridx = 1;
		constraintsTNombre.gridy = 0;
		
		GridBagConstraints constraintsLPass = new GridBagConstraints();
		constraintsLPass.insets = new Insets(10, 20, 20, 5);
		constraintsLPass.anchor = GridBagConstraints.EAST;
		constraintsLPass.gridx = 0;
		constraintsLPass.gridy = 1;
		
		GridBagConstraints constraintsTPass = new GridBagConstraints();
		constraintsTPass.insets = new Insets(10, 5, 20, 15);
		constraintsTPass.fill = GridBagConstraints.EAST;
		constraintsTPass.gridx = 1;
		constraintsTPass.gridy = 1;

		add(lNombre, constraintsLNombre);
		add(tfNombre, constraintsTNombre);
		add(lPass, constraintsLPass);
		add(pfPass, constraintsTPass);

	}

	private void addBotonesForm() {
		JButton bAceptar = new JButton("Aceptar");
		JButton bCancelar = new JButton("Cancelar");

		GridBagConstraints constraintsBAceptar = new GridBagConstraints();
		constraintsBAceptar.insets = new Insets(20, 20, 20, 15);
		constraintsBAceptar.fill = GridBagConstraints.FIRST_LINE_START;
		constraintsBAceptar.gridx = 0;
		constraintsBAceptar.gridy = 2;
		
		GridBagConstraints constraintsBCancelar = new GridBagConstraints();
		constraintsBCancelar.insets = new Insets(20, 20, 20, 15);
		constraintsBCancelar.anchor = GridBagConstraints.FIRST_LINE_END;
		constraintsBCancelar.gridx = 1;
		constraintsBCancelar.gridy = 2;

		add(bAceptar, constraintsBAceptar);
		add(bCancelar, constraintsBCancelar);
		
		addListenerBotonLogin(bAceptar);
		addListenerBotonCancelar(bCancelar);

	}

	private void addListenerBotonCancelar(JButton bCancelar) {
		bCancelar.addActionListener(e -> {
			cancelar();
		});
	}

	private void addListenerBotonLogin(JButton bAceptar) {
		bAceptar.addActionListener(e -> {
			aceptar();
		});
//		bAceptar.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyPressed(KeyEvent e) {
//				aceptar();
//			}
//		});
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
	
	private void aceptar() {
		boolean isLogin = Controlador.getInstancia().getLogin(tfNombre.getText(), new String(pfPass.getPassword()));
		
		if (isLogin) {
			LaminaSuperior.getInstancia().getbRecientes().setSelected(true);
			removeAll();
			LaminaSuperior.getInstancia().mostrar();
			LaminaSuperior.getInstancia().mostrarLamina("Recientes");
		
			revalidate();
			repaint();
		} else {
			JOptionPane.showMessageDialog(this, "Nombre de usuario o contraseña no valido", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void cancelar() {
		tfNombre.setText("");
		pfPass.setText("");
	}

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

