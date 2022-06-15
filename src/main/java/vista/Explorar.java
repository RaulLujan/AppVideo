package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;

import controlador.Controlador;
import modelo.CatalogoVideos;
import modelo.Video;

public class Explorar extends JPanel {
	private static final int NUM_COLUMNAS = 4;
	
	private JTextField tfBuscar;
	private JList<String> listaDisponibles;
	private JList<String> listaSeleccionadas;
	private List<Video> listaVideos;
	private Video[][] tablaVideos;
	
	private boolean buscado = false;
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
		tfBuscar = new JTextField(20);
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

		addListenerBotonNuevaBusqueda(bNuevaBusq);
		addListenerBotonBuscar(bBuscar);
	}

	private void confEtiquetas() {
		JPanel pEtiquetas = new JPanel();

		JLabel lEtiqDisponibles = new JLabel("Etiquetas disponibles");
		listaDisponibles = new JList<>();
		JLabel lEtiqSeleccionadas = new JLabel("Etiquetas seleccionadas búsqueda");
		listaSeleccionadas = new JList<>();

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
		pEtiquetas.add(listaDisponibles); // , BorderLayout.CENTER);
		pEtiquetas.add(Box.createVerticalGlue());
		pEtiquetas.add(lEtiqSeleccionadas);
		pEtiquetas.add(Box.createVerticalGlue());

		add(pEtiquetas, BorderLayout.EAST);
	}

	private void confVideos() {
		LaminaVideos pVideos = new LaminaVideos();
		
		if(buscado) {
			listaATablaVideos();
			JTable jTablaVideos = new JTable(new TablaVideoModel());
			JScrollPane pScroll = new JScrollPane(jTablaVideos);
			add(pScroll);
		}
		
		pVideos.setOpaque(isOpaque);
		pVideos.setLayout(new GridLayout(0, 1));  // TODO deberia ocupar el resto de la pantalla y no lo hace
		pVideos.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY, Color.GRAY));
		

		add(pVideos, BorderLayout.CENTER);
	}

	private void listaATablaVideos() {
		int numVideos = listaVideos.size();
		int mod = numVideos % NUM_COLUMNAS;
		double div = numVideos / NUM_COLUMNAS;
		int numFilas = (int) ((mod == 0) ? div : div + 1);

		for (int i = 0; i < numFilas; i++)
			for (int j = 0; j < NUM_COLUMNAS; j++)
				tablaVideos[i][j] = listaVideos.get((j * 10) + i);
	}

	private void addListenerBotonNuevaBusqueda(JButton bNuevaBusq) {
		bNuevaBusq.addActionListener(e -> {
			tfBuscar.setText("");
			listaDisponibles.add(listaSeleccionadas);
			listaSeleccionadas.removeAll();
		});
	}

	private void addListenerBotonBuscar(JButton bBuscar) {
		bBuscar.addActionListener(e -> {
			listaVideos = CatalogoVideos.getUnicaInstancia().consultarVideosPorPalabra(tfBuscar.getText());
			buscado = true;
			confVideos();
		});
	}
	
	class TablaVideoModel extends AbstractTableModel{

		@Override
		public int getRowCount() {
			return tablaVideos.length;
		}

		@Override
		public int getColumnCount() {
			return NUM_COLUMNAS;
		}

		@Override
		public Video getValueAt(int rowIndex, int columnIndex) {
			return tablaVideos[rowIndex][columnIndex];
		}
  }
}
