package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import controlador.Controlador;

@SuppressWarnings("serial")
public class TabFiltros extends JPanel {
	
	private Controlador controlador = Controlador.getInstancia();
	
	private String filtroSeleccionado;

	private JLabel lFiltroUsuario;
	private JTextArea taDescripcion;
	
	public TabFiltros(Ventana window) {
		filtroSeleccionado = controlador.getFiltroUsuario();
		
		setLayout(new BorderLayout());
		
		// addPanelLateral
		JPanel pLateral = new JPanel();
		pLateral.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		pLateral.setLayout(new BorderLayout());
		add(pLateral, BorderLayout.WEST);
		
		// addPanelCentral
		JPanel pCentral = new JPanel();
		pCentral.setLayout(new BorderLayout());
		add(pCentral, BorderLayout.CENTER);
		
		// CONF PanelLateral
		
		// addFiltroUsuario
		JPanel pFiltroUsuario = new JPanel();
		FlowLayout layout = (FlowLayout) pFiltroUsuario.getLayout();
		layout.setAlignment(FlowLayout.LEFT);
		lFiltroUsuario = new JLabel("Filtro actual: " + filtroSeleccionado);
		pFiltroUsuario.add(lFiltroUsuario);
		pLateral.add(pFiltroUsuario, BorderLayout.NORTH);
		
		// addPanelFiltros
		JPanel pFiltros = new JPanel();
		pFiltros.setLayout(new BoxLayout(pFiltros, BoxLayout.Y_AXIS));
		JScrollPane spFiltros = new JScrollPane(pFiltros);
		spFiltros.setPreferredSize(new Dimension(200, 10));
		spFiltros.setMinimumSize(new Dimension(200, 10));
		spFiltros.setBorder(new TitledBorder("Filtros"));
		pLateral.add(spFiltros, BorderLayout.CENTER);
		
		// addAplicar
		JPanel pAplicar = new JPanel();
		JButton bAplicar = new JButton("Aplicar");
		bAplicar.addActionListener(e -> {
			if (filtroSeleccionado != null) {
				controlador.setFiltroUsuario(filtroSeleccionado);
				lFiltroUsuario.setText("Filtro actual: " + filtroSeleccionado);
			}
		});
		pAplicar.add(bAplicar);
		pLateral.add(pAplicar, BorderLayout.SOUTH);
		
		// CONF PanelCentral
		
		// addLabelDescripcion
		JPanel plDescripcion = new JPanel();
		FlowLayout layout1 = (FlowLayout) plDescripcion.getLayout();
		layout1.setAlignment(FlowLayout.LEFT);
		JLabel lDescripcion = new JLabel("Descripcion:");
		plDescripcion.add(lDescripcion);
		pCentral.add(plDescripcion, BorderLayout.NORTH);
		
		// addAreaDescripcion
//		JPanel paDescripcion = new JPanel();
//		paDescripcion.setLayout(new BorderLayout());
		taDescripcion = new JTextArea(controlador.getDescripcionFiltro(filtroSeleccionado));
		JScrollPane spaDescripcion = new JScrollPane(taDescripcion);
//		paDescripcion.add(spaDescripcion, BorderLayout.CENTER);
//		paDescripcion.add(Box.createHorizontalStrut(25), BorderLayout.EAST);
//		paDescripcion.add(Box.createVerticalStrut(30), BorderLayout.SOUTH);
//		pCentral.add(paDescripcion, BorderLayout.CENTER);
		spaDescripcion.setBorder(new EmptyBorder(25, 30, 25, 30));
		pCentral.add(spaDescripcion, BorderLayout.CENTER);
		
		// CONF PanelFiltros
		
		List<String> filtros = controlador.getNombresFiltros();
		ButtonGroup grupo = new ButtonGroup();
		for (String filtro : filtros) {
			JRadioButton casilla = null;
			if (filtro.equals(filtroSeleccionado))
				casilla = new JRadioButton(filtro, true);
			else
				casilla = new JRadioButton(filtro, false);
			casilla.setHorizontalAlignment(SwingConstants.LEFT);
			casilla.addItemListener(e -> {
				JRadioButton resultado = (JRadioButton) e.getSource();
				if (resultado.isSelected()) {
					filtroSeleccionado = resultado.getText();
					taDescripcion.setText(controlador.getDescripcionFiltro(filtroSeleccionado));
				}
				
			});
			grupo.add(casilla);
			pFiltros.add(casilla);
		}
		
	}
	
}
