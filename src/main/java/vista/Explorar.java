package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.print.PrinterException;
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
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import controlador.Controlador;
import modelo.CatalogoVideos;
import modelo.Video;

public class Explorar extends JPanel {
	private static final int NUM_COLUMNAS = 4;

	private JTextField tfBuscar;
	private JList<String> listaDisponibles;
	private JList<String> listaSeleccionadas;
	//Revisar
	private List<Video> listaVideos;
	private Video[][] tablaDatosVideos;

	private boolean buscado = false;
	private boolean isOpaque = false;

	public Explorar() {
		confLamina();
	}

	private void confLamina() {

		setLayout(new GridBagLayout());
		setBackground(new Color(96, 96, 96));

		confBusqueda();
		confEtiquetas();
		confVideos();
	}

	private void confBusqueda() {
		JPanel pBusqueda = new JPanel();
		pBusqueda.setLayout(new GridBagLayout());
		pBusqueda.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY, Color.GRAY));
		pBusqueda.setOpaque(isOpaque);

		JLabel lBuscar = new JLabel("Buscar t�tulo:", SwingConstants.RIGHT);
		tfBuscar = new JTextField(20);
		JButton bBuscar = new JButton("Buscar");
		JButton bNuevaBusq = new JButton("Nueva b�squeda");

		lBuscar.setForeground(Color.WHITE);

		GridBagConstraints constraintsLBuscar = new GridBagConstraints();
		constraintsLBuscar.insets = new Insets(20, 20, 10, 5);
		constraintsLBuscar.anchor = GridBagConstraints.EAST;
		constraintsLBuscar.gridx = 0;
		constraintsLBuscar.gridy = 0;

		GridBagConstraints constraintsTBuscar = new GridBagConstraints();
		constraintsTBuscar.insets = new Insets(20, 5, 10, 5);
		constraintsTBuscar.anchor = GridBagConstraints.WEST;
		constraintsTBuscar.gridx = 1;
		constraintsTBuscar.gridy = 0;

		GridBagConstraints constraintsBBuscar = new GridBagConstraints();
		constraintsBBuscar.insets = new Insets(20, 5, 10, 20);
		constraintsBBuscar.anchor = GridBagConstraints.CENTER;
		constraintsBBuscar.gridx = 2;
		constraintsBBuscar.gridy = 0;

		GridBagConstraints constraintsBNuevaBusq = new GridBagConstraints();
		constraintsBNuevaBusq.insets = new Insets(5, 5, 20, 5);
		constraintsBNuevaBusq.fill = GridBagConstraints.CENTER;
		constraintsBNuevaBusq.gridx = 0;
		constraintsBNuevaBusq.gridy = 1;
		constraintsBNuevaBusq.gridwidth = 3;

		pBusqueda.add(lBuscar, constraintsLBuscar);
		pBusqueda.add(tfBuscar, constraintsTBuscar);
		pBusqueda.add(bBuscar, constraintsBBuscar);
		pBusqueda.add(bNuevaBusq, constraintsBNuevaBusq);

		GridBagConstraints constraintsPBusqueda = new GridBagConstraints();
		constraintsPBusqueda.insets = new Insets(10, 10, 2, 2);
		constraintsPBusqueda.fill = GridBagConstraints.HORIZONTAL;
		constraintsPBusqueda.gridx = 0;
		constraintsPBusqueda.gridy = 0;

		add(pBusqueda, constraintsPBusqueda);

		addListenerBotonNuevaBusqueda(bNuevaBusq);
		addListenerBotonBuscar(bBuscar);
	}

	private void confEtiquetas() {
		JPanel pEtiquetas = new JPanel();

		JLabel lEtiqDisponibles = new JLabel("Etiquetas disponibles");
		listaDisponibles = new JList<>();
		JLabel lEtiqSeleccionadas = new JLabel("Etiquetas seleccionadas b�squeda");
		listaSeleccionadas = new JList<>();

		pEtiquetas.setLayout(new GridBagLayout());
		pEtiquetas.setOpaque(isOpaque);
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

		listaSeleccionadas.setModel(new AbstractListModel<String>() {
			String[] values = new String[] { "Etiqueta1", "Etiqueta2", "Etiqueta3", "Etiqueta4" };

			public int getSize() {
				return values.length;
			}

			@Override
			public String getElementAt(int index) {
				return values[index];
			}
		});

		GridBagConstraints constraintsLEtiDispo = new GridBagConstraints();
		constraintsLEtiDispo.insets = new Insets(20, 20, 10, 20);
		constraintsLEtiDispo.fill = GridBagConstraints.CENTER;
		constraintsLEtiDispo.gridx = 0;
		constraintsLEtiDispo.gridy = 0;

		GridBagConstraints constraintsListaDisp = new GridBagConstraints();
		constraintsListaDisp.insets = new Insets(20, 20, 10, 20);
		constraintsListaDisp.fill = GridBagConstraints.CENTER;
		constraintsListaDisp.gridx = 0;
		constraintsListaDisp.gridy = 1;

		GridBagConstraints constraintsLEtiquetaSelect = new GridBagConstraints();
		constraintsLEtiquetaSelect.insets = new Insets(20, 20, 10, 20);
		constraintsLEtiquetaSelect.fill = GridBagConstraints.CENTER;
		constraintsLEtiquetaSelect.gridx = 0;
		constraintsLEtiquetaSelect.gridy = 2;

		GridBagConstraints constraintsListaSelecionadas = new GridBagConstraints();
		constraintsListaSelecionadas.insets = new Insets(20, 20, 20, 20);
		constraintsListaSelecionadas.fill = GridBagConstraints.CENTER;
		constraintsListaSelecionadas.gridx = 0;
		constraintsListaSelecionadas.gridy = 3;

		pEtiquetas.add(lEtiqDisponibles, constraintsLEtiDispo);
		pEtiquetas.add(listaDisponibles, constraintsListaDisp);
		pEtiquetas.add(lEtiqSeleccionadas, constraintsLEtiquetaSelect);
		pEtiquetas.add(listaSeleccionadas, constraintsListaSelecionadas);

		GridBagConstraints constraintsPEtiquetas = new GridBagConstraints();
		constraintsPEtiquetas.insets = new Insets(10, 2, 10, 10);
		constraintsPEtiquetas.anchor = GridBagConstraints.EAST;
		constraintsPEtiquetas.gridx = 1;
		constraintsPEtiquetas.gridy = 0;
		constraintsPEtiquetas.gridheight = 3;
		constraintsPEtiquetas.gridwidth = 1;
		add(pEtiquetas, constraintsPEtiquetas);
	}

	private void confVideos() {
//		LaminaVideos pVideos = new LaminaVideos();
//
//		if (buscado) {
//			listaATablaVideos();
//			JTable jTablaVideos = new JTable(new TablaVideoModel());
//			JScrollPane pScroll = new JScrollPane(jTablaVideos);
//			pVideos.add(pScroll);
//		}
//
//		JButton bPrueba = new JButton("PRUEBA");
//		bPrueba.setPreferredSize(new Dimension(200, 200));
//
//		GridBagConstraints constraintsbPrueba = new GridBagConstraints();
//		constraintsbPrueba.insets = new Insets(0, 0, 0, 0);
//		constraintsbPrueba.fill = GridBagConstraints.CENTER;
//		constraintsbPrueba.gridx = 0;
//		constraintsbPrueba.gridy = 0;
//		//pVideos.add(bPrueba, constraintsbPrueba);
//
//		GridBagConstraints constraintsPVideos = new GridBagConstraints();
//		constraintsPVideos.insets = new Insets(2, 10, 10, 2);
//		constraintsPVideos.fill = GridBagConstraints.BOTH;
//		constraintsPVideos.gridx = 0;
//		constraintsPVideos.gridy = 1;
//
//		add(pVideos, constraintsPVideos);
//
//		addListenerBotonPrueba(bPrueba);

		JTable tablaVideos;
		String[] nombresColumnas = {};
		
		System.out.println("HOla " + listaVideos.get(0).getTitulo());
		listaATablaVideos();
		tablaVideos = new JTable(tablaDatosVideos, nombresColumnas);
		
		GridBagConstraints constraintsPVideos = new GridBagConstraints();
		constraintsPVideos.insets = new Insets(2, 10, 10, 2);
		constraintsPVideos.fill = GridBagConstraints.BOTH;
		constraintsPVideos.gridx = 0;
		constraintsPVideos.gridy = 1;

		add(new JScrollPane(tablaVideos), constraintsPVideos);
		try {
			tablaVideos.print();
		} catch (PrinterException e) {
			e.printStackTrace();
		}
		
	}

	private void listaATablaVideos() {
		int numVideos = listaVideos.size();
		int mod = numVideos % NUM_COLUMNAS;
		double div = numVideos / NUM_COLUMNAS;
		int numFilas = (int) ((mod == 0) ? div : div + 1);

		for (int i = 0; i < numFilas; i++)
			for (int j = 0; j < NUM_COLUMNAS; j++)
				tablaDatosVideos[i][j] = listaVideos.get((j * 10) + i);
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
			listaVideos = (List<Video>) CatalogoVideos.getInstancia().getVideosOK(null,tfBuscar.getText(),null);
			buscado = true;
			confVideos();
		});
	}

	private void addListenerBotonPrueba(JButton bPrueba) {
		bPrueba.addActionListener(e -> {
			removeAll();
			LaminaSuperior.getInstancia().mostrar();
			LaminaSuperior.getInstancia().mostrarLamina("ReproducionVideo");

			revalidate();
			repaint();
		});
	}

//	class TablaVideoModel extends AbstractTableModel {
//
//		@Override
//		public int getRowCount() {
//			return tablaDatosVideos.length;
//		}
//
//		@Override
//		public int getColumnCount() {
//			return NUM_COLUMNAS;
//		}
//
//		@Override
//		public Video getValueAt(int rowIndex, int columnIndex) {
//			return tablaDatosVideos[rowIndex][columnIndex];
//		}
//
//	}
}
