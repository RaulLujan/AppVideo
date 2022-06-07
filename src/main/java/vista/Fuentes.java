package vista;

import java.awt.GraphicsEnvironment;
import java.util.Iterator;

import javax.swing.JOptionPane;

public class Fuentes {
	public static void main(String[] args) {
		boolean isFuente = false;
		String fuente = JOptionPane.showInputDialog("Introduce fuente");
		
		String[] nombresFuentes = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		
		for (String nombre :nombresFuentes) {
			System.out.println(nombre);
			if(nombre.equals(fuente)) {
				isFuente = true;
			}
		}
		
		if(isFuente)
			System.out.println("La fuente OK");
		else
			System.out.println("La fuente KO");
		
	}
}
