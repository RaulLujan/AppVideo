package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


import controlador.Controlador;

public class Login extends JPanel {
	private JTextField tfNombre;
	private JPasswordField pfPass;

	public Login() {
		confLamina();
	}

	private void confLamina() {
		setBackground(new Color(96, 96, 96));
		setLayout(new BorderLayout());
		addTitulo();
		addFormulario();
		addBotonesForm();
	}

	private void addTitulo() {
		JPanel pTitulo = new JPanel();

		JLabel titulo = new JLabel("Inicia sesión");

		pTitulo.setBackground(Color.GRAY);
		titulo.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 18));

		pTitulo.add(titulo);
		add(pTitulo, BorderLayout.NORTH);
	}

	private void addFormulario() {
		JPanel pForm = new JPanel();
		pForm.setLayout(new GridLayout(2, 2));

		JLabel lNombre = new JLabel("Nombre: ");
		tfNombre = new JTextField();

		JLabel lPass = new JLabel("Password: ");
		pfPass = new JPasswordField();

		pForm.add(lNombre);
		pForm.add(tfNombre);
		pForm.add(lPass);
		pForm.add(pfPass);

		add(pForm, BorderLayout.CENTER);

	}

	private void addBotonesForm() {
		JPanel pBotones = new JPanel();
		pBotones.setLayout(new BoxLayout(pBotones, BoxLayout.X_AXIS));

		JButton bAceptar = new JButton("Aceptar");
		JButton bCancelar = new JButton("Cancelar");

		pBotones.add(Box.createHorizontalGlue());
		pBotones.add(bAceptar);
		pBotones.add(Box.createHorizontalGlue());
		pBotones.add(bCancelar);
		pBotones.add(Box.createHorizontalGlue());

		add(pBotones, BorderLayout.SOUTH);

		addListenerBotonLogin(bAceptar);
		addListenerBotonCancelar(bCancelar);

	}

	private void addListenerBotonCancelar(JButton bCancelar) {
		bCancelar.addActionListener(e -> {
			tfNombre.setText("");
			pfPass.setText("");
		});
	}

	private void addListenerBotonLogin(JButton bAceptar) {
		bAceptar.addActionListener(e -> {
			boolean isLogin = Controlador.getInstaciaUnica().getLogin(tfNombre.getText(), new String(pfPass.getPassword()));
			
			if (isLogin) {
				LaminaCentral.getInstancia().setLamina("Explorar");
			} else {
				JOptionPane.showMessageDialog(this, "Nombre de usuario o contraseña no valido", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
	}
}
