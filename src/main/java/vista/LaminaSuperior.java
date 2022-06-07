package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

public class LaminaSuperior extends JPanel {
	private JPanel laminaInicio = new JPanel();
	private JPanel laminaFuncionalidad = new JPanel();
	private LaminaCentral laminaCentral = new LaminaCentral();
	private ButtonGroup grupoFuncionalidad = new ButtonGroup();

	public LaminaSuperior(LaminaCentral laminaCentral) {
		this.laminaCentral = laminaCentral;
		confLamina();
	}

	private void confLamina() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		confLaminaInicio();
		confLaminaFuncionalidad();

		add(laminaInicio);
		add(laminaFuncionalidad);

	}

	private void confLaminaInicio() {
		laminaInicio.setLayout(new BoxLayout(laminaInicio, BoxLayout.X_AXIS));
		laminaInicio.setBorder(BorderFactory.createLoweredBevelBorder());

		JLabel appVideoLabel = new JLabel("AppVideo");
		appVideoLabel.setFont(new Font("Arial", Font.BOLD, 30));
		appVideoLabel.setForeground(Color.RED);

		JLabel nombreUsuarioLabel = new JLabel("Hola usuario sin nombre");
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
		addMasVistosrButton(laminaFuncionalidad);
		laminaFuncionalidad.add(Box.createHorizontalGlue());
	}

	private void addLoginButton(JPanel lamina) {
		JButton login = createButtonJPanel("Login", laminaInicio);
		login.addActionListener(e -> {
			laminaCentral.setLamina(login.getText());
		});
		
		// si ha ido bien el login se muestra la vetana de "Recientes"
		// si falla, se puestra una ventana de error
	}

	private void addRegistroButton(JPanel lamina) {
		JButton registro = createButtonJPanel("Registro", laminaInicio);
		registro.addActionListener(e -> {
			laminaCentral.setLamina(registro.getText());
		});
		
		// mostrar ventana de error si no se ha introducito campos obligatorios (lo son todos, menos apellidos y email)
	}

	private void addLogoutButton(JPanel lamina) {
		JButton logout = createButtonJPanel("Logout", laminaInicio);
		logout.addActionListener(e -> {
			int salida = JOptionPane.showConfirmDialog(null, "¿Seguro de que quiere salir?", "Logout",
					JOptionPane.YES_NO_CANCEL_OPTION);
			switch (salida) {
			case JOptionPane.YES_OPTION:

				break;
			case JOptionPane.NO_OPTION:
			case JOptionPane.CANCEL_OPTION:

				break;

			}
		});
	}

	private void addPremiumButton(JPanel lamina) {
		JButton premium = createButtonJPanel("Premium", laminaInicio);
		premium.setForeground(Color.RED);
		
		// se deben mostrar las funciones/botones que son premium
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

	}

	private void addRecienteButton(JPanel lamina) {
		JToggleButton reciente = createToggleButtonJPanel("Reciente", lamina);

	}

	private void addMasVistosrButton(JPanel lamina) {
		JToggleButton masVistos = createToggleButtonJPanel("Más vistos", lamina);

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

	private void addButtonJPanel(String texto, JPanel lamina) {
		JButton boton = new JButton(texto);
		lamina.add(boton);
	}
}
