package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

import controlador.Controlador;

public class Registro extends JPanel {
	private JLabel lNombre;
	private JTextField tfNombre;
	private JLabel lApellidos;
	private JTextField tfApellidos;
	private JLabel lFechaNacimiento;
	private JDateChooser cFechaNacimiento;
	private JLabel lEmail;
	private JTextField tfEmail;
	private JLabel lUsuario;
	private JTextField tfUsuario;
	private JLabel lPass;
	private JPasswordField pfPass;
	private JLabel lPassRepetir;
	private JPasswordField pfPassRepetir;

	private JLabel errorCampo;

	public Registro() {
		confLamina();
	}

	private void confLamina() {

		setLayout(new GridBagLayout());
		setBackground(new Color(96, 96, 96));
		TitledBorder titulo = new TitledBorder("Registro");
		titulo.setTitleColor(Color.WHITE);
		titulo.setTitleFont(new Font("Arial", Font.BOLD, 25));
		setBorder(titulo);
		addFormulario();
		addBotonesForm();
	}

	private void addFormulario() {
		lNombre = new JLabel("* Nombre: ");
		tfNombre = new JTextField();

		lApellidos = new JLabel("Apellidos: ");
		tfApellidos = new JTextField();

		lFechaNacimiento = new JLabel("* Fecha de Nacimiento: ");
		cFechaNacimiento = new JDateChooser();
		cFechaNacimiento.setLocale(Locale.ENGLISH);
		cFechaNacimiento.setPreferredSize(new Dimension(200, 20));

		lEmail = new JLabel("Email: ");
		tfEmail = new JTextField();

		lUsuario = new JLabel("* Usuari@: ");
		tfUsuario = new JTextField();

		lPass = new JLabel("* Contraseña: ");
		pfPass = new JPasswordField();

		lPassRepetir = new JLabel("* Repetir contraseña: ");
		pfPassRepetir = new JPasswordField();
		
		GridBagConstraints constraintsFecha = new GridBagConstraints();
		constraintsFecha.insets = new Insets(8, 10, 20, 15);
		constraintsFecha.fill = GridBagConstraints.EAST;
		constraintsFecha.gridx = 1;
		constraintsFecha.gridy = 2;

		addLabel(lNombre, 0, 0);
		addTextField(tfNombre, 1, 0);
		addLabel(lApellidos, 0, 1);
		addTextField(tfApellidos, 1, 1);
		addLabel(lFechaNacimiento, 0, 2);
		add(cFechaNacimiento, constraintsFecha);
		addLabel(lEmail, 0, 3);
		addTextField(tfEmail, 1, 3);
		addLabel(lUsuario, 0, 4);
		addTextField(tfUsuario, 1, 4);
		addLabel(lPass, 0, 5);
		addTextField(pfPass, 1, 5);
		addLabel(lPassRepetir, 0, 6);
		addTextField(pfPassRepetir, 1, 6);

	}

	private void addBotonesForm() {

		JButton bRegistrar = new JButton("Registrar");
		JButton bCancelar = new JButton("Cancelar");

		GridBagConstraints constraintsBRegistrar = new GridBagConstraints();
		constraintsBRegistrar.insets = new Insets(20, 20, 20, 15);
		constraintsBRegistrar.fill = GridBagConstraints.FIRST_LINE_START;
		constraintsBRegistrar.gridx = 0;
		constraintsBRegistrar.gridy = 7;
		
		GridBagConstraints constraintsBCancelar = new GridBagConstraints();
		constraintsBCancelar.insets = new Insets(20, 20, 20, 15);
		constraintsBCancelar.anchor = GridBagConstraints.FIRST_LINE_END;
		constraintsBCancelar.gridx = 1;
		constraintsBCancelar.gridy = 7;

		add(bRegistrar, constraintsBRegistrar);
		add(bCancelar, constraintsBCancelar);
	

		JLabel camposO = new JLabel("* Los campos son obligatorios");
		camposO.setFont(new Font("Arial", Font.BOLD, 15));
		camposO.setForeground((Color.WHITE));

		errorCampo = new JLabel();
		errorCampo.setVisible(false);
		errorCampo.setFont(new Font("Arial", Font.BOLD, 20));
		errorCampo.setForeground((Color.RED));
		errorCampo.setBackground(Color.BLUE);
		errorCampo.setBorder(new MatteBorder(2,2,2,2,Color.RED));

		
		GridBagConstraints constraintsLCampo = new GridBagConstraints();
		constraintsLCampo.insets = new Insets(20, 5, 5, 15);
		constraintsLCampo.fill = GridBagConstraints.CENTER;
		constraintsLCampo.gridx = 1;
		constraintsLCampo.gridy = 8;
		
		GridBagConstraints constraintsLCampoError = new GridBagConstraints();
		constraintsLCampoError.insets = new Insets(5, 20, 20, 15);
		constraintsLCampoError.fill = GridBagConstraints.CENTER;
		constraintsLCampoError.gridx = 1;
		constraintsLCampoError.gridy = 9;
		
		
		add(camposO, constraintsLCampo);
		add(errorCampo, constraintsLCampoError);
	
		addListenerBotonCancelar(bCancelar);
		addListenerBotonRegistro(bRegistrar);
	}

	private void addLabel(JLabel label, int x, int y) {
		label.setFont(new Font("Arial", Font.BOLD, 15));
		label.setForeground((Color.WHITE));
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(8, 10, 20, 5);
		constraints.anchor = GridBagConstraints.EAST;
		constraints.gridx = x;
		constraints.gridy = y;
		
		add(label, constraints);
	}

	private void addTextField(JTextField textField, int x, int y) {
		textField.setPreferredSize(new Dimension(200, 20));
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(8, 10, 20, 15);
		constraints.fill = GridBagConstraints.EAST;
		constraints.gridx = x;
		constraints.gridy = y;
		
		add(textField, constraints);
	}

	private boolean isRegistroCamposCompleted() {

		boolean completed = true;
		String pass = new String(pfPass.getPassword());
		String passRepetir = new String(pfPassRepetir.getPassword());

		errorCampo.setVisible(false);

		if (tfNombre.getText().trim().isEmpty()) {
			completed = false;
			errorCampo.setText("El nombre es obligatorio");
		} else if (cFechaNacimiento.getDate() == null) {
			completed = false;
			errorCampo.setText("La fecha es obligatoria");
		} else if (tfUsuario.getText().trim().isEmpty()) {
			completed = false;
			errorCampo.setText("El nombre de usuario es obligatorio");
		} else if (pass.trim().isEmpty()) {
			completed = false;
			errorCampo.setText("La contraseña es obligatoria");
		} else if (passRepetir.trim().isEmpty()) {
			completed = false;
			errorCampo.setText("La contraseña es obligatoria");
		} else if (!pass.equals(passRepetir)) {
			completed = false;
			errorCampo.setText("Las contraseñas deben coincidir");
		}

		if (!completed) {
			errorCampo.setVisible(true);
		}

		revalidate();
		repaint();

		return completed;
	}

	private void addListenerBotonCancelar(JButton bCancelar) {
		bCancelar.addActionListener(e -> {
			tfNombre.setText("");
			tfApellidos.setText("");
			cFechaNacimiento.setDate(null);
			tfEmail.setText("");
			tfUsuario.setText("");
			pfPass.setText("");
			pfPassRepetir.setText("");
		});
	}

	private void addListenerBotonRegistro(JButton bRegistrar) {
		bRegistrar.addActionListener(e -> {
			boolean isOKRegistroCampos = isRegistroCamposCompleted();

			if (isOKRegistroCampos) {
				boolean usuarioRegistrado = Controlador.getInstancia().registrarUsuario(tfNombre.getText(),
						tfApellidos.getName(), cFechaNacimiento.getDate(), tfEmail.getText(), tfUsuario.getText(),
						new String(pfPass.getPassword()));

				if (usuarioRegistrado) {
					LaminaSuperior.getInstancia().getbRecientes().setSelected(true);
					removeAll();
					LaminaSuperior.getInstancia().mostrar();
					LaminaSuperior.getInstancia().mostrarLamina("Recientes");

				} else {
					JOptionPane.showMessageDialog(this,
							"Ha habido un error al registrar el usuario, parece que ya está registrado",
							"Error de registro", JOptionPane.ERROR_MESSAGE);
				}
				revalidate();
				repaint();

			}
		});
	}

}
