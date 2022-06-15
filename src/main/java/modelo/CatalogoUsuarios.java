package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import persistencia.FactoriaDAO;

public class CatalogoUsuarios {
	private static CatalogoUsuarios instancia;
	private FactoriaDAO factoria;

	private HashMap<Integer, Usuario> usuariosPorID;
	private HashMap<String, Usuario> usuariosPorNombre;

	public static CatalogoUsuarios getUnicaInstancia() {
		if (instancia == null)
			instancia = new CatalogoUsuarios();
		return instancia;
	}

	private CatalogoUsuarios() {
		usuariosPorID = new HashMap<Integer, Usuario>();
		usuariosPorNombre = new HashMap<String, Usuario>();

		factoria = FactoriaDAO.getInstancia();

		List<Usuario> listaAsistentes = factoria.getUsuarioDAO().listarTodosUsuarios();
		for (Usuario usuario : listaAsistentes) {
			usuariosPorID.put(usuario.getId(), usuario);
			usuariosPorNombre.put(usuario.getLogin(), usuario);
		}

	}

	public List<Usuario> getUsuarios() {
		return new ArrayList<Usuario>(usuariosPorNombre.values());
	}

	public Usuario getUsuario(String login) {
		return usuariosPorNombre.get(login);
	}

	public Usuario getUsuario(int id) {
		return usuariosPorID.get(id);
	}

	public void addUsuario(Usuario usuario) {
		usuariosPorID.put(usuario.getId(), usuario);
		usuariosPorNombre.put(usuario.getLogin(), usuario);
	}

	public void removeUsuario(Usuario usuario) {
		usuariosPorID.remove(usuario.getId());
		usuariosPorNombre.remove(usuario.getLogin());
	}

}
