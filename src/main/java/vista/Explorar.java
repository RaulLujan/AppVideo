package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;

public class Explorar extends JPanel {
	private boolean isOpaque = false;

	public Explorar() {
		confLamina();
	}

	private void confLamina() {
		setLayout(new BorderLayout());
		setBackground(new Color(96, 96, 96));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		confBusqueda();
		confEtiquetas();
		confVideos();
	}

	private void confBusqueda() {
		JPanel pBusqueda = new JPanel();
		JPanel pInsertarBusq = new JPanel();

		JLabel lBuscar = new JLabel("Buscar título: ", SwingConstants.RIGHT);
		JTextField tfBuscar = new JTextField(20);
		JButton bBuscar = new JButton("Buscar");
		JButton bNuevaBusq = new JButton("Nueva búsqueda");

		pBusqueda.setLayout(new BoxLayout(pBusqueda, BoxLayout.Y_AXIS));
		pBusqueda.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY, Color.GRAY));
		pBusqueda.setOpaque(isOpaque);

		pInsertarBusq.setLayout(new FlowLayout(FlowLayout.CENTER));
		pInsertarBusq.setOpaque(isOpaque);

		lBuscar.setForeground(Color.WHITE);

		pInsertarBusq.add(Box.createHorizontalGlue());
		pInsertarBusq.add(lBuscar);
		pInsertarBusq.add(tfBuscar);
		pInsertarBusq.add(Box.createHorizontalGlue());
		pInsertarBusq.add(bBuscar);
		pInsertarBusq.add(Box.createHorizontalGlue());

		pBusqueda.add(pInsertarBusq);
		pBusqueda.add(bNuevaBusq);

		add(pBusqueda, BorderLayout.NORTH);
	}

	private void confEtiquetas() {
		JPanel pEtiquetas = new JPanel();

		JLabel lEtiqDisponibles = new JLabel("Etiquetas disponibles");
		JList<String> listaDisponibles = new JList<>();
		JLabel lEtiqSeleccionadas = new JLabel("Etiquetas seleccionadas búsqueda");
		JList<String> listaSeleccionadas = new JList<>();

		pEtiquetas.setLayout(new BoxLayout(pEtiquetas, BoxLayout.Y_AXIS));
		pEtiquetas.setOpaque(isOpaque);
		pEtiquetas.setAlignmentX(CENTER_ALIGNMENT);
		pEtiquetas.setAlignmentY(CENTER_ALIGNMENT);
		pEtiquetas.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY, Color.GRAY));
		
		lEtiqDisponibles.setForeground(Color.WHITE);
		lEtiqDisponibles.setBorder(new EmptyBorder(5, 5, 5, 5));
		lEtiqSeleccionadas.setForeground(Color.WHITE);

		listaDisponibles.setVisibleRowCount(1);
		listaDisponibles.setModel(new AbstractListModel<String>() {
			String[] values = new String[] { "Etiqueta1", "Etiqueta2", "Etiqueta3", "Etiqueta4" };

			public int getSize() {
				return values.length;
			}

			@Override
			public String getElementAt(int index) {
				return values[index];
			}
		});
		

		pEtiquetas.add(lEtiqDisponibles);
		pEtiquetas.add(listaDisponibles); //, BorderLayout.CENTER);
		pEtiquetas.add(Box.createVerticalGlue());
		pEtiquetas.add(lEtiqSeleccionadas);
		pEtiquetas.add(Box.createVerticalGlue());

		add(pEtiquetas, BorderLayout.EAST);
	}

	private void confVideos() {
		LaminaVideos pVideos = new LaminaVideos();
		
//		JTable tablaVideos = new JTable(new TablaVideoModel());
		pVideos.setOpaque(isOpaque);
		pVideos.setLayout(new FlowLayout(FlowLayout.LEFT));
		pVideos.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY, Color.GRAY));
		
		add(pVideos, BorderLayout.CENTER);
	}
	
	
//	
//	class TablaVideoModel extends AbstractTableModel{
//		
//		private String videos = 
//
//		@Override
//		public int getRowCount() {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		@Override
//		public int getColumnCount() {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		@Override
//		public Object getValueAt(int rowIndex, int columnIndex) {
//			// TODO Auto-generated method stub
//			return null;
//		}
		
//	}
}
