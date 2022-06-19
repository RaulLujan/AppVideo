package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;


import javax.swing.*;

import controlador.Controlador;
import tds.video.VideoWeb;

public class ReproducionVideo extends JPanel {


	public ReproducionVideo() {
		confLamina();
	}

	private void confLamina() {
		setLayout(new GridBagLayout());
		setBackground(new Color(96, 96, 96));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JLabel titulo = new JLabel("Sin título");
		titulo.setFont(new Font("Arial", Font.BOLD, 24));

		JButton btCancelar = new JButton("Cancelar");
		JLabel reprod = new JLabel("reproduciendo:");
		
		GridBagConstraints constraintsLTitulo = new GridBagConstraints();
		constraintsLTitulo.insets = new Insets(20, 10, 20, 5);
		constraintsLTitulo.anchor = GridBagConstraints.EAST;
		constraintsLTitulo.gridx = 0;
		constraintsLTitulo.gridy = 0;
		
		GridBagConstraints constraintsVWRepr = new GridBagConstraints();
		constraintsVWRepr.insets = new Insets(10, 20, 20, 5);
		constraintsVWRepr.anchor = GridBagConstraints.EAST;
		constraintsVWRepr.gridx = 0;
		constraintsVWRepr.gridy = 1;
		
		
		GridBagConstraints constraintsBCancelar = new GridBagConstraints();
		constraintsBCancelar.insets = new Insets(20, 5, 20, 15);
		constraintsBCancelar.anchor = GridBagConstraints.EAST;
		constraintsBCancelar.gridx = 0;
		constraintsBCancelar.gridy = 2;
		
		GridBagConstraints constraintsLReproducion = new GridBagConstraints();
		constraintsLReproducion.insets = new Insets(10, 20, 20, 5);
		constraintsLReproducion.anchor = GridBagConstraints.EAST;
		constraintsLReproducion.gridx = 0;
		constraintsLReproducion.gridy = 3;
		

		
		add(titulo, constraintsLTitulo);
		add(Controlador.getInstaciaUnica().getVideoWeb(),constraintsVWRepr);
		addReproductor();
		add(btCancelar, constraintsBCancelar);
		add(reprod, constraintsLReproducion);

		addCopyRigth();

	}

	private void addReproductor() {
		JPanel panelReproductor = new JPanel();
		panelReproductor.setLayout(new GridLayout(2, 2));

		VideoWeb videoWeb = Controlador.getInstaciaUnica().getVideoWeb();
		panelReproductor.add(videoWeb);
		Controlador.getInstaciaUnica().ponerVideo();
	}

	private void addCopyRigth() {
		VideoWeb videoWeb = Controlador.getInstaciaUnica().getVideoWeb();
		JLabel copyright = new JLabel(videoWeb.getVersion());
		copyright.setFont(new Font("Arial", Font.BOLD, 10));
		
		
		GridBagConstraints constraintsLCopy = new GridBagConstraints();
		constraintsLCopy.insets = new Insets(10, 20, 20, 5);
		constraintsLCopy.anchor = GridBagConstraints.EAST;
		constraintsLCopy.gridx = 0;
		constraintsLCopy.gridy = 4;
		
		add(copyright, constraintsLCopy);
	}

}
