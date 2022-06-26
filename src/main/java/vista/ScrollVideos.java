package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import controlador.Controlador;
import modelo.Video;

@SuppressWarnings("serial")
public class ScrollVideos extends JScrollPane {
	
	private DefaultListModel<CaratulaVideos> modeloVideos;
	
	private Video selected;

	public ScrollVideos(Ventana window) {
		
		// initModel
		modeloVideos = new DefaultListModel<>();
		// addListVideos
		JList<CaratulaVideos> listaVideos = new JList<>();
		listaVideos.setModel(modeloVideos);
		listaVideos.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		listaVideos.setVisibleRowCount(-1);
		listaVideos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		int ancho = Ventana.ancho * 1 / 4;
		setPreferredSize(new Dimension(ancho, 0));
		setMinimumSize(new Dimension(ancho, 0));
		listaVideos.setBackground(new Color(96, 96, 96));
		listaVideos.setCellRenderer(CaratulaVideos.getProcesador());
		listaVideos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				@SuppressWarnings("unchecked")
				JList<CaratulaVideos> resultados = (JList<CaratulaVideos>) e.getComponent();
				CaratulaVideos caratula = resultados.getSelectedValue();
				if (caratula != null) {
					selected = caratula.getVideo();
					if (e.getClickCount() == 2) {
						Controlador.getInstancia().setVideoActual(selected);
						window.setLaminaCentral("Reproductor");
					}
				}
			}
		});
		setViewportView(listaVideos);
	}
 	
	public Video getSelectedVideo() {
		return selected;
	}
 	
	public void setVideos(List<Video> lv) {
		modeloVideos.clear();
		for (Video v : lv)
			modeloVideos.addElement(new CaratulaVideos(v));
	}
}

@SuppressWarnings("serial")
class CaratulaVideos extends JPanel implements Cloneable {
	
	private static ListCellRenderer<CaratulaVideos> procesador = null;
	public static ListCellRenderer<CaratulaVideos> getProcesador() {
		if (procesador == null)
			procesador = new ListCellRenderer<CaratulaVideos>() {
				@Override
				public Component getListCellRendererComponent(JList<? extends CaratulaVideos> list,
						CaratulaVideos value, int index, boolean isSelected, boolean cellHasFocus) {

			        JList.DropLocation dropLocation = list.getDropLocation();
			        if (dropLocation != null
			                && !dropLocation.isInsert()
			                && dropLocation.getIndex() == index) {
			        	value.setBackground(SystemColor.BLUE);
			        	value.setForeground(SystemColor.WHITE);

			        } else if (isSelected) {
			        	value.setBackground(SystemColor.activeCaption);
			        	value.setForeground(SystemColor.WHITE);

			        } else {
			        	value.setBackground(SystemColor.controlHighlight);
			        	value.setForeground(SystemColor.BLACK);
			        };

					return value;
				}
			};
		return procesador;
	}

	private Video video;
	
	public CaratulaVideos(Video video) {
		this.video = video;

		int ancho = Ventana.ancho * 4 / 20;
		int largo = ancho * 2 / 3;
		setPreferredSize(new Dimension(ancho, largo));
		setMinimumSize(new Dimension(ancho, largo));
		setMaximumSize(new Dimension(ancho, largo));
		setLayout(new BorderLayout());
		
		ImageIcon icono = Controlador.getInstancia().getIconoVideo(video);
		add(new JLabel(icono), BorderLayout.CENTER);
		
		String tituloCorto = video.getTituloCorto();
		JLabel lTituloCorto = new JLabel(tituloCorto, SwingConstants.CENTER);
		add(lTituloCorto, BorderLayout.SOUTH);
		
	}
	
	
	public Video getVideo() {
		return video;
	}
	
}
