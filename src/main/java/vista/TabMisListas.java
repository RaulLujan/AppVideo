package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controlador.Controlador;
import modelo.ListaVideos;
import modelo.Video;

@SuppressWarnings("serial")
public class TabMisListas extends JPanel {
	
	private boolean isOpaque = false;
	
	private Controlador controlador = Controlador.getInstancia();
	
	private ListaVideos lv;
	private int segundos;
	private ReproductorLista reproductor = null;
	private TabMisListas tab;
	private Ventana window;
	
	public TabMisListas(Ventana window) {
		this.window = window;
		tab = this;
		
		setLayout(new BorderLayout());
		setBackground(new Color(96, 96, 96));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		// CONF MIS LISTAS
		
		// addSelector
		JPanel pSelector = new JPanel();
		pSelector.setLayout(new BorderLayout());
		pSelector.setBackground(new Color(96, 96, 96));
		pSelector.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		add(pSelector, BorderLayout.WEST);

		// addReproductor
//		JPanel pReproductor = new TabReproductor(window);
//		int ancho = Ventana.ancho * 3 / 4;
//		pReproductor.setPreferredSize(new Dimension(ancho, 0));
//		pReproductor.setMinimumSize(new Dimension(ancho, 0));
//		add(pReproductor, BorderLayout.CENTER);
		
		// CONF SELECTOR
		
		// addPanelSelecion
		JPanel pSeleccion = new JPanel();
		pSeleccion.setLayout(new BoxLayout(pSeleccion, BoxLayout.Y_AXIS));
		pSeleccion.setAlignmentX(Component.LEFT_ALIGNMENT);
		pSeleccion.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY, Color.GRAY));
		pSeleccion.setOpaque(isOpaque);
		pSelector.add(pSeleccion, BorderLayout.NORTH);
		
		// addPanelCancelar
		JPanel pCancelar = new JPanel();
//		pCancelar.setLayout(new BoxLayout(pCancelar, BoxLayout.Y_AXIS));
		pCancelar.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY, Color.GRAY));
		pCancelar.setOpaque(isOpaque);
		pSelector.add(pCancelar, BorderLayout.SOUTH);

		// addScrollVideos
		ScrollVideos pVideos = new ScrollVideos(window);
		pVideos.setVideos(new ArrayList<Video>());
		pSelector.add(pVideos, BorderLayout.CENTER);

		// CONF SELECCION
		
		// addLabelSeleccionar
		JLabel lSeleccionar = new JLabel("Seleccionar nombre lista:", SwingConstants.LEFT);
		lSeleccionar.setForeground(Color.WHITE);
		pSeleccion.add(lSeleccionar);
		
		// addComboBoxSeleccionar
		JComboBox<String> cbMisListas = new JComboBox<>();
		for (String miLista : controlador.getMisListas())
			cbMisListas.addItem(miLista);
		cbMisListas.setSelectedIndex(-1);
		cbMisListas.addItemListener(e -> {
			@SuppressWarnings("unchecked")
			JComboBox<String> campo = (JComboBox<String>) e.getSource();
			String nombre = (String) campo.getSelectedItem();
			lv = controlador.getMiLista(nombre);
			pVideos.setVideos(lv.getVideos());
		});
		pSeleccion.add(cbMisListas);
		
		// addButtonReproducir
		JButton bReproducir = new JButton("Reproducir");
		bReproducir.addActionListener(e -> {
			if (reproductor == null && lv != null) {
				String cadSegundos = JOptionPane.showInputDialog(null,
										"Indique los segundos",
										"Reproducir lista de videos",
										JOptionPane.QUESTION_MESSAGE);
				if (cadSegundos != null) {
					segundos = Integer.parseInt(cadSegundos);
					reproductor = new ReproductorLista();
					reproductor.setDetener(false);
					reproductor.start();
				}
			}

		});
		pSeleccion.add(bReproducir);

		// CONF CANCELAR
		
		// addButtonCancelar
		JButton bCancelar = new JButton("Cancelar");
		bCancelar.addActionListener(e -> {
			if (reproductor != null) {
				reproductor.setDetener(true);
				reproductor.despertar();
				reproductor = null;
			}
		});
		pCancelar.add(bCancelar);
		
	}
	
	private class ReproductorLista extends Thread {
		
		private volatile boolean detener;
		
		public void setDetener(boolean detener) {
			this.detener = detener;
		}
		
		public synchronized void run() {
			for (Video video : lv.getVideos()) {
				if (detener)
					break;
				controlador.setVideoActual(video);
				
				// addReproductor
				JPanel pReproductor = new TabReproductor(window);
				int ancho = Ventana.ancho * 3 / 4;
				pReproductor.setPreferredSize(new Dimension(ancho, 0));
				pReproductor.setMinimumSize(new Dimension(ancho, 0));
				tab.add(pReproductor, BorderLayout.CENTER);
				
				Thread reproductor = new Thread() {
					public void run() {
						controlador.playVideo();
					}
				};
				reproductor.start();
				try {
					wait(segundos*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				controlador.stopVideo();
			}
			reproductor = null;
		}
		
		public synchronized void despertar() {
			notify();
		}
		
	}
}
