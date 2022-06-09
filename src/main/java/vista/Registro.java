package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

public class Registro extends JPanel{
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
	
	private void addTitulo(){
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
		
		JLabel lNombre = new JLabel("* Nombre: ");
		JTextField tfNombre = new JTextField();
		
		JLabel lApellidos = new JLabel("Apellidos: ");
		JTextField tfApellidos = new JTextField();
		
		JLabel lFechaNacimiento = new JLabel("Fecha de Nacimiento: ");
		JDateChooser cFechaNacimiento = new JDateChooser();
		cFechaNacimiento.setLocale(Locale.ENGLISH);
		
		JLabel lEmail = new JLabel("Email: ");
		JTextField tfEmail = new JTextField();
		
		JLabel lUsuario = new JLabel("* Usuari@: ");
		JTextField tfUsuario = new JTextField();
		
		JLabel lPass = new JLabel("* Contraseña: ");
		JPasswordField pfPass = new JPasswordField();
		
		JLabel lPassRepetir = new JLabel("* Repetir contraseña: ");
		JPasswordField pfPassRepetir = new JPasswordField();
		
		pFormParte1.add(lNombre); pFormParte1.add(tfNombre);
		pFormParte1.add(lApellidos); pFormParte1.add(tfApellidos);
		pFormParte1.add(lFechaNacimiento); pFormParte1.add(cFechaNacimiento);
		pFormParte1.add(lEmail); pFormParte1.add(tfEmail);

		pFormParte2.add(lUsuario); pFormParte2.add(tfUsuario);
		pFormParte2.add(lPass); pFormParte2.add(pfPass);
		pFormParte2.add(lPassRepetir); pFormParte2.add(pfPassRepetir);
		
		pForm.add(pFormParte1);
		pForm.add(Box.createHorizontalGlue());
		pForm.add(pFormParte2);
		
		add(pForm, BorderLayout.CENTER);
		
	}
	
	private void addBotonesForm() {
		JPanel pGeneral = new JPanel();
		JPanel pBotones = new JPanel();
		JPanel pCamposO = new JPanel();
		
		pGeneral.setLayout(new BoxLayout(pGeneral, BoxLayout.Y_AXIS));
		pGeneral.setBorder(new EmptyBorder(10, 5, 10, 5));
		
		pBotones.setLayout(new BoxLayout(pBotones, BoxLayout.X_AXIS));
		
		JButton bRegistrar = new JButton("Registrar");
		JButton bCancelar = new JButton("Cancelar");
		
		JLabel camposO = new JLabel("* Los campos son obligatorios");
		camposO.setForeground(Color.DARK_GRAY);
		
		pBotones.add(Box.createHorizontalGlue());
		pBotones.add(bRegistrar);
		pBotones.add(Box.createHorizontalGlue());
		pBotones.add(bCancelar);
		pBotones.add(Box.createHorizontalGlue());
		
		pCamposO.add(camposO);
		
		pGeneral.add(pBotones);
		pGeneral.add(pCamposO);
		
		add(pGeneral, BorderLayout.SOUTH);
	}

}
