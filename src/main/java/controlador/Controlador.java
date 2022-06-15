package controlador;

import java.util.Date;

import modelo.CatalogoUsuarios;
import modelo.Usuario;
import persistencia.FactoriaDAO;

public class Controlador {

	private Usuario usuarioActual;
	private static Controlador instancia;
	private FactoriaDAO factoria;

	private Controlador() {
		usuarioActual = null;
		factoria = FactoriaDAO.getInstancia();
	}

	public static Controlador getInstaciaUnica() {
		if (instancia == null)
			instancia = new Controlador();
		return instancia;
	}
	
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}
	
	public boolean isUsuarioREgistrador(String login) {
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
	
	public boolean registrarUsuario(String nombre, String apellidos, Date fechaNacimiento, String email, String login, String pass) {
		if (isUsuarioREgistrador(login)) return false;
		
		Usuario usuario = new Usuario(login, pass, nombre, apellidos, fechaNacimiento, email);
		factoria.getUsuarioDAO().insertarUsuario(usuario);
		CatalogoUsuarios.getUnicaInstancia().addUsuario(usuario);
		
		return true;
	}
	
	public boolean registrarUsuario(String nombre, Date fechaNacimiento, String login, String pass) {
		return registrarUsuario(nombre, "", fechaNacimiento, "", login, pass);
	}
}
