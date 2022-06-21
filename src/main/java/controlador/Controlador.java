package controlador;

import java.util.Date;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cargadorVideos.Videos;
import cargadorVideos.VideosEvent;
import cargadorVideos.VideosListener;
import modelo.CatalogoEtiquetas;
import modelo.CatalogoUsuarios;
import modelo.CatalogoVideos;
import modelo.Etiqueta;
import modelo.ListaVideos;
import modelo.Usuario;
import modelo.Video;
import persistencia.AdaptadorListaVideosDAO;
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

	private Usuario usuarioActual;
	private FactoriaDAO factoria;

	private CatalogoVideos catalogoVideos;
	private CatalogoEtiquetas catalogoEtiquetas;

	private AdaptadorListaVideosDAO adaptadorListaVideos;
	private AdaptadorVideoDAO adaptadorVideo;

	private VideoWeb videoWeb;

	private Controlador() {
		try {
			videoWeb = new VideoWeb();
			usuarioActual = null;

			catalogoVideos = CatalogoVideos.getInstancia();
			catalogoEtiquetas = CatalogoEtiquetas.getInstancia();

			factoria = FactoriaDAO.getInstancia();

			adaptadorListaVideos = factoria.getListaVideosDAO();
			adaptadorVideo = factoria.getVideoDAO();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Usuario getUsuarioActual() {
		return usuarioActual;
	}

	public boolean isUsuarioRegistrado(String login) {
		return CatalogoUsuarios.getInstancia().getUsuario(login) != null;
	}

	public boolean getLogin(String nombre, String pass) {
		Usuario usuario = CatalogoUsuarios.getInstancia().getUsuario(nombre);
		if (usuario != null && usuario.getPassword().equals(pass)) {
			this.usuarioActual = usuario;
			return true;
		}

		return false;
	}

	public boolean registrarUsuario(String nombre, String apellidos, Date fechaNacimiento, String email, String login,
			String pass) {
		if (isUsuarioRegistrado(login))
			return false;

		Usuario usuario = new Usuario(login, pass, nombre, apellidos, fechaNacimiento, email);
		factoria.getUsuarioDAO().insertarUsuario(usuario);
		CatalogoUsuarios.getInstancia().addUsuario(usuario);

		return true;
	}

	public boolean registrarUsuario(String nombre, Date fechaNacimiento, String login, String pass) {
		return registrarUsuario(nombre, "", fechaNacimiento, "", login, pass);
	}

	public boolean isUsuarioLogin() {
		return this.usuarioActual != null;
	}

	public VideoWeb getVideoWeb() {
		return videoWeb;
	}

	public void reproducirVideo(int id) {
		if (usuarioActual != null && catalogoVideos.existeVideo(id)) {
			Video video = catalogoVideos.getVideo(id);

			usuarioActual.addRecentVideo(video);
			adaptadorListaVideos.modificarListaVideos(usuarioActual.getRecentVideo());

			video.incrementarNumReproducciones();
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
			if (!catalogoVideos.existeVideo(video.getUrl())) {
				for (String etiquetaCargadorV : videoCargadorV.getEtiqueta()) {
					Etiqueta etiqueta = null;
					if (catalogoEtiquetas.existeEtiqueta(etiquetaCargadorV)) {
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

	public Map<String, Map<String, Integer>> getListaVideosNumRepro() {
		Map<String, Map<String, Integer>> resultado = new HashMap<String, Map<String,Integer>>();
		List<ListaVideos> listaVideos = usuarioActual.getMisListas();
		
		for (ListaVideos lv : listaVideos) {
			Map<String, Integer> videos = new HashMap<>();
			
			for (Video v : lv.getVideos()) {
				videos.put(v.getTitulo(), v.getNumReproducciones());
			}
			
			resultado.put(lv.getNombre(), videos);
		}
		
		return resultado;
	}
}
