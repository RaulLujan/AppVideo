package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import modelo.Video;

@SuppressWarnings("serial")
public class Buscador extends JPanel {
	
	private boolean isOpaque = false;
	private int ancho = Ventana.ancho * 3 / 4;
	
	private Controlador controlador = Controlador.getInstancia();
	private ScrollVideos pVideos;
	
	public Buscador(Ventana window) {
		this(window, new DefaultListModel<>(), new DefaultListModel<>());
	}
	public Buscador(Ventana window,
			DefaultListModel<String> modeloEtiqDisp,
			DefaultListModel<String> modeloEtiqSelec) {
		
		setLayout(new BorderLayout());
		setBackground(new Color(96, 96, 96));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setOpaque(isOpaque);
		setPreferredSize(new Dimension(ancho, 0));
		setMinimumSize(new Dimension(ancho, 0));
		
		// CONF BUSCADOR

		// addPanelBusqueda
		JPanel pBusqueda = new JPanel();
		pBusqueda.setLayout(new BoxLayout(pBusqueda, BoxLayout.Y_AXIS));
		pBusqueda.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY, Color.GRAY));
		pBusqueda.setOpaque(isOpaque);
		add(pBusqueda, BorderLayout.NORTH);

		pVideos = new ScrollVideos(window);
		pVideos.setPreferredSize(new Dimension(ancho, 0));
		pVideos.setMinimumSize(new Dimension(ancho, 0));
		pVideos.setVideos(new ArrayList<Video>());
//		pVideos.setVideos(controlador.getVideosExplorar());
		add(pVideos, BorderLayout.CENTER);
		
		// CONF BUSQUEDA
		
		// addPanelBuscar
		JPanel pBuscar = new JPanel();
		pBuscar.setOpaque(isOpaque);
		pBusqueda.add(pBuscar);
		
		// addPanelNuevaBusqueda
		JPanel pNuevaBusqueda = new JPanel();
		pNuevaBusqueda.setOpaque(isOpaque);
		pBusqueda.add(pNuevaBusqueda);

		// CONF BUSCAR
		
		// addLabelBuscar
		JLabel lBuscar = new JLabel("Buscar titulo:", SwingConstants.RIGHT);
		lBuscar.setForeground(Color.WHITE);
		lBuscar.setBorder(new EmptyBorder(5, 5, 5, 5));
		pBuscar.add(lBuscar);
		
		// addFieldBuscar
		JTextField tfBuscar = new JTextField(20);
		pBuscar.add(tfBuscar);

		pBuscar.add(Box.createHorizontalGlue());

		// addButtonBuscar
		JButton bBuscar = new JButton("Buscar");
		bBuscar.addActionListener(e -> {
			List<String> etiquetas = new ArrayList<>(modeloEtiqSelec.getSize());
			for (int i = 0; i < modeloEtiqSelec.getSize(); i++)
				etiquetas.add(i, modeloEtiqSelec.getElementAt(i));
			String subtitulo = tfBuscar.getText();
			pVideos.setVideos(controlador.getVideosBuscar(subtitulo,etiquetas));
		});
		pBuscar.add(bBuscar);

		// CONF NUEVA BUSQUEDA
		
		// addButtonNuevaBusqueda
		JButton bNuevaBusqueda = new JButton("Nueva busqueda");
		bNuevaBusqueda.addActionListener(e -> {
			modeloEtiqSelec.removeAllElements();
			modeloEtiqDisp.removeAllElements();
			modeloEtiqDisp.addAll(controlador.getEtiquetas());
			tfBuscar.setText("");
//			pVideos.setVideos(controlador.getVideosExplorar());
			pVideos.setVideos(new ArrayList<Video>());
		});
		pNuevaBusqueda.add(bNuevaBusqueda);
	}
	
	public Video getSelectedVideo() {
		return pVideos.getSelectedVideo();
	}
	
}
