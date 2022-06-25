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
		
		// CONF LAMINA
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setBackground(new Color(96, 96, 96));
		
		//GridBagConstraints constraints = new GridBagConstraints();
		//constraints.anchor = GridBagConstraints.CENTER;
		//constraints.insets = new Insets(3, 3, 3, 3);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public void setLamina(String titulo) {
		String t = titulo.replace(" ", "");
		try {
			Class<?> c = Class.forName("vista."+t);
			System.out.println(c.toString());
			Constructor<?> constructor = c.getConstructor();

			JPanel nuevaLamina = (JPanel) constructor.newInstance();
			nuevaLamina.setSize(getPreferredSize());
			removeAll();
			
			if (titulo.equals("Login") || titulo.equals("Registro"))
				setLayout(new FlowLayout());
			else
				setLayout(new GridLayout(1, 1));
			
			add(nuevaLamina);
			
			revalidate();
			repaint();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
