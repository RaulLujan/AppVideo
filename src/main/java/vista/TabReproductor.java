package vista;

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
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(new Color(96, 96, 96));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// CONF REPRODUCTOR
		
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
				boolean ok = controlador.setEtiquetaVideo(nombre);
				if (ok) {
					addLabelEtiqueta(panelEtiquetas, nombre, 10);
					campoNuevaEtiqueta.setText("");
				}
			}
		});
		
	}
	
	private JPanel addPanel() {
		JPanel panel = new JPanel();
		panel.setBackground(new Color(96, 96, 96));
		add(panel);
		return panel;
	}

	private JLabel addLabel(JPanel panel, String texto, int size) {
		JLabel label = new JLabel(texto);
		label.setFont(new Font("Arial", Font.BOLD, size));
		label.setForeground(Color.WHITE);
		panel.add(label, SwingConstants.CENTER);
		return label;
	}
	
	private JLabel addLabelEtiqueta(JPanel panel, String texto, int size) {
		JLabel label = new JLabel(texto);
		label.setFont(new Font("Arial", Font.BOLD, size));
		label.setForeground(Color.BLACK);
		label.setBackground(Color.GRAY);
		panel.add(label, SwingConstants.CENTER);
		return label;
	}

}
