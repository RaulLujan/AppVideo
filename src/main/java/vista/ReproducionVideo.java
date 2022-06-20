package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.*;

import controlador.Controlador;
import tds.video.VideoWeb;

public class ReproducionVideo extends JPanel {

	private JPanel panelTitulo;
	private JPanel panelReproductor;
	private JPanel panelBotonoes;
	
	public ReproducionVideo() {
		confLamina();
	}

	private void confLamina() {
		setLayout(new GridBagLayout());
		setBackground(new Color(96, 96, 96));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		panelTitulo = new JPanel();
		panelTitulo.setLayout(new GridBagLayout());
		panelTitulo.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY, Color.GRAY));
		panelTitulo.setBackground(new Color(180, 180, 180));
		
		panelReproductor = new JPanel();
		panelReproductor.setLayout(new GridBagLayout());
		panelReproductor.setBackground(new Color(96, 96, 96));
		
		panelBotonoes = new JPanel();
		panelBotonoes.setLayout(new GridBagLayout());
		panelBotonoes.setBackground(new Color(96, 96, 96));
		
		GridBagConstraints constraintspPTitulo = new GridBagConstraints();
		constraintspPTitulo.insets = new Insets(20, 20, 20, 20);
		constraintspPTitulo.anchor = GridBagConstraints.CENTER;
		constraintspPTitulo.gridx = 0;
		constraintspPTitulo.gridy = 0;
		
		GridBagConstraints constraintspPReproductor = new GridBagConstraints();
		constraintspPReproductor.insets = new Insets(20, 20, 20, 20);
		constraintspPReproductor.anchor = GridBagConstraints.CENTER;
		constraintspPReproductor.gridx = 0;
		constraintspPReproductor.gridy = 1;
		
		GridBagConstraints constraintspPBotones = new GridBagConstraints();
		constraintspPBotones.insets = new Insets(20, 20, 20, 20);
		constraintspPBotones.anchor = GridBagConstraints.CENTER;
		constraintspPBotones.gridx = 0;
		constraintspPBotones.gridy = 2;

		add(panelTitulo, constraintspPTitulo);
		add(panelReproductor, constraintspPReproductor);
		add(panelBotonoes, constraintspPBotones);
		addComponentes();


	}

	private void addComponentes() {
		JLabel titulo = new JLabel("Sin título");
		titulo.setFont(new Font("Arial", Font.BOLD, 24));

		JButton btCancelar = new JButton("Cancelar");
		JLabel lReprod = new JLabel("Reproduciendo:");
		lReprod.setForeground(Color.WHITE);

		JPanel panelReprodurtor = new JPanel();
		panelReprodurtor.add(Controlador.getInstaciaUnica().getVideoWeb());

		GridBagConstraints constraintsLTitulo = new GridBagConstraints();
		constraintsLTitulo.insets = new Insets(10, 10, 10, 10);
		constraintsLTitulo.anchor = GridBagConstraints.CENTER;
		constraintsLTitulo.gridx = 0;
		constraintsLTitulo.gridy = 0;

		GridBagConstraints constraintsVWRepr = new GridBagConstraints();
		constraintsVWRepr.insets = new Insets(5, 5, 5, 5);
		constraintsVWRepr.anchor = GridBagConstraints.CENTER;
		constraintsVWRepr.gridx = 0;
		constraintsVWRepr.gridy = 1;

		GridBagConstraints constraintsLReproducion = new GridBagConstraints();
		constraintsLReproducion.insets = new Insets(5, 5, 2, 5);
		constraintsLReproducion.anchor = GridBagConstraints.CENTER;
		constraintsLReproducion.gridx = 0;
		constraintsLReproducion.gridy = 2;
		

		GridBagConstraints constraintsBCancelar = new GridBagConstraints();
		constraintsBCancelar.insets = new Insets(5, 20, 20, 20);
		constraintsBCancelar.anchor = GridBagConstraints.CENTER;
		constraintsBCancelar.gridx = 0;
		constraintsBCancelar.gridy = 4;
		
		

		panelTitulo.add(titulo, constraintsLTitulo);
		panelReproductor.add(panelReprodurtor, constraintsVWRepr);
		panelReproductor.add(lReprod, constraintsLReproducion);
		addReproductor();
		addCopyRigth();
		panelBotonoes.add(btCancelar, constraintsBCancelar);
		
	}

	private void addReproductor() {
		Controlador.getInstaciaUnica().ponerVideo();
	}

	private void addCopyRigth() {
		VideoWeb videoWeb = Controlador.getInstaciaUnica().getVideoWeb();
		JLabel copyright = new JLabel(videoWeb.getVersion());
		copyright.setFont(new Font("Arial", Font.BOLD, 10));
		copyright.setForeground(Color.WHITE);
		
		GridBagConstraints constraintsLCopy = new GridBagConstraints();
		constraintsLCopy.insets = new Insets(2, 5, 5, 5);
		constraintsLCopy.anchor = GridBagConstraints.EAST;
		constraintsLCopy.gridx = 0;
		constraintsLCopy.gridy = 3;

		panelReproductor.add(copyright, constraintsLCopy);
	}

}
