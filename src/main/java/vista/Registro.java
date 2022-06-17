package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
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
		setBackground(new Color(96, 96, 96));
		setLayout(new BorderLayout());

		addTitulo();
		addFormulario();
		addBotonesForm();
	}

	private void addTitulo() {
		JPanel pTitulo = new JPanel();

		JLabel titulo = new JLabel("Registro");

		pTitulo.setBackground(Color.GRAY);
		titulo.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 18));

		pTitulo.add(titulo);
		add(pTitulo, BorderLayout.NORTH);
	}

	private void addFormulario() {
		JPanel pForm = new JPanel();
		JPanel pFormParte1 = new JPanel();
		JPanel pFormParte2 = new JPanel();

		pForm.setLayout(new BoxLayout(pForm, BoxLayout.Y_AXIS));
		pForm.setBorder(new EmptyBorder(10, 5, 10, 5));

		pFormParte1.setLayout(new GridLayout(4, 2));
		pFormParte2.setLayout(new GridLayout(3, 2));

		lNombre = new JLabel("* Nombre: ");
		tfNombre = new JTextField();

		lApellidos = new JLabel("Apellidos: ");
		tfApellidos = new JTextField();

		lFechaNacimiento = new JLabel("* Fecha de Nacimiento: ");
		cFechaNacimiento = new JDateChooser();
		cFechaNacimiento.setLocale(Locale.ENGLISH);

		lEmail = new JLabel("Email: ");
		tfEmail = new JTextField();

		lUsuario = new JLabel("* Usuari@: ");
		tfUsuario = new JTextField();

		lPass = new JLabel("* Contraseña: ");
		pfPass = new JPasswordField();

		lPassRepetir = new JLabel("* Repetir contraseña: ");
		pfPassRepetir = new JPasswordField();

		pFormParte1.add(lNombre);
		pFormParte1.add(tfNombre);
		pFormParte1.add(lApellidos);
		pFormParte1.add(tfApellidos);
		pFormParte1.add(lFechaNacimiento);
		pFormParte1.add(cFechaNacimiento);
		pFormParte1.add(lEmail);
		pFormParte1.add(tfEmail);

		pFormParte2.add(lUsuario);
		pFormParte2.add(tfUsuario);
		pFormParte2.add(lPass);
		pFormParte2.add(pfPass);
		pFormParte2.add(lPassRepetir);
		pFormParte2.add(pfPassRepetir);

		pForm.add(pFormParte1);
		pForm.add(Box.createHorizontalGlue());
		pForm.add(pFormParte2);

		add(pForm, BorderLayout.CENTER);

	}

	private void addBotonesForm() {
		JPanel pGeneral = new JPanel();
		JPanel pBotones = new JPanel();
		JPanel pCampos = new JPanel();

		pGeneral.setLayout(new BoxLayout(pGeneral, BoxLayout.Y_AXIS));
		pGeneral.setBorder(new EmptyBorder(10, 5, 10, 5));

		pBotones.setLayout(new BoxLayout(pBotones, BoxLayout.X_AXIS));

		pCampos.setLayout(new BoxLayout(pCampos, BoxLayout.Y_AXIS));

		JButton bRegistrar = new JButton("Registrar");
		JButton bCancelar = new JButton("Cancelar");

		JLabel camposO = new JLabel("* Los campos son obligatorios");
		camposO.setForeground(Color.DARK_GRAY);
		camposO.setAlignmentX(Component.CENTER_ALIGNMENT);

		errorCampo = new JLabel();
		errorCampo.setForeground(Color.RED);
		errorCampo.setAlignmentX(Component.CENTER_ALIGNMENT);
		errorCampo.setVisible(false);

		pBotones.add(Box.createHorizontalGlue());
		pBotones.add(bRegistrar);
		pBotones.add(Box.createHorizontalGlue());
		pBotones.add(bCancelar);
		pBotones.add(Box.createHorizontalGlue());

		pCampos.add(camposO);
		pCampos.add(errorCampo);

		pGeneral.add(pBotones);
		pGeneral.add(pCampos);

		add(pGeneral, BorderLayout.SOUTH);

		addListenerBotonCancelar(bCancelar);
		addListenerBotonRegistro(bRegistrar);
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
		} else if (!tfUsuario.getText().isEmpty()
				&& Controlador.getInstaciaUnica().isUsuarioRegistrado(tfUsuario.getText())) {
			completed = false;
			errorCampo.setText("Este nombre de usuario ya se está usando");
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
				boolean usuarioRegistrado = Controlador.getInstaciaUnica().registrarUsuario(tfNombre.getText(),
						tfApellidos.getName(), cFechaNacimiento.getDate(), tfEmail.getText(), tfUsuario.getText(),
						new String(pfPass.getPassword()));
				
				if(usuarioRegistrado) {
					LaminaCentral.getInstancia().setLamina("Recientes");
				} else {
					JOptionPane.showMessageDialog(this, "Ha habido un error al registrar el usuario, parece que ya está registrado", "Error de registro", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
	}

}
