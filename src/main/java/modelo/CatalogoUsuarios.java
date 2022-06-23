package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistencia.AdaptadorUsuarioDAO;
import persistencia.FactoriaDAO;

public class CatalogoUsuarios {
	
	private static CatalogoUsuarios instancia = new CatalogoUsuarios();
	public static CatalogoUsuarios getInstancia() {
		return instancia;
	}

	private Map<Integer, Usuario> mapaPorID;
	private Map<String, Usuario> mapaPorLogin;

	private AdaptadorUsuarioDAO adaptador;

	private CatalogoUsuarios() {
		try {
			FactoriaDAO factoria = FactoriaDAO.getInstancia(FactoriaDAO.TDS_DAO);
			adaptador = factoria.getUsuarioDAO();
			mapaPorID = new HashMap<Integer, Usuario>();
			mapaPorLogin = new HashMap<String, Usuario>();
			cargarCatalogo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void cargarCatalogo() {
		List<Usuario> lista = adaptador.listarTodosUsuarios();
		for (Usuario usuario : lista) {
			mapaPorID.put(usuario.getId(), usuario);
			mapaPorLogin.put(usuario.getLogin(), usuario);
		}
	}

	public void addUsuario(Usuario usuario) {
		adaptador.insertarUsuario(usuario);
		mapaPorID.put(usuario.getId(), usuario);
		mapaPorLogin.put(usuario.getLogin(), usuario);
	}
	public void removeUsuario(Usuario usuario) {
		mapaPorID.remove(usuario.getId());
		mapaPorLogin.remove(usuario.getLogin());
		adaptador.borrarUsuario(usuario);
	}

	public boolean existsUsuario(String login) {
		return mapaPorLogin.containsKey(login);
	}
	public Usuario getUsuario(int id) {
		return mapaPorID.get(id);
	}
	public Usuario getUsuario(String login) {
		return mapaPorLogin.get(login);
	}
	
	public List<Usuario> getUsuarios() {
		return new ArrayList<Usuario>(mapaPorID.values());
	}

}
