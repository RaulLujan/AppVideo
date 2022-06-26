package vista;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controlador.Controlador;

@SuppressWarnings("serial")
public class TabGenerarPDF extends JPanel {
	
	public TabGenerarPDF(Ventana window) {
		int respuesta = JOptionPane.showConfirmDialog(
				this,
				"¿Quieres generar un PDF con tus listas de videos?",
				"AppVideo",
				JOptionPane.YES_NO_OPTION);
		if (respuesta == JOptionPane.YES_OPTION)
			Controlador.getInstancia().generarPDF();
	}

}
