package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;

@SuppressWarnings("serial")
public class TabExplorar extends JPanel {
	
	private boolean isOpaque = false;
	
	public TabExplorar(Ventana window) {
		
		setLayout(new BorderLayout());
		setBackground(new Color(96, 96, 96));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		// CONF EXPLORAR
		
		// initModels
		DefaultListModel<String> modeloEtiqDisp = new DefaultListModel<>();
		modeloEtiqDisp.addAll(Controlador.getInstancia().getEtiquetas());
		DefaultListModel<String> modeloEtiqSelec = new DefaultListModel<>();
		
		// addPanelEtiquetas
		JPanel pEtiquetas = new JPanel();
		pEtiquetas.setLayout(new BoxLayout(pEtiquetas, BoxLayout.Y_AXIS));
		pEtiquetas.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY, Color.GRAY));
		pEtiquetas.setOpaque(isOpaque);
		add(pEtiquetas, BorderLayout.EAST);
		
		// addPanelBuscador
		JPanel pBuscador = new Buscador(window, modeloEtiqDisp, modeloEtiqSelec);
		add(pBuscador, BorderLayout.CENTER);
		
		// CONF ETIQUETAS
		
		// addLabelDisponibles
		addLabel(pEtiquetas, "Etiquetas disponibles");
		// addListaDisponibles
		JList<String> listaEtiqDisp = addList(pEtiquetas,
				modeloEtiqDisp, modeloEtiqSelec);
		listaEtiqDisp.setVisibleRowCount(1);

		// addLabelSeleccionadas
		addLabel(pEtiquetas, "Buscar etiquetas");
		// addListSeleccionadas
		JList<String> listaEtiqSelec = addList(pEtiquetas,
				modeloEtiqSelec, modeloEtiqDisp);
	}
	
	private JLabel addLabel(JPanel parent, String texto) {
		JLabel label = new JLabel(texto, SwingConstants.CENTER);
		label.setForeground(Color.WHITE);
		label.setBorder(new EmptyBorder(5, 5, 5, 5));
		parent.add(label);
		return label;
	}

	private JList<String> addList(JPanel parent,
			DefaultListModel<String> model, DefaultListModel<String> other) {
		JPanel panel = new JPanel();
		panel.setOpaque(isOpaque);
	
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
		lista.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lista);
		parent.add(panel);
		return lista;
	}
}
