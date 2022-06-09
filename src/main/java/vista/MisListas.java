package vista;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MisListas extends JPanel{

	public MisListas() {
		confLamina();
	}
	
	private void confLamina() {
		setLayout(new BorderLayout());
		setBackground(new Color(96, 96, 96));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		confSeleccionar();
		confVideos();
	}
	
	private void confSeleccionar() {
		JPanel pGeneral = new JPanel();
		JPanel pSeleccionarLista = new JPanel();
		JPanel pListaVideos = new JPanel();
		JPanel pCancelar = new JPanel();
		
		JLabel lSeleccionar = new JLabel("Seleccione la lista:");
		JComboBox<String> cListaVideos = new JComboBox<>();
		JButton bReproducir = new JButton("Reproducir");
		JButton bCancelar = new JButton("Cancelar");
		
		pGeneral.setLayout(new BoxLayout(pGeneral, BoxLayout.Y_AXIS));
		
		lSeleccionar.setForeground(Color.WHITE);
		
	}
	
	private void confVideos() {
		
	}
}
