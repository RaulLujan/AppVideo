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
	
	private static ListCellRenderer<CaratulaVideo> procesador = null;
	public static ListCellRenderer<CaratulaVideo> getProcesador() {
		if (procesador == null)
			procesador = new ListCellRenderer<CaratulaVideo>() {
				@Override
				public Component getListCellRendererComponent(JList<? extends CaratulaVideo> list,
						CaratulaVideo value, int index, boolean isSelected, boolean cellHasFocus) {

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
	
	public CaratulaVideo(Video video) {
		this.video = video;

		Toolkit pantalla = Toolkit.getDefaultToolkit();
		Dimension dimPantalla = pantalla.getScreenSize();
		int anchoPantalla = dimPantalla.width;
		int altoPantalla  = dimPantalla.height;
		int ancho = (int) (anchoPantalla * 0.8 * 3 / 16);
		int alto  = (int) ( altoPantalla * 0.8 * 3 / 16);
		setPreferredSize(new Dimension(ancho, alto));
		setMinimumSize(new Dimension(ancho, alto));
		setLayout(new BorderLayout());
		
		Controlador controlador = Controlador.getInstancia();
		
		ImageIcon icono = controlador.getIconoVideo(video.getId());
		add(new JLabel(icono), BorderLayout.CENTER);
		
		String tituloCorto = video.getTituloCorto();
		add(new JLabel(tituloCorto), BorderLayout.SOUTH);
		
	}
	
	
	public Video getVideo() {
		return video;
	}
	
}
