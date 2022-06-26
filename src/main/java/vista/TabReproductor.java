package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

import controlador.Controlador;
import modelo.Etiqueta;
import modelo.Video;
import tds.video.VideoWeb;

@SuppressWarnings("serial")
public class TabReproductor extends JPanel {

	private Controlador controlador = Controlador.getInstancia();

	public TabReproductor(Ventana window) {
		VideoWeb videoWeb = controlador.getVideoWeb();
		Video video = controlador.getVideoActual();

		setLayout(new BorderLayout());
		setBackground(new Color(96, 96, 96));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// CONF REPRODUCTOR
		
		// conf parte NORTH
		JPanel pInfoVideo = new JPanel();
		pInfoVideo.setBackground(new Color(96, 96, 96));
		pInfoVideo.setLayout(new BoxLayout(pInfoVideo, BoxLayout.Y_AXIS));
		// addTitulo
		addLabel(pInfoVideo, video.getTitulo(), 30);
		// addNumRepro
		addLabel(pInfoVideo, "Visto por : " + video.getNumRepro() + " usuarios", 24);
		add(pInfoVideo, BorderLayout.NORTH);
		
		// conf parte CENTER
		
		JPanel pVideoReproductor = new JPanel();
		JPanel pVideo = new JPanel();
		pVideo.setOpaque(false);
		pVideoReproductor.setBackground(new Color(96, 96, 96));
		pVideoReproductor.setLayout(new BoxLayout(pVideoReproductor, BoxLayout.Y_AXIS));
		
		// addReproductor
		pVideoReproductor.add(Box.createHorizontalGlue());
		pVideo.add(videoWeb);
		videoWeb.setAlignmentX(CENTER_ALIGNMENT);
		pVideoReproductor.add(pVideo);
		pVideoReproductor.add(Box.createHorizontalGlue());
		
		// addCopyrigth
		JLabel copyrigth = addLabel(pVideoReproductor, videoWeb.getVersion(), 12);
		copyrigth.setForeground(Color.WHITE);
		// addEtiquetas
		JPanel panelEtiquetas = new JPanel();
		panelEtiquetas.setBackground(new Color(96, 96, 96));
		
		for (Etiqueta et : video.getEtiquetas())
			addLabelEtiqueta(panelEtiquetas, et.getNombre(), 14);
		
		pVideoReproductor.add(panelEtiquetas);
		
		add(pVideoReproductor, BorderLayout.CENTER);

		// conf parte SOUTH
		// addNuevaEtiqueta
		JPanel panelNuevaEtiqueta = new JPanel();
		panelNuevaEtiqueta.setBackground(new Color(96, 96, 96));
		
		addLabel(panelNuevaEtiqueta, "Nueva etiqueta:", 10);
		JTextField campoNuevaEtiqueta = new JTextField();
		campoNuevaEtiqueta.setColumns(16);
		panelNuevaEtiqueta.add(campoNuevaEtiqueta);
		JButton botonNuevaEtiqueta = new JButton("Añadir");
		panelNuevaEtiqueta.add(botonNuevaEtiqueta);
		botonNuevaEtiqueta.addActionListener(e -> {
			String nombre = campoNuevaEtiqueta.getText();
			if (!nombre.trim().isEmpty()) {
				boolean ok = controlador.setEtiquetaVideo(nombre);
				if (ok) {
					addLabelEtiqueta(panelEtiquetas, nombre, 10);
					campoNuevaEtiqueta.setText("");
				}
			}
		});
		
		add(panelNuevaEtiqueta, BorderLayout.SOUTH);

	}
	
	private JLabel addLabel(JPanel panel, String texto, int size) {
		JLabel label = new JLabel(texto, SwingConstants.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, size));
		label.setForeground(Color.WHITE);
		label.setAlignmentX(LEFT_ALIGNMENT);
		panel.add(label);
		return label;
	}

	private JLabel addLabelEtiqueta(JPanel panel, String texto, int size) {
		JLabel label = new JLabel(texto);
		label.setFont(new Font("Arial", Font.BOLD, size));
		label.setOpaque(true);
		label.setForeground(Color.BLACK);
		label.setBackground(Color.LIGHT_GRAY);
		panel.add(label);
		
		return label;
	}

}
