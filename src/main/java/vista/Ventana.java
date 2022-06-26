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
import pulsador.Luz;

@SuppressWarnings("serial")
public class Ventana extends JFrame {

	public static final String PREMIUM = "Premium";
	public static final String LOGOUT = "Logout";
	public static final String REGISTRO = "Registro";
	public static final String LOGIN = "Login";
	private static final Toolkit pantalla = Toolkit.getDefaultToolkit();
	private static final Dimension dimPantalla = pantalla.getScreenSize();
	private static final int anchoPantalla = dimPantalla.width;
	private static final int altoPantalla  = dimPantalla.height;
	public static final int ancho = anchoPantalla/2 + anchoPantalla/6;
	public static final int alto  =  altoPantalla/2 +  altoPantalla/6;
	
	private Controlador controlador = Controlador.getInstancia();
	
	private JPanel pCentral;

	private JPanel pInicio;
	private JLabel lSaludo;
	private JButton bLogin;
	private JButton bRegistro;
	private JButton bLogout;
	private JButton bPremium;

	private JPanel pFuncionalidad;
	private ButtonGroup bgFuncionalidad;
	private JToggleButton bMasVistos, bGenerarPDF, bFiltros;

	public Ventana() {
		super("AppVideo");
		
		// Se estrablecen las dimensiones
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
		pCentral = new JPanel();
		pCentral.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pCentral.setBackground(new Color(96, 96, 96));
		laminaPrincipal.add(pCentral, BorderLayout.CENTER);
		
		// 1.2 addLaminaSuperior a LaminaPrincipal
		JPanel laminaSuperior = new JPanel();
		laminaSuperior.setLayout(new BoxLayout(laminaSuperior, BoxLayout.Y_AXIS));
		laminaPrincipal.add(laminaSuperior, BorderLayout.NORTH);
		
		// 1.2.1 addLaminaInicio a LaminaSuperior
		pInicio = new JPanel();
		pInicio.setLayout(new BoxLayout(pInicio, BoxLayout.X_AXIS));
		pInicio.setBorder(BorderFactory.createLoweredBevelBorder());
		laminaSuperior.add(pInicio);
		
		// 1.2.2 addLaminaFuncionalidad a LaminaSuperior
		pFuncionalidad = new JPanel();
		pFuncionalidad.setLayout(new BoxLayout(pFuncionalidad, BoxLayout.X_AXIS));
		pFuncionalidad.setBorder(new EmptyBorder(10, 0, 10, 0));
		laminaSuperior.add(pFuncionalidad);
		
		bgFuncionalidad = new ButtonGroup();
		
		// 1.2.2.1 addButtonExplorar a LaminaFuncionalidad
		addButtonFuncionalidad("Explorar");
		// 1.2.2.2 addButtonNuevaLista a LaminaFuncionalidad
		addButtonFuncionalidad("Nueva Lista");
		// 1.2.2.3 addButtonMisListas a LaminaFuncionalidad
		addButtonFuncionalidad("Mis Listas");
		// 1.2.2.4 addButtonRecientes a LaminaFuncionalidad
		addButtonFuncionalidad("Recientes");
//		if (controlador.isUsuarioPremium()) {
			// 1.2.2.5 addButtonMasVistos a LaminaFuncionalidad
			bMasVistos = addButtonFuncionalidad("Mas Vistos");
			// 1.2.2.6 addButtonGenerarPDF a LaminaFuncionalidad
			bGenerarPDF = addButtonFuncionalidad("Generar PDF");
			// 1.2.2.6 addButtonGenerarPDF a LaminaFuncionalidad
			bFiltros = addButtonFuncionalidad("Filtros");
//		}
		pFuncionalidad.add(Box.createHorizontalGlue());
		
		// 1.2.1.1 addLabelAppVideo a LaminaInicio
		JLabel labelAppVideo = new JLabel("AppVideo");
		labelAppVideo.setFont(new Font("Arial", Font.BOLD, 30));
		labelAppVideo.setForeground(Color.RED);
		pInicio.add(labelAppVideo);
		
		pInicio.add(Box.createHorizontalGlue());
		
		// 1.2.1.1 addLabelNombreUsuario a LaminaInicio
		lSaludo = new JLabel();
		lSaludo.setFont(new Font("Arial", Font.BOLD, 14));
		pInicio.add(lSaludo);
		
		pInicio.add(Box.createHorizontalGlue());
		
		// 1.2.1.1 addButtonLogin a LaminaInicio
		bLogin = addButtonInicio(LOGIN);
		// 1.2.1.1 addButtonRegistro a LaminaInicio
		bRegistro = addButtonInicio(REGISTRO);
		
		pInicio.add(Box.createHorizontalGlue());
		
		// 1.2.1.1 addButtonLogout a LaminaInicio
		bLogout = addButtonInicio(LOGOUT);
		
		pInicio.add(Box.createHorizontalGlue());
		
		// 1.2.1.1 addButtonPremium a LaminaInicio
		bPremium = addButtonInicio(PREMIUM);
		bPremium.setForeground(Color.RED);
		
		pInicio.add(Box.createHorizontalGlue());
		
		// 1.2.1.1 addButtonCargador (Pulsador Luz) a LaminaInicio
		addButtonCargador();
		
		// MOSTRAR
		mostrarLaminaSuperior();
		setLaminaCentral(LOGIN);
	}

	private JToggleButton addButtonFuncionalidad(String texto) {
		JToggleButton boton = new JToggleButton(texto);
		boton.setForeground(Color.WHITE);
		boton.setBackground(new Color(160, 160, 160));
		boton.addActionListener(e -> {
			setLaminaCentral(boton.getText());
		});
		pFuncionalidad.add(boton);
		bgFuncionalidad.add(boton);
		return boton;
	}

	private JButton addButtonInicio(String texto) {
		JButton boton = new JButton(texto);
		boton.addActionListener(e -> {
			String text = boton.getText();
			switch (text) {
			case LOGIN:
			case REGISTRO:
				setLaminaCentral(text);
				break;

			case LOGOUT:
				int salida = JOptionPane.showConfirmDialog(null, "¿Seguro de que quiere salir?", LOGOUT, JOptionPane.YES_NO_CANCEL_OPTION);
				switch (salida) {
				case JOptionPane.YES_OPTION:
					controlador.logoutUsuario();
					setLaminaCentral(LOGIN);
					mostrarLaminaSuperior();
//					System.exit(0);
					break;
				case JOptionPane.NO_OPTION:
				case JOptionPane.CANCEL_OPTION:
					break;
				}
				break;
				
			case PREMIUM:
				if (!controlador.isUsuarioPremium()) {
					int respuesta = JOptionPane.showConfirmDialog(
							this,
							"El plan Premium de AppVideo brindara acceso a los videos más vistos, la generacion de un PDF con las listas del usuario y la seleccion de un filtro para realizar busquedas.",
							"Adquirir plan Premium",
							JOptionPane.YES_NO_OPTION);
					if (respuesta == JOptionPane.YES_OPTION) {
						controlador.setUsuarioPremium();
						mostrarLaminaSuperior();
						// TODO repaint
					}
				}
				break;
			}
		});
		pInicio.add(boton);
		return boton;
	}
	
	private void addButtonCargador() {
		Luz pulsador = new Luz();
		pulsador.addEncendidoListener(e ->{
			IBuscadorVideos componente = new BuscadorVideos();
			componente.addVideosListener(controlador);
			JFileChooser fileChooser = new JFileChooser();
		    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		    int seleccion = fileChooser.showOpenDialog((Component)e.getSource());
		    if (seleccion == JFileChooser.APPROVE_OPTION) {
		        File fichero = fileChooser.getSelectedFile();
		        componente.setArchivoVideos(fichero);
		    } 
			
		});
		
		pInicio.add(pulsador);
	}

	public void setLaminaCentral(String titulo) {
		String t = titulo.replace(" ", "");
		try {
			controlador.stopVideo();
			
			Class<?> c = Class.forName("vista.Tab"+t);
			Class<?> cArgs[] = { Ventana.class };
			Constructor<?> constructor = c.getConstructor(cArgs);
			Object oParams[] = { this };
			JPanel nuevaLamina = (JPanel) constructor.newInstance(oParams);
			nuevaLamina.setSize(getPreferredSize());
			pCentral.removeAll();
			
			if (titulo.equals(LOGIN) || titulo.equals(REGISTRO))
				pCentral.setLayout(new FlowLayout());
			else
				pCentral.setLayout(new GridLayout(1, 1));
			
			pCentral.add(nuevaLamina);
			
			pCentral.revalidate();
			pCentral.repaint();
			
			if (nuevaLamina instanceof TabReproductor) {
				Thread reproductor = new Thread() {
					public void run() {
						controlador.playVideo();
					}
				};
				reproductor.start();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	public void mostrarLaminaSuperior() {
		String nombreUsuario = controlador.getNombreUsuario();
		lSaludo.setText("Hola " + nombreUsuario);
		
		boolean usuarioLogin = controlador.isUsuarioLogin();
		bLogin.setVisible(!usuarioLogin);
		bRegistro.setVisible(!usuarioLogin);
		bLogout.setVisible(usuarioLogin);
		bPremium.setVisible(usuarioLogin);
		pFuncionalidad.setVisible(usuarioLogin);
		boolean usuarioPremium = controlador.isUsuarioPremium();
		bMasVistos.setVisible(usuarioPremium);
		bGenerarPDF.setVisible(usuarioPremium);
		bFiltros.setVisible(usuarioPremium);
	}

}
