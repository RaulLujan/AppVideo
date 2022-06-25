package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.lang.reflect.Constructor;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

import cargadorVideos.BuscadorVideos;
import cargadorVideos.IBuscadorVideos;
import controlador.Controlador;

@SuppressWarnings("serial")
public class Ventana extends JFrame {
	
	private JPanel laminaCentral;

	private JPanel laminaInicio;
	private JLabel labelNombreUsuario;
	private JButton bLogin;
	private JButton bRegistro;
	private JButton bLogout;
	private JButton bPremium;

	private JPanel laminaFuncionalidad;
	private ButtonGroup grupoFuncionalidad;

	public Ventana() {
		super("AppVideo");
		
		// CONF VENTANA

		// Se estrablecen las dimensiones
		Toolkit pantalla = Toolkit.getDefaultToolkit();
		Dimension dimPantalla = pantalla.getScreenSize();
		int anchoPantalla = dimPantalla.width;
		int altoPantalla  = dimPantalla.height;
		int ancho = anchoPantalla/2 + anchoPantalla/6;
		int alto  =  altoPantalla/2 +  altoPantalla/6;
		setSize(ancho, alto);
		setLocation(anchoPantalla/6, altoPantalla/6);
		//setSize(anchoPantalla/2, alturaPantalla/2);
		//setLocation(anchoPantalla/4, alturaPantalla/4);
		
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Image icono = pantalla.getImage("resources/icono.png");
		setIconImage(icono);
		
		// 1 addLaminaPrincipal a Ventana
		JPanel laminaPrincipal = new JPanel();
		laminaPrincipal.setLayout(new BorderLayout());
		laminaPrincipal.setBorder(new EmptyBorder(10, 5, 10, 5));
		add(laminaPrincipal);
		
		// 1.1 addLaminaCentral a LaminaPrincipal
		laminaCentral = new JPanel();
		laminaCentral.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		laminaCentral.setBackground(new Color(96, 96, 96));
		laminaPrincipal.add(laminaCentral, BorderLayout.CENTER);
		
		// 1.2 addLaminaSuperior a LaminaPrincipal
		JPanel laminaSuperior = new JPanel();
		laminaSuperior.setLayout(new BoxLayout(laminaSuperior, BoxLayout.Y_AXIS));
		laminaPrincipal.add(laminaSuperior, BorderLayout.NORTH);
		
		// 1.2.1 addLaminaInicio a LaminaSuperior
		laminaInicio = new JPanel();
		laminaInicio.setLayout(new BoxLayout(laminaInicio, BoxLayout.X_AXIS));
		laminaInicio.setBorder(BorderFactory.createLoweredBevelBorder());
		laminaSuperior.add(laminaInicio);
		
		// 1.2.2 addLaminaFuncionalidad a LaminaSuperior
		laminaFuncionalidad = new JPanel();
		laminaFuncionalidad.setLayout(new BoxLayout(laminaFuncionalidad, BoxLayout.X_AXIS));
		laminaFuncionalidad.setBorder(new EmptyBorder(10, 0, 10, 0));
		laminaSuperior.add(laminaFuncionalidad);
		
		grupoFuncionalidad = new ButtonGroup();
		
		// 1.2.2.1 addButtonExplorar a LaminaFuncionalidad
		addButtonFuncionalidad("Explorar");
		// 1.2.2.2 addButtonNuevaLista a LaminaFuncionalidad
		addButtonFuncionalidad("Nueva Lista");
		// 1.2.2.3 addButtonMisListas a LaminaFuncionalidad
		addButtonFuncionalidad("Mis Listas");
		// 1.2.2.4 addButtonRecientes a LaminaFuncionalidad
		addButtonFuncionalidad("Recientes");
		if (Controlador.getInstancia().isUsuarioPremium()) {
			// 1.2.2.5 addButtonMasVistos a LaminaFuncionalidad
			addButtonFuncionalidad("Mas Vistos");
			// 1.2.2.6 addButtonGenerarPDF a LaminaFuncionalidad
			addButtonFuncionalidad("Generar PDF");
			// TODO filtros
		}
		
		laminaFuncionalidad.add(Box.createHorizontalGlue());
		
		// 1.2.1.1 addLabelAppVideo a LaminaInicio
		JLabel labelAppVideo = new JLabel("AppVideo");
		labelAppVideo.setFont(new Font("Arial", Font.BOLD, 30));
		labelAppVideo.setForeground(Color.RED);
		laminaInicio.add(labelAppVideo);
		
		laminaInicio.add(Box.createHorizontalGlue());
		
		// 1.2.1.1 addLabelNombreUsuario a LaminaInicio
		labelNombreUsuario = new JLabel();
		labelNombreUsuario.setFont(new Font("Arial", Font.BOLD, 14));
		laminaInicio.add(labelNombreUsuario);
		
		laminaInicio.add(Box.createHorizontalGlue());
		
		// 1.2.1.1 addButtonLogin a LaminaInicio
		bLogin = addButtonInicio("Login");
		// 1.2.1.1 addButtonRegistro a LaminaInicio
		bRegistro = addButtonInicio("Registro");
		
		laminaInicio.add(Box.createHorizontalGlue());
		
		// 1.2.1.1 addButtonLogout a LaminaInicio
		bLogout = addButtonInicio("Logout");
		
		laminaInicio.add(Box.createHorizontalGlue());
		
		// 1.2.1.1 addButtonPremium a LaminaInicio
		bPremium = addButtonInicio("Premium");
		bPremium.setForeground(Color.RED);
		
		laminaInicio.add(Box.createHorizontalGlue());
		
		// 1.2.1.1 addButtonCargador a LaminaInicio
		addButtonInicio("Cargador Videos");
		
		// MOSTRAR
		mostrarLaminaSuperior();
	}

	private JToggleButton addButtonFuncionalidad(String texto) {
		JToggleButton boton = new JToggleButton(texto);
		boton.setForeground(Color.WHITE);
		boton.setBackground(new Color(160, 160, 160));
		boton.addActionListener(e -> {
			setLaminaCentral(boton.getText());
		});
		laminaFuncionalidad.add(boton);
		grupoFuncionalidad.add(boton);
		return boton;
	}

	private JButton addButtonInicio(String texto) {
		JButton boton = new JButton(texto);
		boton.addActionListener(e -> {
			String text = boton.getText();
			switch (text) {
			case "Login":
			case "Registro":
				setLaminaCentral(text);
				break;

			case "Logout":
				int salida = JOptionPane.showConfirmDialog(null, "�Seguro de que quiere salir?", "Logout", JOptionPane.YES_NO_CANCEL_OPTION);
				switch (salida) {
				case JOptionPane.YES_OPTION:
					// TODO LOGOUT
					System.exit(0);
					break;
				case JOptionPane.NO_OPTION:
				case JOptionPane.CANCEL_OPTION:
					break;
				}
				break;
				
			case "Premium":
				Controlador.getInstancia().setUsuarioPremium();
				break;
				
			case "Cargador videos":
				IBuscadorVideos componente = new BuscadorVideos();
				componente.addVideosListener(Controlador.getInstancia());
				JFileChooser fileChooser = new JFileChooser();
			    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			    int seleccion = fileChooser.showOpenDialog((Component)e.getSource());
			    if (seleccion == JFileChooser.APPROVE_OPTION) {
			        File fichero = fileChooser.getSelectedFile();
			        componente.setArchivoVideos(fichero);
			    } 
				break;
			}
		});
		laminaInicio.add(boton);
		return boton;
	}

	public void setLaminaCentral(String titulo) {
		String t = titulo.replace(" ", "");
		try {
			Class<?> c = Class.forName("vista.Tab"+t);
			System.out.println(c.toString());
			Class<?> cArgs[] = { Ventana.class };
			Constructor<?> constructor = c.getConstructor(cArgs);
			Object oParams[] = { this };
			JPanel nuevaLamina = (JPanel) constructor.newInstance(oParams);
			nuevaLamina.setSize(getPreferredSize());
			laminaCentral.removeAll();
			
			if (titulo.equals("Login") || titulo.equals("Registro"))
				laminaCentral.setLayout(new FlowLayout());
			else
				laminaCentral.setLayout(new GridLayout(1, 1));
			
			laminaCentral.add(nuevaLamina);
			
			laminaCentral.revalidate();
			laminaCentral.repaint();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	public void mostrarLaminaSuperior() {
		String nombreUsuario = Controlador.getInstancia().getNombreUsuario();
		labelNombreUsuario.setText("Hola " + nombreUsuario);
		
		boolean usuarioLogin = Controlador.getInstancia().isUsuarioLogin();
		bLogin.setVisible(!usuarioLogin);
		bRegistro.setVisible(!usuarioLogin);
		bLogout.setVisible(usuarioLogin);
		bPremium.setVisible(usuarioLogin);
		laminaFuncionalidad.setVisible(usuarioLogin);
	}

}
