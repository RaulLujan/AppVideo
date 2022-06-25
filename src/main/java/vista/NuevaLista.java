package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class NuevaLista extends JPanel {
	
	private boolean isOpaque = false;
	private GridBagConstraints c;
	
	public NuevaLista() {
		c = new GridBagConstraints();
		
		// CONF LAMINA
		setLayout(new GridBagLayout());
		setBackground(new Color(96, 96, 96));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		confIntroducirNombre();
		confBusqueda();
		confBotonesVideos();
	}

	private void confIntroducirNombre() {
		JPanel pGeneral = new JPanel();
		JPanel pGrupoIntroducir = new JPanel();
		
		JLabel lIntroducir = new JLabel("Introducir nombre lista:");
		JTextField tfNombreLista = new JTextField(10);
		JButton bBuscar = new JButton("Buscar");
		JButton bEliminar = new JButton("Eliminar");
		
		pGeneral.setLayout(new BoxLayout(pGeneral, BoxLayout.Y_AXIS));
		pGeneral.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY, Color.GRAY));
		pGeneral.setOpaque(isOpaque);
		
		pGrupoIntroducir.setLayout(new FlowLayout());
		pGrupoIntroducir.setOpaque(isOpaque);
		
		lIntroducir.setForeground(Color.WHITE);
		
		pGrupoIntroducir.add(tfNombreLista);
		pGrupoIntroducir.add(bBuscar);
		
		pGeneral.add(lIntroducir);
		pGeneral.add(pGrupoIntroducir);
		pGeneral.add(bEliminar);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0; c.gridy = 0;
		c.weightx = 1; c.weighty = 1;
		
		add(pGeneral, c);
		
	}
	
	private void confBusqueda() {
		JPanel pBusqueda = new JPanel();
		JPanel pInsertarBusq = new JPanel();

		JLabel lBuscar = new JLabel("Buscar t�tulo: ", SwingConstants.RIGHT);
		JTextField tfBuscar = new JTextField(20);
		JButton bBuscar = new JButton("Buscar");
		JButton bNuevaBusq = new JButton("Nueva b�squeda");

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
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 0;
		c.weightx = 2; c.weighty = 1;

		add(pBusqueda, c);
	}
	
	private void confBotonesVideos() {
		JPanel pBotones = new JPanel();
		JPanel pBotonesVideoLista = new JPanel();
		JPanel pAceptar = new JPanel();
		
		JButton bAnyadir = new JButton("A�adir");
		JButton bQuitar = new JButton("Quitar");
		JButton bAceptar = new JButton("Aceptar");
		
		pBotones.setLayout(new BoxLayout(pBotones, BoxLayout.Y_AXIS));
		pBotones.setOpaque(isOpaque);
		pBotones.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY, Color.GRAY));
		
		pBotonesVideoLista.setLayout(new BoxLayout(pBotonesVideoLista, BoxLayout.X_AXIS));
		pBotonesVideoLista.setOpaque(isOpaque);
		pBotonesVideoLista.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
		
		pAceptar.setLayout(new BoxLayout(pAceptar, BoxLayout.X_AXIS));
		pAceptar.setOpaque(isOpaque);
		
		pBotonesVideoLista.add(Box.createHorizontalGlue());
		pBotonesVideoLista.add(bAnyadir);
		pBotonesVideoLista.add(Box.createHorizontalGlue());
		pBotonesVideoLista.add(bQuitar);
		pBotonesVideoLista.add(Box.createHorizontalGlue());
		
		pAceptar.add(Box.createHorizontalGlue());
		pAceptar.add(bAceptar);
		pAceptar.add(Box.createHorizontalGlue());
		
		pBotones.add(pBotonesVideoLista);
		pBotones.add(pAceptar);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_END;
		c.gridx = 0; c.gridy = 4;
		c.weightx = 1; c.weighty = 1;
		
		add(pBotones, c);
	}
	
	private void confListaActual() {
		
	}
	
	private void confVideos() {
		
	}
	
	
}
