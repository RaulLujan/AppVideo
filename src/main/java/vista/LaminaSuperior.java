package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;

public class LaminaSuperior extends JPanel {

	private static LaminaSuperior instancia = null;
	public static LaminaSuperior getInstancia(LaminaCentral laminaCentral) {
		if (instancia == null)
			return instancia = new LaminaSuperior(laminaCentral);
		return instancia;
	}
	public static LaminaSuperior getInstancia() {
		return instancia;
	}

	private JPanel laminaInicio;
	private JPanel laminaFuncionalidad;
	private LaminaCentral laminaCentral;
	private ButtonGroup grupoFuncionalidad;
	
	private JToggleButton bRecientes;
	
	private JLabel nombreUsuarioLabel;
	private JButton premium;
	private JButton login;
	private JButton registro;
	private JButton logout;

	private LaminaSuperior(LaminaCentral laminaCentral) {
		laminaInicio = new JPanel();
		laminaFuncionalidad = new JPanel();
		this.laminaCentral = laminaCentral;
		grupoFuncionalidad = new ButtonGroup();
		confLamina();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	private void confLamina() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		confLaminaInicio();
		confLaminaFuncionalidad();

		add(laminaInicio);
		add(laminaFuncionalidad);

		mostrar();
	}

	private void confLaminaInicio() {
		laminaInicio.setLayout(new BoxLayout(laminaInicio, BoxLayout.X_AXIS));
		laminaInicio.setBorder(BorderFactory.createLoweredBevelBorder());

		JLabel appVideoLabel = new JLabel("AppVideo");
		appVideoLabel.setFont(new Font("Arial", Font.BOLD, 30));
		appVideoLabel.setForeground(Color.RED);

		nombreUsuarioLabel = new JLabel();
		nombreUsuarioLabel.setFont(new Font("Arial", Font.BOLD, 14));

		laminaInicio.add(appVideoLabel);
		laminaInicio.add(Box.createHorizontalGlue());
		laminaInicio.add(nombreUsuarioLabel);
		laminaInicio.add(Box.createHorizontalGlue());
		addLoginButton(laminaInicio);
		addRegistroButton(laminaInicio);
		laminaInicio.add(Box.createHorizontalGlue());
		addLogoutButton(laminaInicio);
		laminaInicio.add(Box.createHorizontalGlue());
		addPremiumButton(laminaInicio);
	}

	private void confLaminaFuncionalidad() {
		laminaFuncionalidad.setLayout(new BoxLayout(laminaFuncionalidad, BoxLayout.X_AXIS));
		laminaFuncionalidad.setBorder(new EmptyBorder(10, 0, 10, 0));
		addExplorarButton(laminaFuncionalidad);
		addNuevaListaButton(laminaFuncionalidad);
		addMisListasButton(laminaFuncionalidad);
		addRecienteButton(laminaFuncionalidad);
		if (Controlador.getInstancia().isUsuarioPremium()) {
			addMasVistosrButton(laminaFuncionalidad);
			addGenerarPDF(laminaFuncionalidad);
		}
		laminaFuncionalidad.add(Box.createHorizontalGlue());
	}

	private void addLoginButton(JPanel lamina) {
		login = createButtonJPanel("Login", laminaInicio);

		login.addActionListener(e -> {
			laminaCentral.setLamina(login.getText());
		});

	}

	private void addRegistroButton(JPanel lamina) {
		registro = createButtonJPanel("Registro", laminaInicio);

		registro.addActionListener(e -> {
			laminaCentral.setLamina(registro.getText());
		});

	}

	private void addLogoutButton(JPanel lamina) {
		logout = createButtonJPanel("Logout", laminaInicio);

		logout.addActionListener(e -> {
			int salida = JOptionPane.showConfirmDialog(null, "¿Seguro de que quiere salir?", "Logout",
					JOptionPane.YES_NO_CANCEL_OPTION);

			switch (salida) {
			case JOptionPane.YES_OPTION:
				System.exit(0);
				break;

			case JOptionPane.NO_OPTION:
			case JOptionPane.CANCEL_OPTION:

				break;

			}
		});

	}

	private void addPremiumButton(JPanel lamina) {
		premium = createButtonJPanel("Premium", laminaInicio);
		premium.setForeground(Color.RED);

		premium.addActionListener(e -> {
			Controlador.getInstancia().activarUsuarioPremium();
		});
		
		// TODO se deben mostrar las funciones/botones que son premium --> Generar PDF y
		// top_ten
		
	}

	private void addExplorarButton(JPanel lamina) {
		JToggleButton explorar = createToggleButtonJPanel("Explorar", lamina);

		explorar.addActionListener(e -> {
			laminaCentral.setLamina(explorar.getText());
		});
	}

	private void addNuevaListaButton(JPanel lamina) {
		JToggleButton nuevaLista = createToggleButtonJPanel("Nueva Lista", lamina);

		nuevaLista.addActionListener(e -> {
			laminaCentral.setLamina(nuevaLista.getText());
		});
	}

	private void addMisListasButton(JPanel lamina) {
		JToggleButton misListas = createToggleButtonJPanel("Mis Listas", lamina);

		misListas.addActionListener(e -> {
			laminaCentral.setLamina(misListas.getText());
		});
	}

	private void addRecienteButton(JPanel lamina) {
		bRecientes = createToggleButtonJPanel("Recientes", lamina);

		bRecientes.addActionListener(e -> {
			laminaCentral.setLamina(bRecientes.getText());
		});
	}

	private void addMasVistosrButton(JPanel lamina) {
		JToggleButton masVistos = createToggleButtonJPanel("Más Vistos", lamina);

		masVistos.addActionListener(e -> {
			laminaCentral.setLamina(masVistos.getText().replace(" ", "").replace("á", "a"));
		});
	}
	
	private void addGenerarPDF(JPanel lamina) {
		JToggleButton generarPDF = createToggleButtonJPanel("Generar PDF", lamina);

		generarPDF.addActionListener(e -> {
			laminaCentral.setLamina(generarPDF.getText().replace(" ", ""));
		});
	}	

	private JButton createButtonJPanel(String texto, JPanel lamina) {
		JButton boton = new JButton(texto);
		lamina.add(boton);
		return boton;
	}

	private JToggleButton createToggleButtonJPanel(String texto, JPanel lamina) {
		JToggleButton boton = new JToggleButton(texto);
		boton.setForeground(Color.WHITE);
		boton.setBackground(new Color(160, 160, 160));
		lamina.add(boton);
		grupoFuncionalidad.add(boton);
		return boton;
	}

	public void mostrar() {
		boolean usuarioLogin = Controlador.getInstancia().isUsuarioLogin();
		laminaFuncionalidad.setVisible(usuarioLogin);
		premium.setVisible(usuarioLogin);
		logout.setVisible(usuarioLogin);
		
		login.setVisible(!usuarioLogin);
		registro.setVisible(!usuarioLogin);
		
			String nombreUsuario = Controlador.getInstancia().getNombreUsuario();
			nombreUsuarioLabel.setText("Hola " + nombreUsuario);
	}
	
	public void mostrarLamina(String tipo) {
		laminaCentral.setLamina(tipo);
	}

	public JToggleButton getbRecientes() {
		return bRecientes;
	}
	
}
