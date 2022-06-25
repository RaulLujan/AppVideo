package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import controlador.Controlador;
import modelo.Video;


@SuppressWarnings("serial")
public class CaratulaVideo extends JPanel implements Cloneable {
	
	private Video video;
	
	private static ListCellRenderer<CaratulaVideo> procesador = null;
	
	public static ListCellRenderer<CaratulaVideo> getProcesador() {
		if (procesador == null)
			procesador = new ListCellRenderer<CaratulaVideo>() {
				@Override
				public Component getListCellRendererComponent(JList<? extends CaratulaVideo> list,
						CaratulaVideo value, int index, boolean isSelected, boolean cellHasFocus) {
					Color background, foreground;

			        JList.DropLocation dropLocation = list.getDropLocation();
			        if (dropLocation != null
			                && !dropLocation.isInsert()
			                && dropLocation.getIndex() == index) {

			            background = SystemColor.BLUE;
			            foreground = SystemColor.WHITE;

			        } else if (isSelected) {
			            background = SystemColor.activeCaption;
			            foreground = SystemColor.WHITE;

			        } else {
			            background = SystemColor.controlHighlight;
			            foreground = SystemColor.BLACK;
			        };

			        value.setBackground(background);
			        value.setForeground(foreground);
					return value;
				}
			};
		return procesador;
	}

	public CaratulaVideo(Video video) {
		this.video = video;
		
		int ancho = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.8 * 3 / 16);
		int largo =  (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.8 * 3 / 16);
		setPreferredSize(new Dimension(ancho, largo));
		setMinimumSize(new Dimension(ancho, largo));
		setLayout(new BorderLayout());
		
		Controlador controlador = Controlador.getInstancia();
		
		ImageIcon icono = controlador.getIconoVideo(video.getId());
		add(new JLabel(icono), BorderLayout.CENTER);
		
		String tituloCorto = controlador.getTituloCortoVideo(video.getId());
		add(new JLabel(tituloCorto), BorderLayout.SOUTH);
		
	}
	
	
	public Video getVideo() {
		return video;
	}
	
}
