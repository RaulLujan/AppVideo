package vista;

import java.awt.Dimension;
//import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Ventana extends JFrame{
	public Ventana() {
		super("AppVideo");
		confVentana();
		addLaminaPrincipal();
	}
	
	public void confVentana() {
		Toolkit pantalla = Toolkit.getDefaultToolkit();
		Dimension dPantalla = pantalla.getScreenSize();
		int alturaPantalla = dPantalla.height;
		int anchoPantalla = dPantalla.width;
		
		// ancho/altoPantalla / 1.5 
		setSize(anchoPantalla/2 + anchoPantalla/6, alturaPantalla/2 + alturaPantalla/6);
		setLocation(anchoPantalla/6, alturaPantalla/6);
		
		//setSize(anchoPantalla/2, alturaPantalla/2);
		//setLocation(anchoPantalla/4, alturaPantalla/4);
		
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Image icono = pantalla.getImage("src/recursos/icono.png");
		setIconImage(icono);
	}
	
	public void addLaminaPrincipal() {
		Lamina pPrincipal = new Lamina();
		add(pPrincipal);
	}
	
}
