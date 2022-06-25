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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import controlador.Controlador;
import modelo.Etiqueta;
import modelo.Video;

public class Explorar extends JPanel {
	
	private static final int NUM_COLUMNAS = 4;

	private JTextField tfBuscar;
	private DefaultListModel<String> modeloDisponibles;
	private DefaultListModel<String> modeloSeleccionadas;
	private JList<String> listaDisponibles;
	private JList<String> listaSeleccionadas;
	//Revisar
	private List<Video> listaVideos;

	private boolean buscado = false;
	private boolean isOpaque = false;

	public Explorar() {
		
		// CONF LAMINA
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

		JLabel lBuscar = new JLabel("Buscar titulo:", SwingConstants.RIGHT);
		tfBuscar = new JTextField(20);
		JButton bBuscar = new JButton("Buscar");
		JButton bNuevaBusq = new JButton("Nueva busqueda");

		lBuscar.setForeground(Color.WHITE);

		GridBagConstraints constraintsLBuscar = new GridBagConstraints();
		constraintsLBuscar.insets = new Insets(20, 20, 10, 5);
		constraintsLBuscar.anchor = GridBagConstraints.WEST;
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
		modeloDisponibles = new DefaultListModel<>();
		modeloDisponibles.addAll(Controlador.getInstancia().getEtiquetas());
		listaDisponibles.setModel(modeloDisponibles);

		modeloSeleccionadas = new DefaultListModel<>();
		listaSeleccionadas.setModel(modeloSeleccionadas);

		addListenerListaDisponibles();
		addListenerListaSeleccionadas();
		
		
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
		constraintsPEtiquetas.fill = GridBagConstraints.VERTICAL;
		constraintsPEtiquetas.gridx = 1;
		constraintsPEtiquetas.gridy = 0;
		constraintsPEtiquetas.gridheight = 2;
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

		
		JScrollPane pVideos = new JScrollPane();
		pVideos.setPreferredSize(new Dimension(500, 240));
		
		if (!buscado)
			listaVideos = Controlador.getInstancia().getVideosExplorar();
		for (Video v : listaVideos)
			System.out.println(v.getTitulo());
		DefaultListModel<CaratulaVideo> modelVideos =new DefaultListModel<>();
		modelVideos.clear();
		for (Video video : listaVideos)
			modelVideos.addElement(new CaratulaVideo(video));
	
		JList<CaratulaVideo> tablaVideos = new JList<>(modelVideos);
		tablaVideos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaVideos.setBackground(new Color(96, 96, 96));
		tablaVideos.setCellRenderer(CaratulaVideo.getProcesador());
		pVideos.setViewportView(tablaVideos);
		
		//listaATablaVideos(tablaVideos); 
		//tablaVideos.setBackground(new Color(96, 96, 96));
		//for (Video video : listaVideos)
		//	tablaVideos.add(new CaratulaVideo(video));
		
		GridBagConstraints constraintsPVideos = new GridBagConstraints();
		constraintsPVideos.insets = new Insets(2, 10, 10, 2);
		constraintsPVideos.fill = GridBagConstraints.BOTH;
		constraintsPVideos.gridx = 0;
		constraintsPVideos.gridy = 1;

		add(pVideos, constraintsPVideos);
		//add(new JScrollPane(tablaVideos), constraintsPVideos);
//		try {
//
//			tablaVideos.print();
//		} catch (PrinterException e) {
//			e.printStackTrace();
//		}
		
		
		
		
	}

	private JPanel[][] listaATablaVideos() {
		int numVideos = listaVideos.size();
		int mod = numVideos % NUM_COLUMNAS;
		double div = numVideos / NUM_COLUMNAS;
		int numFilas = (int) ((mod == 0) ? div : div + 1);
		int numCol = (mod == 0) ? NUM_COLUMNAS : mod;

		JPanel[][] tablaVideos = new JPanel[numFilas][NUM_COLUMNAS];
		for (int i = 0; i < numFilas-1; i++)
			for (int j = 0; j < NUM_COLUMNAS; j++)
				tablaVideos[i][j] = new CaratulaVideo(listaVideos.get(i * NUM_COLUMNAS + j));
		int i = numFilas-1;
		for (int j = 0; j < numCol; j++)
			tablaVideos[i][j] = new CaratulaVideo(listaVideos.get(i * NUM_COLUMNAS + j));
		return tablaVideos;
	}

	private void addListenerListaDisponibles() {
		listaDisponibles.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JList<String> resultados = (JList<String>) e.getComponent();
					String etiqueta = resultados.getSelectedValue();
					modeloDisponibles.removeElement(etiqueta);
					modeloSeleccionadas.addElement(etiqueta);
				}
			}
		});
		
	}

	private void addListenerListaSeleccionadas() {
		listaSeleccionadas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JList<String> resultados = (JList<String>) e.getComponent();
					String etiqueta = resultados.getSelectedValue();
					modeloSeleccionadas.removeElement(etiqueta);
					modeloDisponibles.addElement(etiqueta);
				}
			}
		});
	}
	
	private void addListenerBotonNuevaBusqueda(JButton bNuevaBusq) {
		bNuevaBusq.addActionListener(e -> {
			tfBuscar.setText("");
			modeloSeleccionadas.removeAllElements();
			modeloDisponibles.removeAllElements();
			modeloDisponibles.addAll(Controlador.getInstancia().getEtiquetas());
		});
	}

	private void addListenerBotonBuscar(JButton bBuscar) {
		bBuscar.addActionListener(e -> {
			List<String> etiquetas = new ArrayList<>(modeloSeleccionadas.getSize());
			for (int i = 0; i < modeloSeleccionadas.getSize(); i++)
				etiquetas.add(i, modeloSeleccionadas.getElementAt(i));
			System.out.println(etiquetas);
			listaVideos = Controlador.getInstancia().getVideosBuscar(tfBuscar.getText(),etiquetas);
			buscado = true;
			confVideos();
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
