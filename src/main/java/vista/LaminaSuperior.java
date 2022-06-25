package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

import cargadorVideos.BuscadorVideos;
import cargadorVideos.IBuscadorVideos;
import controlador.Controlador;

public class LaminaSuperior extends JPanel {

	private LaminaCentral laminaCentral;

	private JPanel laminaInicio;
	private JLabel labelNombreUsuario;
	private JButton bLogin;
	private JButton bRegistro;
	private JButton bLogout;
	private JButton bPremium;

	private JPanel laminaFuncionalidad;
	private ButtonGroup grupoFuncionalidad;
	private JToggleButton bRecientes;

	public LaminaSuperior(LaminaCentral laminaCentral) {
		this.laminaCentral = laminaCentral;
		
		// CONF LAMINA
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// addLaminas inicio y funcionalidad
		laminaInicio = new JPanel();
		laminaFuncionalidad = new JPanel();
		add(laminaInicio);
		add(laminaFuncionalidad);
		
		
		
		// CONF LAMINA INICIO
		laminaInicio.setLayout(new BoxLayout(laminaInicio, BoxLayout.X_AXIS));
		laminaInicio.setBorder(BorderFactory.createLoweredBevelBorder());
		
		// addLabelAppVideo
		JLabel labelAppVideo = new JLabel("AppVideo");
		labelAppVideo.setFont(new Font("Arial", Font.BOLD, 30));
		labelAppVideo.setForeground(Color.RED);
		laminaInicio.add(labelAppVideo);
		
		laminaInicio.add(Box.createHorizontalGlue());
		
		// addLabelNombreUsuario
		labelNombreUsuario = new JLabel();
		labelNombreUsuario.setFont(new Font("Arial", Font.BOLD, 14));
		laminaInicio.add(labelNombreUsuario);
		
		laminaInicio.add(Box.createHorizontalGlue());
		
		// addButtonLogin
		bLogin = addButtonInicio("Login");
		
		// addButtonRegistro
		bRegistro = addButtonInicio("Registro");
		
		laminaInicio.add(Box.createHorizontalGlue());
		
		// addButtonLogout
		bLogout = new JButton("Logout");
		laminaInicio.add(bLogout);
		bLogout.addActionListener(e -> {
			int salida = JOptionPane.showConfirmDialog(null, "�Seguro de que quiere salir?", "Logout", JOptionPane.YES_NO_CANCEL_OPTION);
			switch (salida) {
			case JOptionPane.YES_OPTION:
				System.exit(0);
				break;
			case JOptionPane.NO_OPTION:
			case JOptionPane.CANCEL_OPTION:
				break;
			}
		});
		
		laminaInicio.add(Box.createHorizontalGlue());
		
		// addButtonPremium
		bPremium = new JButton("Premium");
		laminaInicio.add(bPremium);
		bPremium.setForeground(Color.RED);
		bPremium.addActionListener(e -> {
			Controlador.getInstancia().setUsuarioPremium();
		});
		
		laminaInicio.add(Box.createHorizontalGlue());
		
		// addButtonCargador
		JButton bCargador = new JButton("Cargador Videos");
		laminaInicio.add(bCargador);
		bCargador.addActionListener(e -> {
			IBuscadorVideos componente = new BuscadorVideos();
			componente.addVideosListener(Controlador.getInstancia());
			JFileChooser fileChooser = new JFileChooser();
		    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		    int seleccion = fileChooser.showOpenDialog((Component)e.getSource());
		    if (seleccion == JFileChooser.APPROVE_OPTION) {
		        File fichero = fileChooser.getSelectedFile();
		        componente.setArchivoVideos(fichero);
		    } 
		});
		
		
		
		// CONF LAMINA FUNCIONALIDAD
		laminaFuncionalidad.setLayout(new BoxLayout(laminaFuncionalidad, BoxLayout.X_AXIS));
		laminaFuncionalidad.setBorder(new EmptyBorder(10, 0, 10, 0));
		
		grupoFuncionalidad = new ButtonGroup();
		
		// addButtonExplorar
		addButtonFuncionalidad("Explorar");
		
		// addButtonNuevaLista
		addButtonFuncionalidad("Nueva Lista");
		
		// addButtonMisListas
		addButtonFuncionalidad("Mis Listas");
		
		// addButtonRecientes
		bRecientes = addButtonFuncionalidad("Recientes");
		
		if (Controlador.getInstancia().isUsuarioPremium()) {
			
			// addButtonMasVistos
			addButtonFuncionalidad("Mas Vistos");
			
			// addButtonGenerarPDF
			addButtonFuncionalidad("Generar PDF");
		}
		
		laminaFuncionalidad.add(Box.createHorizontalGlue());
		
		
		
		
		// MOSTRAR
		mostrar();
	}

	private JButton addButtonInicio(String texto) {
		JButton boton = new JButton(texto);
		laminaInicio.add(boton);
		boton.addActionListener(e -> {
			mostrarLamina(boton.getText());
		});
		return boton;
	}

	private JToggleButton addButtonFuncionalidad(String texto) {
		JToggleButton boton = new JToggleButton(texto);
		boton.setForeground(Color.WHITE);
		boton.setBackground(new Color(160, 160, 160));
		laminaFuncionalidad.add(boton);
		grupoFuncionalidad.add(boton);
		boton.addActionListener(e -> {
			mostrarLamina(boton.getText());
		});
		return boton;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public void mostrar() {
		String nombreUsuario = Controlador.getInstancia().getNombreUsuario();
		labelNombreUsuario.setText("Hola " + nombreUsuario);
		
		boolean usuarioLogin = Controlador.getInstancia().isUsuarioLogin();
		bLogin.setVisible(!usuarioLogin);
		bRegistro.setVisible(!usuarioLogin);
		bLogout.setVisible(usuarioLogin);
		bPremium.setVisible(usuarioLogin);
		laminaFuncionalidad.setVisible(usuarioLogin);
	}

	public void mostrarLamina(String tipo) {
		laminaCentral.setLamina(tipo);
	}

	public JToggleButton getbRecientes() {
		return bRecientes;
	}

}
