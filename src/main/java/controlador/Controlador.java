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
import modelo.Usuario;
import modelo.Video;
import persistencia.FactoriaDAO;
import tds.video.VideoWeb;

public class Controlador implements VideosListener {

	private static Controlador instancia;

	public static Controlador getInstancia() {
		if (instancia == null)
			instancia = new Controlador();
		return instancia;
	}

	private CatalogoUsuarios catalogoUsuarios;
	private CatalogoListasVideos catalogoListas;
	private CatalogoVideos catalogoVideos;
	private CatalogoEtiquetas catalogoEtiquetas;
	 private CatalogoFiltros catalogoFiltros;

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
			catalogoFiltros = CatalogoFiltros.getInstancia();
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
	public boolean registrarUsuario(String nombre, Date fechaNac, String login, String pass) {
		return registrarUsuario(nombre, "", fechaNac, "", login, pass);
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

	// TODO LOGOUT
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
			FactoriaDAO.getInstancia().getUsuarioDAO()
				.modificarUsuario(usuarioActual);
		}
	}

	public boolean isUsuarioPremium() {
		return isUsuarioLogin() && usuarioActual.isPremium();
	}

	// Ventana
	public boolean isCumpleUsuario() {
		return isUsuarioPremium() && usuarioActual.isCumple();
	}

	// TabGenerarPDF
	public void generarPDF() {
		if (isUsuarioPremium())
			usuarioActual.generarPDF();
	}

	// TODO filtros
	
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
				FactoriaDAO.getInstancia().getUsuarioDAO()
					.modificarUsuario(usuarioActual);
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
	
	// TabReproductor
	public VideoWeb getVideoWeb() {
		return videoWeb;
	}

	// TabReproductor
	public boolean setEtiquetaVideo(String nombre, Video video) {
		if (!catalogoEtiquetas.existsEtiqueta(nombre))
			catalogoEtiquetas.addEtiqueta(new Etiqueta(nombre));
		Etiqueta etiqueta = catalogoEtiquetas.getEtiqueta(nombre);
		if (!video.containsEtiqueta(etiqueta)) {
			video.addEtiqueta(etiqueta);
			FactoriaDAO.getInstancia().getVideoDAO()
				.modificarVideo(video);
			return true;
		}
		return false;
	}
	
	// TabReproductor
	public void playVideo(Video video) {
		if (isUsuarioLogin()) {
			usuarioActual.addRecentVideo(video);
			FactoriaDAO.getInstancia().getListaVideosDAO()
				.modificarListaVideos(usuarioActual.getRecentVideo());

			video.incrNumRepro();
			FactoriaDAO.getInstancia().getVideoDAO()
				.modificarVideo(video);

			videoWeb.playVideo(video.getUrl());
		}
	}
	
	// TabReproductor
	public void cancelVideo() {
		videoWeb.cancel();
	}
	
	// TabExplorar, TabNuevaLista
	public List<String> getEtiquetas() {
		return catalogoEtiquetas.getNombresEtiquetas();
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
	
	// TODO: crear/eliminar/modificar ListaVideos
	// TODO: getMisListas/MiLista

	public List<Video> getRecientes() {
		if (isUsuarioLogin())
			return usuarioActual.getRecentVideo().getVideos();
		return null;
	}

	public List<Video> getTopVideos() {
		if (isUsuarioPremium())
			return catalogoVideos.getTopVideos();
		else
			return null;
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
