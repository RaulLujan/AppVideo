package controlador;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfWriter;

import cargadorVideos.VideosEvent;
import cargadorVideos.VideosListener;
import modelo.CatalogoEtiquetas;
import modelo.CatalogoListasVideos;
import modelo.CatalogoUsuarios;
import modelo.CatalogoVideos;
import modelo.Etiqueta;
import modelo.ListaVideos;
import modelo.Usuario;
import modelo.Video;
import persistencia.AdaptadorEtiquetaDAO;
import persistencia.AdaptadorListaVideosDAO;
import persistencia.AdaptadorUsuarioDAO;
import persistencia.AdaptadorVideoDAO;
import persistencia.FactoriaDAO;
import tds.video.VideoWeb;

public class Controlador implements VideosListener {
	
	private static Controlador instancia;
	public static Controlador getInstancia() {
		if (instancia == null)
			instancia = new Controlador();
		return instancia;
	}

	private static final Font FUENTE_TITULO = new Font(FontFamily.TIMES_ROMAN, 22.0f, Font.BOLD);
	private static final Font FUENTE_SUBTITULO = new Font(FontFamily.TIMES_ROMAN, 18.0f, Font.ITALIC);
	private static final Font FUENTE_PARRAFO = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.NORMAL);

	private static final String NOMBRE_PDF = "listasVideos.pdf";
	
	private AdaptadorUsuarioDAO adaptadorUsuario;
	private AdaptadorListaVideosDAO adaptadorListaVideos;
	private AdaptadorVideoDAO adaptadorVideo;

	private CatalogoUsuarios catalogoUsuarios;
	private CatalogoListasVideos catalogoListas;
	private CatalogoVideos catalogoVideos;
	private CatalogoEtiquetas catalogoEtiquetas;
	//private CatalogoFiltros catalogoFiltros;

	private Usuario usuarioActual;

	private VideoWeb videoWeb;

	private Controlador() {
		try {
			videoWeb = new VideoWeb();
			usuarioActual = null;

			catalogoUsuarios = CatalogoUsuarios.getInstancia();
			catalogoListas = CatalogoListasVideos.getInstancia();
			catalogoVideos = CatalogoVideos.getInstancia();
			catalogoEtiquetas = CatalogoEtiquetas.getInstancia();

			FactoriaDAO factoria = FactoriaDAO.getInstancia();
			adaptadorListaVideos = factoria.getListaVideosDAO();
			adaptadorVideo = factoria.getVideoDAO();
			adaptadorUsuario = factoria.getUsuarioDAO();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean registrarUsuario(String nombre, String apellidos, Date fechaNac, String email, String login, String pass) {
		if (catalogoUsuarios.existsUsuario(login))
			return false;

		Usuario usuario = new Usuario(login, pass, nombre, apellidos, fechaNac, email);
		catalogoUsuarios.addUsuario(usuario);
		catalogoListas.addListaVideos(usuario.getRecentVideo());
		return true;
	}
	public boolean registrarUsuario(String nombre, Date fechaNac, String login, String pass) {
		return registrarUsuario(nombre, "", fechaNac, "", login, pass);
	}

	public boolean loginUsuario(String login, String pass) {
		if (usuarioActual != null || !catalogoUsuarios.existsUsuario(login))
			return false;
		Usuario usuario = catalogoUsuarios.getUsuario(login);
		if (!usuario.checkPassword(pass))
			return false;
		usuarioActual = usuario;
		return true;
	}

	public boolean isUsuarioLogin() {
		return usuarioActual != null;
	}
	
	public String getNombreUsuario() {
		if (usuarioActual != null)
			return usuarioActual.getNombre();
		return "Usuari@";
	}

	public void activarUsuarioPremium() {
		if (usuarioActual != null && !usuarioActual.isPremium()) {
			usuarioActual.setPremium(true);
			adaptadorUsuario.modificarUsuario(usuarioActual);
		}
	}
	
	public void desactivarUsuarioPremium() {
		if (usuarioActual != null && usuarioActual.isPremium()) {
			usuarioActual.setPremium(false);
			adaptadorUsuario.modificarUsuario(usuarioActual);
		}
	}
	
	public boolean isUsuarioPremium() {
		return usuarioActual != null && usuarioActual.isPremium();
	}
	
	public boolean comprobarCumpleUsuario() {
		return isUsuarioPremium() && usuarioActual.isCumple();
	}
	
	public VideoWeb getVideoWeb() {
		return videoWeb;
	}

	public void reproducirVideo(int id) {
		if (usuarioActual != null && catalogoVideos.existsVideo(id)) {
			Video video = catalogoVideos.getVideo(id);

			usuarioActual.addRecentVideo(video);
			adaptadorListaVideos.modificarListaVideos(usuarioActual.getRecentVideo());

			video.incrementarNumRepro();
			adaptadorVideo.modificarVideo(video);

			videoWeb.playVideo(video.getUrl());
		}
	}

	public void ponerVideo() {
		videoWeb.playVideo("https://www.youtube.com/watch?v=hnRphfqIvsM");
	}

	@Override
	public void nuevosVideos(EventObject e) {
		VideosEvent videos = (VideosEvent) e;
		for (cargadorVideos.Video videoCargadorV : videos.getListaVideos().getVideo()) {
			Video video = new Video(videoCargadorV.getURL(), videoCargadorV.getTitulo());
			if (!catalogoVideos.existsVideo(video.getUrl())) {
				for (String etiquetaCargadorV : videoCargadorV.getEtiqueta()) {
					Etiqueta etiqueta = null;
					if (catalogoEtiquetas.existsEtiqueta(etiquetaCargadorV)) {
						etiqueta = catalogoEtiquetas.getEtiqueta(etiquetaCargadorV);
					} else {
						etiqueta = new Etiqueta(etiquetaCargadorV);
						catalogoEtiquetas.addEtiqueta(etiqueta);
					}
					video.addEtiqueta(etiqueta);
				}
				catalogoVideos.addVideo(video);
			}
		}
	}
	
	public List<Video> getRecientes(){
		return usuarioActual.getRecentVideo().getVideos();
	}
	
	public List<Video> getTopVideos(){
		return catalogoVideos.getTopVideos();
	}

	private Map<String, Map<String, Integer>> getListaVideosNumRepro() {
		Map<String, Map<String, Integer>> resultado = new HashMap<String, Map<String, Integer>>();
		List<ListaVideos> listaVideos = usuarioActual.getMisListas();

		for (ListaVideos lv : listaVideos) {
			Map<String, Integer> videos = new HashMap<>();

			for (Video v : lv.getVideos()) {
				videos.put(v.getTitulo(), v.getNumRepro());
			}

			resultado.put(lv.getNombre(), videos);
		}

		return resultado;
	}

	private void anyadirContenido(Document doc) throws DocumentException {
		String tituloLista;
		Map<String, Integer> videosDeLista;

		doc.addTitle("Listas de videos");

		Map<String, Map<String, Integer>> listasVideos = getListaVideosNumRepro();
		listasVideos.forEach((k, v) -> {
			try {
				doc.add(new Paragraph(k, FUENTE_SUBTITULO));
				v.forEach((k2, v2) -> {
					try {
						doc.add(new Paragraph("Titulo: " + k2 + ", n�mero de reproducciones:  " + v2, FUENTE_PARRAFO));
					} catch (DocumentException e) {
						e.printStackTrace();
					}
				});
			} catch (DocumentException e) {
				e.printStackTrace();
			}

		});

	}
	
	public void generarPDF() {

		Document documento = new Document(PageSize.A4, 50, 50, 50, 50);
		try {
			PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(NOMBRE_PDF));
			documento.open();
			anyadirContenido(documento);
			documento.close();
			writer.close();
		} catch (FileNotFoundException | DocumentException fileNotFoundException) {
			System.out.println("No se encontr� el fichero para generar el pdf)" + fileNotFoundException);
		}
	}
}
