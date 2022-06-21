package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Lamina extends JPanel implements ActionListener{

	
	public Lamina() {
		confLamina();
	}

	public void actionPerformed(ActionEvent e) {
		setBackground(Color.WHITE);
	}
	
	private void confLamina() {
		LaminaCentral laminaCentral = LaminaCentral.getInstancia();
		LaminaSuperior laminaSuperior = LaminaSuperior.getInstancia(laminaCentral);
		
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 5, 10, 5));
		
		add(laminaSuperior, BorderLayout.NORTH);
		add(laminaCentral, BorderLayout.CENTER);		
	}
}
