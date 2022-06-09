package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MisListas extends JPanel{
	private boolean isOpaque = false;

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
		
		JLabel lSeleccionar = new JLabel("Seleccione la lista:", SwingConstants.LEFT);
		JComboBox<String> cListaVideos = new JComboBox<>();
		String[] listaVideos = new String[] {"ListaVideo1", "ListaVideo2", "ListaVideo3"};
		JButton bReproducir = new JButton("Reproducir");
		JButton bCancelar = new JButton("Cancelar");
		
		pGeneral.setLayout(new BoxLayout(pGeneral, BoxLayout.Y_AXIS));
		pGeneral.setOpaque(isOpaque);
		
		pSeleccionarLista.setLayout(new FlowLayout());//(new BoxLayout(pSeleccionarLista, BoxLayout.Y_AXIS));
		pSeleccionarLista.setOpaque(isOpaque);
		pSeleccionarLista.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY, Color.GRAY));
		
		pListaVideos.setOpaque(isOpaque);
		pListaVideos.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY, Color.GRAY));
		
		pCancelar.setLayout(new BoxLayout(pCancelar, BoxLayout.X_AXIS));
		pCancelar.setOpaque(isOpaque);
		pCancelar.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY, Color.GRAY));
		
		lSeleccionar.setForeground(Color.WHITE);
		
		for (String lv : listaVideos) {
			cListaVideos.addItem(lv);
		}
		
		pSeleccionarLista.setAlignmentX(Component.LEFT_ALIGNMENT);
		pSeleccionarLista.add(lSeleccionar);
		pSeleccionarLista.add(cListaVideos);
		pSeleccionarLista.add(bReproducir);
		
		pCancelar.add(Box.createVerticalGlue());
		pCancelar.add(Box.createHorizontalGlue());
		pCancelar.add(bCancelar);
		pCancelar.add(Box.createVerticalGlue());
		pCancelar.add(Box.createHorizontalGlue());
		
		pGeneral.add(pSeleccionarLista);
		pGeneral.add(pListaVideos);
		pGeneral.add(pCancelar);
		
		add(pGeneral, BorderLayout.WEST);
		
	}
	
	private void confVideos() {
		JPanel l = new JPanel();
		add(l, BorderLayout.CENTER);
	}
}
