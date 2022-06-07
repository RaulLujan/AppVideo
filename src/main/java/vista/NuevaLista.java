package vista;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class NuevaLista extends JPanel {
	private boolean isOpaque = false;
	
	public NuevaLista() {
		confLamina();
	}

	private void confLamina() {
		setLayout(new GridBagLayout());
		setBackground(new Color(96, 96, 96));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		confIntroducirNombre();
	}
	
	private void confIntroducirNombre() {
		JPanel pGeneral = new JPanel();
		JPanel pGrupoBuscar = new JPanel();
		
		JLabel lIntroducir = new JLabel("Introducir nombre lista:", SwingConstants.RIGHT);
		JTextField tfNombreLista = new JTextField();
		JButton bBuscar = new JButton("Buscar");
		JButton bEliminar = new JButton("Eliminar");
		
		pGeneral.setLayout(new BoxLayout(pGeneral, BoxLayout.Y_AXIS));
		pGeneral.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY, Color.GRAY));
		pGeneral.setOpaque(isOpaque);
		
		pGrupoBuscar.setLayout(new FlowLayout());
		pGrupoBuscar.setOpaque(isOpaque);
		
		lIntroducir.setForeground(Color.WHITE);
		
		pGrupoBuscar.add(tfNombreLista);
		pGrupoBuscar.add(bBuscar);
		
		pGeneral.add(lIntroducir);
		pGeneral.add(pGrupoBuscar);
		pGeneral.add(bEliminar);
		
		add(pGeneral);
		
	}
	
	private void confBusqueda() {
		
	}
	
	private void confBotonesVideos() {
		
	}
	
	private void confListaActual() {
		
	}
	
	private void confVideos() {
		
	}
	
	
}
