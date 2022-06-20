package controlador;

import java.util.Date;

import modelo.CatalogoUsuarios;
import modelo.CatalogoVideos;
import modelo.Usuario;
import modelo.Video;
import persistencia.AdaptadorListaVideosDAO;
import persistencia.AdaptadorVideoDAO;
import persistencia.FactoriaDAO;
import tds.video.VideoWeb;

public class Controlador {

	private Usuario usuarioActual;
	private static Controlador instancia;
	private FactoriaDAO factoria;

	private CatalogoVideos catalogoVideos;

	private AdaptadorListaVideosDAO adaptadorListaVideos;
	private AdaptadorVideoDAO adaptadorVideo;

	private VideoWeb videoWeb;

	private Controlador() {
		try {
			videoWeb = new VideoWeb();
			usuarioActual = null;

			catalogoVideos = CatalogoVideos.getUnicaInstancia();

			factoria = FactoriaDAO.getInstancia();

			adaptadorListaVideos = factoria.getListaVideosDAO();
			adaptadorVideo = factoria.getVideoDAO();
		} catch (Exception e ) {
			e.printStackTrace();
		}
	}

	public static Controlador getInstaciaUnica() {
		if (instancia == null)
			instancia = new Controlador();
		return instancia;
	}

	public Usuario getUsuarioActual() {
		return usuarioActual;
	}

	public boolean isUsuarioRegistrado(String login) {
		return CatalogoUsuarios.getUnicaInstancia().getUsuario(login) != null;
	}

	public boolean getLogin(String nombre, String pass) {
		Usuario usuario = CatalogoUsuarios.getUnicaInstancia().getUsuario(nombre);
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
		CatalogoUsuarios.getUnicaInstancia().addUsuario(usuario);

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

}
