package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controlador.Controlador;
import modelo.ListaVideos;
import modelo.Video;

@SuppressWarnings("serial")
public class TabNuevaLista extends JPanel {
	
	private boolean isOpaque = false;
	
	private Controlador controlador = Controlador.getInstancia();
	
	private ListaVideos lv = null;
	
	public TabNuevaLista(Ventana window) {
		
		setLayout(new BorderLayout());
		setBackground(new Color(96, 96, 96));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// CONF NUEVA LISTA

		// addPanelEditor
		JPanel pEditor = new JPanel();
		pEditor.setLayout(new BorderLayout());
		pEditor.setBackground(new Color(96, 96, 96));
		pEditor.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		add(pEditor, BorderLayout.WEST);
		
		// addPanelBuscador
		Buscador pBuscador = new Buscador(window);
		add(pBuscador, BorderLayout.CENTER);
		
		// CONF EDITOR
		
		// addPanelBusqueda
		JPanel pBusqueda = new JPanel();
		pBusqueda.setLayout(new BoxLayout(pBusqueda, BoxLayout.Y_AXIS));
		pBusqueda.setAlignmentX(Component.LEFT_ALIGNMENT);
		pBusqueda.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY, Color.GRAY));
		pBusqueda.setOpaque(isOpaque);
		pEditor.add(pBusqueda, BorderLayout.NORTH);
		
		// addPanelEdicion
		JPanel pEdicion = new JPanel();
		pEdicion.setLayout(new BoxLayout(pEdicion, BoxLayout.Y_AXIS));
		pEdicion.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY, Color.GRAY));
		pEdicion.setOpaque(isOpaque);
		pEditor.add(pEdicion, BorderLayout.SOUTH);

		// addScrollVideos
		ScrollVideos pVideos = new ScrollVideos(window);
		pVideos.setVideos(new ArrayList<Video>());
		pEditor.add(pVideos, BorderLayout.CENTER);

		// CONF BUSQUEDA
		
		// addLabelBuscar
		JLabel lIntroducir = new JLabel("Introducir nombre lista:", SwingConstants.LEFT);
		lIntroducir.setForeground(Color.WHITE);
		pBusqueda.add(lIntroducir);
		
		JPanel pBuscar = new JPanel();
		pBuscar.setOpaque(isOpaque);
		// addFieldBuscar
		JTextField tfNombreLista = new JTextField(10);
		pBuscar.add(tfNombreLista);
		// addButtonBuscar
		JButton bBuscar = new JButton("Buscar");
		bBuscar.addActionListener(e -> {
			String nombre = tfNombreLista.getText();
			lv = controlador.getMiLista(nombre);
			if (lv == null) {
				int respuesta = JOptionPane.showConfirmDialog(
						this,
						"¿Desea crear la lista " + nombre + "?",
						"AppVideo",
						JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_OPTION)
					lv = controlador.addListaVideos(nombre);
				pVideos.setVideos(new ArrayList<Video>());
				
			} else {
				pVideos.setVideos(lv.getVideos());
			}
		});
		pBuscar.add(bBuscar);
		pBusqueda.add(pBuscar);

		// addButtonEliminar
		JButton bEliminar = new JButton("Eliminar");
		bEliminar.addActionListener(e -> {
			if (lv != null) {
				controlador.removeListaVideos(lv);
				pVideos.setVideos(new ArrayList<Video>());
			}
		});
		pBusqueda.add(bEliminar);

		// CONF EDICION
		
		JPanel pEditar = new JPanel();
		pEditar.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
		pEditar.setOpaque(isOpaque);
		// addButtonAnyadir
		JButton bAnyadir = new JButton("Añadir");
		bAnyadir.addActionListener(e -> {
			Video selected = pBuscador.getSelectedVideo();
			if (lv != null && selected != null) {
				controlador.addVideoToListaVideos(selected, lv);
				pVideos.setVideos(lv.getVideos());
			}
		});
		pEditar.add(bAnyadir);
		pEditar.add(Box.createHorizontalGlue());
		// addButtonQuitar
		JButton bQuitar = new JButton("Quitar");
		bQuitar.addActionListener(e -> {
			Video selected = pVideos.getSelectedVideo();
			if (lv != null && selected != null) {
				controlador.removeVideoFromListaVideos(selected, lv);
				pVideos.setVideos(lv.getVideos());
			}
		});
		pEditar.add(bQuitar);
		pEdicion.add(pEditar);
	}

}
