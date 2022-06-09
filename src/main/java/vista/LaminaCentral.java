package vista;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.lang.reflect.Constructor;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class LaminaCentral extends JPanel {

	public LaminaCentral() {
		confLamina();
	}

	private void confLamina() {
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setBackground(new Color(96, 96, 96));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public void setLamina(String titulo) {
		String t = titulo.replace(" ", "");
		try {
			Class<?> c = Class.forName("vista."+t);
			
			Constructor<?> constructor = c.getConstructor();

			JPanel nuevaLamina = (JPanel) constructor.newInstance();
			nuevaLamina.setSize(getPreferredSize());
			removeAll();
			add(nuevaLamina);
			
			revalidate();
			repaint();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
