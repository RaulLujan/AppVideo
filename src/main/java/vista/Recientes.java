package vista;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Recientes extends JPanel{
	private boolean isOpaque = false;

	public Recientes() {
		confLamina();
	}

	private void confLamina() {
		setLayout(new BorderLayout());
		setBackground(new Color(96, 96, 96));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		confVideos();
	}

	private void confVideos() {
		JPanel l = new JPanel();
		add(l, BorderLayout.CENTER);
	}
}
