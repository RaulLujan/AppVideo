package vista;

import javax.swing.JPanel;

import controlador.Controlador;

public class GenerarPDF extends JPanel {
	
	public GenerarPDF() {
		Controlador.getInstancia().generarPDF();
	}

}
