package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Lamina extends JPanel {
	
	public Lamina() {
		
		// CONF LAMINA
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 5, 10, 5));
		
		// addLaminas superior y central
		LaminaCentral laminaCentral = new LaminaCentral();
		LaminaSuperior laminaSuperior = new LaminaSuperior(laminaCentral);
		add(laminaSuperior, BorderLayout.NORTH);
		add(laminaCentral, BorderLayout.CENTER);
	}

//	public void actionPerformed(ActionEvent e) {
//		setBackground(Color.WHITE);
//	}
}
