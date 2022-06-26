package controlador;

import java.util.Date;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;

import cargadorVideos.VideosEvent;
import cargadorVideos.VideosListener;
import modelo.CatalogoEtiquetas;
import modelo.CatalogoFiltros;
import modelo.CatalogoListasVideos;
import modelo.CatalogoUsuarios;
import modelo.CatalogoVideos;
import modelo.Etiqueta;
import modelo.Filtro;
import modelo.ListaVideos;
import modelo.Usuario;
import modelo.Video;
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

	private VideoWeb videoWeb;
	private Video videoActual;
	private Usuario usuarioActual;

	private CatalogoUsuarios catalogoUsuarios;
	private CatalogoListasVideos catalogoListas;
	private CatalogoVideos catalogoVideos;
	private CatalogoEtiquetas catalogoEtiquetas;
	private CatalogoFiltros catalogoFiltros;

	private AdaptadorUsuarioDAO adaptadorUsuario;
	private AdaptadorListaVideosDAO adaptadorListaVideos;
	private AdaptadorVideoDAO adaptadorVideo;

	private Controlador() {
		try {
			videoWeb = new VideoWeb();
			usuarioActual = null;

			catalogoUsuarios = CatalogoUsuarios.getInstancia();
			catalogoListas = CatalogoListasVideos.getInstancia();
			catalogoVideos = CatalogoVideos.getInstancia();
			catalogoEtiquetas = CatalogoEtiquetas.getInstancia();
			catalogoFiltros = CatalogoFiltros.getInstancia();
			
			FactoriaDAO factoria = FactoriaDAO.getInstancia(FactoriaDAO.TDS_DAO);
			adaptadorUsuario = factoria.getUsuarioDAO();
			adaptadorListaVideos = factoria.getListaVideosDAO();
			adaptadorVideo = factoria.getVideoDAO();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isUsuarioLogin() {
		return usuarioActual != null;
	}

	// Ventana
	public String getNombreUsuario() {
		if (isUsuarioLogin())
			return usuarioActual.getNombre();
		return "Usuari@";
	}

	// TabRegistro
	public boolean registrarUsuario(String nombre, String apellidos,
			Date fechaNac, String email, String login, String pass) {
		if (catalogoUsuarios.existsUsuario(login))
			return false;

		Usuario usuario = new Usuario(login, pass, nombre, apellidos, fechaNac, email);
		catalogoUsuarios.addUsuario(usuario);
		catalogoListas.addListaVideos(usuario.getRecentVideo());
		return true;
	}

	// TabLogin
	public boolean loginUsuario(String login, String pass) {
		if (isUsuarioLogin() || !catalogoUsuarios.existsUsuario(login))
			return false;
		Usuario usuario = catalogoUsuarios.getUsuario(login);
		if (!usuario.isPassword(pass))
			return false;
		usuarioActual = usuario;
		return true;
	}

	// Ventana
	public boolean logoutUsuario() {
		if (!isUsuarioLogin())
			return false;
		usuarioActual = null;
		return true;
	}

	// Ventana
	public void setUsuarioPremium() {
		if (isUsuarioLogin() && !isUsuarioPremium()) {
			usuarioActual.setPremium(true);
			adaptadorUsuario.modificarUsuario(usuarioActual);
		}
	}

	public boolean isUsuarioPremium() {
		return isUsuarioLogin() && usuarioActual.isPremium();
	}

	// TabGenerarPDF
	public void generarPDF() {
		if (isUsuarioPremium())
			usuarioActual.generarPDF();
	}

	// TabFiltros
	public List<String> getNombresFiltros() {
		return catalogoFiltros.getFiltros();
	}
	
	// TabFiltros
	public String getDescripcionFiltro(String nombre) {
		if (catalogoFiltros.existsFiltro(nombre)) {
			Filtro filtro = catalogoFiltros.getFiltro(nombre);
			return filtro.getDescripcion();
		}
		return "El filtro no existe.";
	}
	
	// TabFiltros, TabExplorar, TabNuevaLista
	public String getFiltroUsuario() {
		if (isUsuarioLogin())
			return usuarioActual.getFiltro().getClass().getSimpleName();
		return "NoFiltro";
	}
	
	// TabFiltros
	public void setFiltroUsuario(String nombre) {
		if (isUsuarioPremium()) {
			Filtro filtro = catalogoFiltros.getFiltro(nombre);
			if (usuarioActual.getFiltro() != filtro) {
				usuarioActual.setFiltro(filtro);
				adaptadorUsuario.modificarUsuario(usuarioActual);
			}
		}
	}
	
	// CaratulaVideo
	public ImageIcon getIconoVideo(Video video) {
		ImageIcon icono = video.getIcono();
		if (icono == null) {
			icono = videoWeb.getThumb(video.getUrl());
			video.setIcono(icono);
		}
		return icono;
	}
	
	public Video getVideoActual() {
		return videoActual;
	}
	
	public void setVideoActual(Video video) {
		videoActual = video;
	}
	
	// TabReproductor
	public VideoWeb getVideoWeb() {
		return videoWeb;
	}

	// TabReproductor
	public boolean setEtiquetaVideo(String nombre) {
		if (!catalogoEtiquetas.existsEtiqueta(nombre))
			catalogoEtiquetas.addEtiqueta(new Etiqueta(nombre));
		Etiqueta etiqueta = catalogoEtiquetas.getEtiqueta(nombre);
		if (!videoActual.containsEtiqueta(etiqueta)) {
			videoActual.addEtiqueta(etiqueta);
			adaptadorVideo.modificarVideo(videoActual);
			return true;
		}
		return false;
	}
	
	// TabReproductor
	public void playVideo() {
		if (isUsuarioLogin()) {
			usuarioActual.addRecentVideo(videoActual);
			adaptadorListaVideos.modificarListaVideos(usuarioActual.getRecentVideo());

			videoActual.incrNumRepro();
			adaptadorVideo.modificarVideo(videoActual);

			videoWeb.playVideo(videoActual.getUrl());
		}
	}
	
	// TabReproductor
	public void stopVideo() {
		videoWeb.cancel();
	}
	
	// TabExplorar, TabNuevaLista
	public List<String> getEtiquetas() {
		return catalogoEtiquetas.getNombresEtiquetas();
	}
	
	// TabNuevaLista
	public ListaVideos addListaVideos(String nombre) {
		if (isUsuarioLogin()) {
			ListaVideos lista = new ListaVideos(nombre);
			catalogoListas.addListaVideos(lista);
			
			usuarioActual.addMiLista(lista);
			adaptadorUsuario.modificarUsuario(usuarioActual);
			
			return lista;
		}
		return null;
	}
	
	// TabNuevaLista
	public void removeListaVideos(ListaVideos lista) {
		if (isUsuarioLogin()) {
			usuarioActual.removeMiLista(lista);
			adaptadorUsuario.modificarUsuario(usuarioActual);
			
			catalogoListas.removeListaVideos(lista);
		}
	}
		
	// TabNuevaLista
	public void addVideoToListaVideos(Video video, ListaVideos lista) {
		if (!lista.containsVideo(video)) {
			lista.addFirstVideo(video);
			adaptadorListaVideos.modificarListaVideos(lista);
		}
	}
	
	// TabNuevaLista
	public void removeVideoFromListaVideos(Video video, ListaVideos lista) {
		if (lista.containsVideo(video)) {
			lista.removeVideo(video);
			adaptadorListaVideos.modificarListaVideos(lista);
		}
	}

	// TabExplorar, TabNuevaLista
	public List<Video> getVideosExplorar() {
		return catalogoVideos.getVideos();
	}

	// TabExplorar, TabNuevaLista
	public List<Video> getVideosBuscar(String subtitulo, List<String> nombres) {
		List<Etiqueta> etiquetas = new LinkedList<Etiqueta>();
		for (String nombre : nombres)
			etiquetas.add(catalogoEtiquetas.getEtiqueta(nombre));
		return catalogoVideos.getVideosOK(usuarioActual, subtitulo, etiquetas);
	}

	// TabMisListas
	public List<String> getMisListas() {
		if (isUsuarioLogin())
			return usuarioActual.getNombresMisListas();
		return new LinkedList<String>();
	}

	// TabMisListas
	public ListaVideos getMiLista(String nombre) {
		if (isUsuarioLogin())
			return usuarioActual.getMiLista(nombre);
		return null;
	}

	// TabRecientes
	public List<Video> getRecientes() {
		if (isUsuarioLogin())
			return usuarioActual.getRecentVideo().getVideos();
		return new LinkedList<Video>();
	}

	// TabMasVistos
	public List<Video> getTopVideos() {
		if (isUsuarioPremium())
			return catalogoVideos.getTopVideos();
		return new LinkedList<Video>();
	}
	
	@Override
	public void nuevosVideos(EventObject e) {
		VideosEvent videos = (VideosEvent) e;
		for (cargadorVideos.Video videoCV : videos.getListaVideos().getVideo()) {
			Video video = new Video(videoCV.getURL(), videoCV.getTitulo());
			if (!catalogoVideos.existsVideo(video.getUrl())) {
				for (String etiquetaCV : videoCV.getEtiqueta()) {
					Etiqueta etiqueta = null;
					if (catalogoEtiquetas.existsEtiqueta(etiquetaCV)) {
						etiqueta = catalogoEtiquetas.getEtiqueta(etiquetaCV);
					} else {
						etiqueta = new Etiqueta(etiquetaCV);
						catalogoEtiquetas.addEtiqueta(etiqueta);
					}
					video.addEtiqueta(etiqueta);
				}
				catalogoVideos.addVideo(video);
			}
		}
	}

}
