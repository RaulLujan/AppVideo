package vista;

import java.awt.Font;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;


import java.io.*;
import java.util.Locale;

public class GenerarPDF extends JPanel {
	private static final Font FUENTE_TITULO = new Font("TimesRoman", 22, Font.BOLD | Font.ITALIC);
	private static final Font FUENTE_SUBTITULO = new Font("TimesRoman", 18, Font.ITALIC);
	private static final Font FUENTE_PARRAFO = new Font("TimesRoman", 12, Font.PLAIN);

	private static final String NOMBRE_PDF = "topVideos.pdf";

	public GenerarPDF() {
		confLamina();
	}

	private void confLamina() {

	}

//	private void generarPDF() {
//		try {
//			Document documento = new Document();
//			try {
//				PdfWriter.getInstance(documento, new FileOutputStream(NOMBRE_PDF));
//			} catch (FileNotFoundException fileNotFoundException) {
//				System.out.println("No such file was found to generate the PDF "
//						+ "(No se encontró el fichero para generar el pdf)" + fileNotFoundException);
//			}
//			documento.open();
//
//			// AQUÍ COMPLETAREMOS NUESTRO CÓDIGO PARA GENERAR EL PDF
//
//			documento.close();
//			System.out.println("Your PDF file has been generated!(¡Se ha generado tu hoja PDF!");
//		} catch (Exception e) {
//			e.getMessage();
//		}
//	}
//
//	private void guardarFichero() {
//		JFileChooser fileChooser = new JFileChooser();
//		fileChooser.setLocale(Locale.ENGLISH);
//		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//		FileNameExtensionFilter filtro = new FileNameExtensionFilter(".pdf", "pdf");
//		fileChooser.setFileFilter(filtro);
//
//		int seleccion = fileChooser.showSaveDialog(laminaTexto.getTextPane());
//
//		if (JFileChooser.APPROVE_OPTION == seleccion) {
//			try {
//				File archivo = fileChooser.getSelectedFile();
//				if (!archivo.exists()) {
//					try {
//						archivo.createNewFile();
//					} catch (IOException e1) {
//						e1.printStackTrace();
//					}
//				}
//
//				PrintStream salida = new PrintStream(archivo + ".pdf");
//				salida.print(laminaTexto.getTextPane().getText());
//				salida.close();
//
//			} catch (FileNotFoundException ex) {
//				JOptionPane.showMessageDialog(null, "Error al guardar fichero: " + ex.getMessage(), "Error",
//						JOptionPane.ERROR_MESSAGE);
//			}
//		}
//	}

}
