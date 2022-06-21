package vista;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Font.FontStyle;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import controlador.Controlador;
import modelo.CatalogoUsuarios;

import java.io.*;
import java.util.Locale;
import java.util.Map;

public class GenerarPDF extends JPanel {
	private static final Font FUENTE_TITULO = new Font(FontFamily.TIMES_ROMAN, 22.0f, Font.BOLD);
	private static final Font FUENTE_SUBTITULO = new Font(FontFamily.TIMES_ROMAN, 18.0f, Font.ITALIC);
	private static final Font FUENTE_PARRAFO = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.NORMAL);

	private static final String NOMBRE_PDF = "listasVideos.pdf";

	public GenerarPDF() {
		confLamina();
	}

	private void confLamina() {
		generarPDF();
	}

	private void generarPDF() {
			Document documento = new Document(PageSize.A4, 50, 50, 50, 50);
			try {
				PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(NOMBRE_PDF));
				documento.open();
				anyadirContenido(documento);				
				documento.close();
				writer.close();
			} catch (FileNotFoundException | DocumentException fileNotFoundException) {
				System.out.println("No se encontró el fichero para generar el pdf)" + fileNotFoundException);
			}
	}

	private void anyadirContenido(Document doc) throws DocumentException {
		String tituloLista;
		Map<String, Integer> videosDeLista;
		
		doc.addTitle("Listas de videos");
		
		Map<String, Map<String, Integer>> listasVideos = Controlador.getInstancia().getListaVideosNumRepro();
		listasVideos.forEach((k,v) -> {
			//Paragraph p = new Paragraph(k, FUENTE_PARRAFO);
			try {
				doc.add(new Paragraph(k, FUENTE_SUBTITULO));
				v.forEach((k2, v2) -> {
					try {
						doc.add(new Paragraph("Titulo: "+ k2 + ", número de reproducciones:  " + v2, FUENTE_PARRAFO));
					} catch (DocumentException e) {
						e.printStackTrace();
					}
				});
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			
		});
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
	}

}
