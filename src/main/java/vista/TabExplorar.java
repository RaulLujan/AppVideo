package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import modelo.Video;

@SuppressWarnings("serial")
public class TabExplorar extends JPanel {
	
	private boolean isOpaque = false;
	
	private JTextField tfBuscar;
	
	private DefaultListModel<String> modeloDisponibles;
	private DefaultListModel<String> modeloSeleccionadas;

	private DefaultListModel<CaratulaVideo> modeloVideos;

	public TabExplorar(Ventana parent) {
		
		
		// CONF LAMINA
		setLayout(new BorderLayout());
		setBackground(new Color(96, 96, 96));
		
		// addPanelEtiquetas
		JPanel pEtiquetas = addPanel(BorderLayout.WEST);
		// addPanelBusqueda
		JPanel pBusqueda = addPanel(BorderLayout.NORTH);
		// addPanelVideos
		JScrollPane pVideos = new JScrollPane();
		pVideos.setPreferredSize(new Dimension(500, 240));
		add(pVideos, BorderLayout.CENTER);
		
		// CONF ETIQUETAS
		// initModels
		modeloDisponibles = new DefaultListModel<>();
		modeloDisponibles.addAll(Controlador.getInstancia().getEtiquetas());
		modeloSeleccionadas = new DefaultListModel<>();
		// addLabelDisponibles
		addLabel(pEtiquetas, "Etiquetas disponibles");
		// addListaDisponibles
		JList<String> listaDisponibles = addList(pEtiquetas, modeloDisponibles, modeloSeleccionadas);
		listaDisponibles.setVisibleRowCount(1);
		// addLabelSeleccionadas
		addLabel(pEtiquetas, "Buscar etiquetas");
		// addListSeleccionadas
		addList(pEtiquetas, modeloSeleccionadas, modeloDisponibles);
		
		// CONF BUSQUEDA
		// addSubPanelBuscar
		JPanel pBuscar = addSubPanel(pBusqueda);
		// addSubPanelNuevaBusqueda
		JPanel pNuevaBusqueda = addSubPanel(pBusqueda);
		// addLabelBuscar
		addLabel(pBuscar, "Buscar titulo:");
		// addFieldBuscar
		tfBuscar = new JTextField(20);
		pBuscar.add(tfBuscar);
		// addButtonBuscar
		addButton(pBuscar, "Buscar");
		// addButtonNuevaBusqueda
		addButton(pNuevaBusqueda, "Nueva busqueda");
		
		// CONF VIDEOS
		// initModel
		modeloVideos = new DefaultListModel<>();
		// addListSeleccionadas
		addList(pVideos, modeloVideos);
		
		
//		setVideos(new ArrayList<Video>());
		setVideos(Controlador.getInstancia().getVideosExplorar());
	}
	
	private JPanel addPanel(String region) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY, Color.GRAY));
		panel.setOpaque(isOpaque);
		add(panel, region);
		return panel;
	}
	
	private JPanel addSubPanel(JPanel parent) {
		JPanel panel = new JPanel();
		// por defecto, FlowLayout
		panel.setOpaque(isOpaque);
		parent.add(panel);
		return panel;
		
	}
	
	private JLabel addLabel(JPanel parent, String texto) {
		JLabel label = new JLabel(texto);
		label.setForeground(Color.WHITE);
		label.setBorder(new EmptyBorder(5, 5, 5, 5));
		parent.add(label);
		return label;
	}

	private JList<String> addList(JPanel parent, DefaultListModel<String> model, DefaultListModel<String> other) {
		JList<String> lista = new JList<>();
		lista.setModel(model);
		lista.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					@SuppressWarnings("unchecked")
					JList<String> resultados = (JList<String>) e.getComponent();
					String etiqueta = resultados.getSelectedValue();
					model.removeElement(etiqueta);
					other.addElement(etiqueta);
				}
			}
		});
		parent.add(lista);
		return lista;
	}
	
	private JButton addButton(JPanel parent, String texto) {
		JButton boton = new JButton(texto);
		boton.addActionListener(e -> {
			String text = boton.getText();
			switch (text) {
			case "Buscar":
				List<String> etiquetas = new ArrayList<>(modeloSeleccionadas.getSize());
				for (int i = 0; i < modeloSeleccionadas.getSize(); i++)
					etiquetas.add(i, modeloSeleccionadas.getElementAt(i));
//				System.out.println(etiquetas);
				setVideos(Controlador.getInstancia().getVideosBuscar(tfBuscar.getText(),etiquetas));
				break;
				
			case "Nueva busqueda":
				tfBuscar.setText("");
				modeloSeleccionadas.removeAllElements();
				modeloDisponibles.removeAllElements();
				modeloDisponibles.addAll(Controlador.getInstancia().getEtiquetas());
				setVideos(Controlador.getInstancia().getVideosExplorar());
//				setVideos(new ArrayList<Video>());
				break;
			}
		});
		parent.add(boton);
		return boton;
	}

	private JList<CaratulaVideo> addList(JScrollPane parent, DefaultListModel<CaratulaVideo> model) {
		JList<CaratulaVideo> lista = new JList<>();
		lista.setModel(model);
		lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lista.setBackground(new Color(96, 96, 96));
		lista.setCellRenderer(CaratulaVideo.getProcesador());
		lista.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					@SuppressWarnings("unchecked")
					JList<CaratulaVideo> resultados = (JList<CaratulaVideo>) e.getComponent();
					CaratulaVideo caratula = resultados.getSelectedValue();
					// TODO abrir reproductor
				}
			}
		});
		parent.setViewportView(lista);
		return lista;
	}
 	
	private void setVideos(List<Video> lv) {
		modeloVideos.clear();
		for (Video v : lv)
//			System.out.println(v.getTitulo());
			modeloVideos.addElement(new CaratulaVideo(v));
	}
}
