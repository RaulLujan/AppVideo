package vista;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Recientes extends JPanel{
	private boolean isOpaque = false;

	public Recientes() {
		
		// CONF LAMINA
		setLayout(new BorderLayout());
		setBackground(new Color(96, 96, 96));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		// addReproductor
		JPanel repr = new Reproductor(null);
		add(repr, BorderLayout.CENTER);
		
		// addRecientes
		JPanel l = new JPanel();
		add(l, BorderLayout.EAST);
	}
}
