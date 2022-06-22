package vista;

import javax.swing.JPanel;

import controlador.Controlador;

public class GenerarPDF extends JPanel {
	public GenerarPDF() {
		confLamina();
	}

	private void confLamina() {
		generarPDF();
	}

	private void generarPDF() {
		Controlador.getInstancia().generarPDF();
	}

}
