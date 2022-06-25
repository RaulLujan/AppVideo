package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

import controlador.Controlador;

public class Registro extends JPanel {
	
	public Registro(Ventana parent) {
		
		// CONF LAMINA
		setLayout(new GridBagLayout());
		setBackground(new Color(96, 96, 96));
		
		// addTitulo
		TitledBorder titulo = new TitledBorder("Registro");
		titulo.setTitleColor(Color.WHITE);
		titulo.setTitleFont(new Font("Arial", Font.BOLD, 25));
		setBorder(titulo);
		
		// addEtiquetas
		addLabel("* Nombre: ", 0);
		addLabel("Apellidos: ", 1);
		addLabel("* Fecha de Nacimiento: ", 2);
		addLabel("Email: ", 3);
		addLabel("* Usuari@: ", 4);
		addLabel("* Contraseña: ", 5);
		addLabel("* Repetir contrase�a: ", 6);
		
		// addCampos
		JTextField tfNombre = addTextField(false, 0);
		JTextField tfApellidos = addTextField(false, 1);
		JDateChooser dcFechaNac = addDateChooser(2);
		JTextField tfEmail = addTextField(false, 3);
		JTextField tfUsuario = addTextField(false, 4);
		JPasswordField pfPass = (JPasswordField) addTextField(true, 5);
		JPasswordField pfPassRepetir = (JPasswordField) addTextField(true, 6);
		
		// addLabelObligatorios
		addLabelObligatorios();
		
		// addLabelErrorCampo
		JLabel errorCampo = addLabelErrorCampo();
		
		// addBotones
		// addBotonRegistrar
		JButton bRegistrar = addButton("Registrar", 0, 7);
		bRegistrar.addActionListener(e -> {
			String pass = new String(pfPass.getPassword());
			String passRepetir = new String(pfPassRepetir.getPassword());
			
			boolean isOKRegistroCampos = false;
			if (tfNombre.getText().trim().isEmpty()) {
				errorCampo.setText("El nombre es obligatorio");
			} else if (dcFechaNac.getDate() == null) {
				errorCampo.setText("La fecha es obligatoria");
			} else if (tfUsuario.getText().trim().isEmpty()) {
				errorCampo.setText("El nombre de usuario es obligatorio");
			} else if (pass.trim().isEmpty()) {
				errorCampo.setText("La contraseña es obligatoria");
			} else if (passRepetir.trim().isEmpty()) {
				errorCampo.setText("Repita la contraseña");
			} else if (!pass.equals(passRepetir)) {
				errorCampo.setText("Las contraseñas no coinciden");
			} else {
				isOKRegistroCampos = true;
			}
		
			if (isOKRegistroCampos) {
				errorCampo.setVisible(false);
				boolean isRegistrado = Controlador.getInstancia().registrarUsuario(
						tfNombre.getText(),
						tfApellidos.getName(),
						dcFechaNac.getDate(),
						tfEmail.getText(),
						tfUsuario.getText(),
						pass);
		
				if (isRegistrado) {
					JOptionPane.showMessageDialog(this,
							"El usuario se ha registrado correctamente",
							"Registro completado", JOptionPane.INFORMATION_MESSAGE);
					parent.setLaminaCentral("Login");
					parent.mostrarLaminaSuperior();
				} else {
					JOptionPane.showMessageDialog(this,
							"Ha habido un error al registrar el usuario, parece que ya está registrado",
							"Error de registro", JOptionPane.ERROR_MESSAGE);
				}
		
			} else {
				errorCampo.setVisible(true);
			}
			revalidate();
			repaint();
		});
		// addBotonCancelar
		JButton bCancelar = addButton("Cancelar", 1, 7);
		bCancelar.addActionListener(e -> {
			tfNombre.setText("");
			tfApellidos.setText("");
			dcFechaNac.setDate(null);
			tfEmail.setText("");
			tfUsuario.setText("");
			pfPass.setText("");
			pfPassRepetir.setText("");
		});
	}

	private JLabel addLabel(String texto, int y) {
		JLabel label = new JLabel(texto);
		label.setFont(new Font("Arial", Font.BOLD, 15));
		label.setForeground((Color.WHITE));
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(8, 10, 20, 5);
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
		constraints.insets = new Insets(8, 10, 20, 15);
		constraints.fill = GridBagConstraints.EAST;
		constraints.gridx = 1;
		constraints.gridy = y;
		
		add(textField, constraints);
		return textField;
	}
	
	private JDateChooser addDateChooser(int y) {
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setLocale(Locale.ENGLISH);
		dateChooser.setPreferredSize(new Dimension(200, 20));
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(8, 10, 20, 15);
		constraints.fill = GridBagConstraints.EAST;
		constraints.gridx = 1;
		constraints.gridy = y;

		add(dateChooser, constraints);
		return dateChooser;
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
	
	private JLabel addLabelObligatorios() {
		JLabel label = new JLabel("* Los campos son obligatorios");
		label.setFont(new Font("Arial", Font.BOLD, 15));
		label.setForeground((Color.WHITE));
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(20, 5, 5, 15);
		constraints.fill = GridBagConstraints.CENTER;
		constraints.gridx = 1;
		constraints.gridy = 8;
		
		add(label, constraints);
		return label;
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
		constraints.gridy = 9;
		
		add(label, constraints);
		return label;
	}

}
