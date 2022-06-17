package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistencia.AdaptadorUsuarioDAO;
import persistencia.FactoriaDAO;

public class CatalogoUsuarios {
	private static CatalogoUsuarios instancia = new CatalogoUsuarios();

	private AdaptadorUsuarioDAO adaptadorUsuario;
	private FactoriaDAO factoria;

	private Map<Integer, Usuario> usuariosPorID;
	private Map<String, Usuario> usuariosPorNombre;

	private CatalogoUsuarios() {
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.TDS_DAO);
			adaptadorUsuario = factoria.getUsuarioDAO();
			usuariosPorID = new HashMap<Integer, Usuario>();
			usuariosPorNombre = new HashMap<String, Usuario>();
			this.cargarCatalogo();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static CatalogoUsuarios getUnicaInstancia() {
		return instancia;
	}

	public List<Usuario> getUsuarios() {
		List<Usuario> lista = new ArrayList<Usuario>();
		for (Usuario u : usuariosPorID.values())
			lista.add(u);
		return lista;
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

	private void cargarCatalogo() {
		List<Usuario> usuariosBD = adaptadorUsuario.listarTodosUsuarios();
		for (Usuario u : usuariosBD) {
			usuariosPorID.put(u.getId(), u);
			usuariosPorNombre.put(u.getLogin(), u);
		}
	}

}
