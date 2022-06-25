package controlador;

import java.util.Date;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;

import cargadorVideos.VideosEvent;
import cargadorVideos.VideosListener;
import modelo.CatalogoEtiquetas;
import modelo.CatalogoListasVideos;
import modelo.CatalogoUsuarios;
import modelo.CatalogoVideos;
import modelo.Etiqueta;
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
	// private CatalogoFiltros catalogoFiltros;

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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean registrarUsuario(String nombre, String apellidos, Date fechaNac, String email, String login,
			String pass) {
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
		if (!usuario.isPassword(pass))
			return false;
		usuarioActual = usuario;
		return true;
	}

	// TODO: LOGOUT
	public boolean logoutUsuario() {
		if (usuarioActual == null)
			return false;
		usuarioActual = null;
		return true;
	}
	
	public boolean isUsuarioLogin() {
		return usuarioActual != null;
	}

	public boolean isUsuarioPremium() {
		return isUsuarioLogin() && usuarioActual.isPremium();
	}

	public boolean isCumpleUsuario() {
		return isUsuarioPremium() && usuarioActual.isCumple();
	}

	public String getNombreUsuario() {
		if (isUsuarioLogin())
			return usuarioActual.getNombre();
		return "Usuari@";
	}

	public void setUsuarioPremium() {
		if (isUsuarioLogin() && !isUsuarioPremium()) {
			usuarioActual.setPremium(true);
			FactoriaDAO.getInstancia().getUsuarioDAO().modificarUsuario(usuarioActual);
		}
	}

	public void generarPDF() {
		if (isUsuarioPremium())
			usuarioActual.generarPDF();
	}

	// TODO: filtros
	
	public List<String> getEtiquetas() {
		return catalogoEtiquetas.getNombresEtiquetas();
	}
	
	// TODO: etiquetar Video
	
	public VideoWeb getVideoWeb() {
		return videoWeb;
	}

	// TODO: get/setIconoVideo
	public ImageIcon getIconoVideo(int id) {
		if (catalogoVideos.existsVideo(id)) {
			Video video = catalogoVideos.getVideo(id);
			ImageIcon icono = video.getIcono();
			if (icono == null) {
				icono = videoWeb.getThumb(video.getUrl());
				video.setIcono(icono);
			}
			return icono;
		}
		return null;
	}
	
	public String getTituloCortoVideo(int id) {
		if (catalogoVideos.existsVideo(id)) {
			Video video = catalogoVideos.getVideo(id);
			return video.getTituloCorto();
		}
		return "Sin titulo";
	}
	
	public void reproducirVideo(int id) {
		if (usuarioActual != null && catalogoVideos.existsVideo(id)) {
			Video video = catalogoVideos.getVideo(id);

			usuarioActual.addRecentVideo(video);
			FactoriaDAO.getInstancia().getListaVideosDAO().modificarListaVideos(usuarioActual.getRecentVideo());

			video.incrNumRepro();
			FactoriaDAO.getInstancia().getVideoDAO().modificarVideo(video);

			videoWeb.playVideo(video.getUrl());
		}
	}
	
	public void cancelarVideo() {
		videoWeb.cancel();
	}

	public List<Video> getVideosExplorar() {
		return catalogoVideos.getVideos();
	}

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
