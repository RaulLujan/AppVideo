package vista;

import javax.swing.JPanel;

import controlador.Controlador;

public class TabGenerarPDF extends JPanel {
	
	public TabGenerarPDF(Ventana parent) {
		Controlador.getInstancia().generarPDF();
	}

}
