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

	private Map<String, Usuario> mapaPorLogin;

	private AdaptadorUsuarioDAO adaptador;

	private CatalogoUsuarios() {
		try {
			FactoriaDAO factoria = FactoriaDAO.getInstancia(FactoriaDAO.TDS_DAO);
			adaptador = factoria.getUsuarioDAO();
			mapaPorLogin = new HashMap<String, Usuario>();
			cargarCatalogo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void cargarCatalogo() {
		List<Usuario> lista = adaptador.listarTodosUsuarios();
		for (Usuario usuario : lista)
			mapaPorLogin.put(usuario.getLogin(), usuario);
	}

	public boolean addUsuario(Usuario usuario) {
		if (existsUsuario(usuario.getLogin()))
			return false;
		
		adaptador.insertarUsuario(usuario);
		mapaPorLogin.put(usuario.getLogin(), usuario);
		return true;
	}
	public boolean removeUsuario(Usuario usuario) {
		if (!existsUsuario(usuario.getLogin()))
			return false;
		
		mapaPorLogin.remove(usuario.getLogin());
		adaptador.borrarUsuario(usuario);
		return true;
	}

	public boolean existsUsuario(String login) {
		return mapaPorLogin.containsKey(login);
	}
	public Usuario getUsuario(String login) {
		return mapaPorLogin.get(login);
	}
	
	public List<Usuario> getUsuarios() {
		return new ArrayList<Usuario>(mapaPorLogin.values());
	}

}
