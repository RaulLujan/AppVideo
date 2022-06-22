package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class LaminaVideos extends JPanel {
	private boolean isOpaque = false;

	public LaminaVideos() {
		confLamina();
	}

	private void confLamina() {
		setOpaque(isOpaque);
		setLayout(new GridLayout(1, 1));
		setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY, Color.GRAY));
		getVideos();
	}
	
	private void getVideos() {
//		JPanel videos = new JPanel();
//		GridBagConstraints constraintsPVideos = new GridBagConstraints();
//		constraintsPVideos.insets = new Insets(2, 10, 10, 2);
//		constraintsPVideos.fill = GridBagConstraints.BOTH;
//		constraintsPVideos.gridx = 0;
//		constraintsPVideos.gridy = 0;
//		
//		add(videos, constraintsPVideos);
		
	}
}
