package vista;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import controlador.Controlador;
import tds.video.VideoWeb;

public class ReproducionVideo extends JPanel{
	
	private Controlador controlador;
	
	public ReproducionVideo() {
		controlador = Controlador.getInstaciaUnica();
		confLamina();
	}

	private void confLamina() {
		setLayout(new GridBagLayout());
		setBackground(new Color(96, 96, 96));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	}
	
	
	private void addReproductor() {
		JPanel panelReproductor = new JPanel();
		panelReproductor.setLayout(new GridLayout(2, 2));
		
		VideoWeb videoWeb = controlador.getVideoWeb();
		panelReproductor.add(videoWeb);
	}
	
	
	

}
