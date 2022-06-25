package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controlador.Controlador;
import modelo.Etiqueta;
import modelo.Video;
import tds.video.VideoWeb;

public class Reproductor extends JPanel {
	
	private Video video;

	public Reproductor(Video video) {
		this.video = video;
		Controlador controlador = Controlador.getInstancia();
		VideoWeb videoWeb = controlador.getVideoWeb();
		
		// CONF LAMINA
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(new Color(96, 96, 96));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		// addTitulo
		addLabel(this, video.getTitulo(), 30);
		// addNumRepro
		addLabel(this, "Visto por : "+video.getNumRepro()+" usuarios", 24);
		// addReproductor
		add(videoWeb);
		// addCopyrigth
		JLabel copyrigth = addLabel(this, videoWeb.getVersion(), 10);
		copyrigth.setForeground(Color.WHITE);
		// addEtiquetas
		JPanel panelEtiquetas = addPanel();
		for (Etiqueta et : video.getEtiquetas())
			addLabel(panelEtiquetas, et.getNombre(), 10);
		// addNuevaEtiqueta
		JPanel panelNuevaEtiqueta = addPanel();
		addLabel(panelNuevaEtiqueta, "Nueva etiqueta:", 10);
		JTextField campoNuevaEtiqueta = new JTextField();
		campoNuevaEtiqueta.setColumns(16);
		panelNuevaEtiqueta.add(campoNuevaEtiqueta);
		JButton botonNuevaEtiqueta = new JButton("Añadir");
		panelNuevaEtiqueta.add(botonNuevaEtiqueta);
		botonNuevaEtiqueta.addActionListener(e -> {
			String nombre = campoNuevaEtiqueta.getText();
			if (!nombre.trim().isEmpty()) {
				boolean ok = controlador.etiquetarVideo(video, nombre);
				if (ok)
					addLabel(panelEtiquetas, nombre, 10);
			}
		});
		
	}
	
	private JPanel addPanel() {
		JPanel panel = new JPanel();
//		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBackground(new Color(96, 96, 96));
		add(panel);
		return panel;
	}

	private JLabel addLabel(JPanel panel, String texto, int size) {
		JLabel label = new JLabel(texto);
		label.setFont(new Font("Arial", Font.BOLD, size));		
		panel.add(label);
		return label;
	}

	public void reproducirVideo() {
		Thread reproductor = new Thread() {
			public void run() {
				Controlador.getInstancia().reproducirVideo(video);
			}
		}
		reproductor.start();
	}
	
	public void cancelarVideo() {
		Controlador.getInstancia().cancelarVideo();
	}
	

}
