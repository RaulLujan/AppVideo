package persistencia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import modelo.ListaVideos;
import modelo.Usuario;
import tds.driver.ServicioPersistencia;

public class AdaptadorUsuarioTDS implements AdaptadorUsuarioDAO {

	private static AdaptadorListaVideosTDS instancia = null;
	private ServicioPersistencia server;
	private SimpleDateFormat dateFormat;
	
	
	private String concatenarIDListaVideos(List<ListaVideos> listaLV) {
		String idsLV = "";
		for (ListaVideos lv : listaLV)
			idsLV += lv.getId() + " ";
		return idsLV;
	}
	
	
	@Override
	public void registrarUsuario(Usuario usuario) {
		Entidad entidadUsu = null;
		if (server.recuperarEntidad(usuario.getId()) != null)
			return;

		AdaptadorListaVideosTDS adaptadorLV = AdaptadorListaVideosTDS.getInstancia();
		for (ListaVideos listaVideos : usuario.getMisListas())
			adaptadorLV.registrarListaVideos(listaVideos);
		adaptadorLV.registrarListaVideos(usuario.getRecentVideo());
		
		entidadUsu = new Entidad();
		entidadUsu.setNombre("usuario");
		entidadUsu.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad("filtro", usuario.getFiltro().getClass().getSimpleName()),
				new Propiedad("premium", String.valueOf(usuario.isPremium())),
				new Propiedad("login", usuario.getLogin()),
				new Propiedad("password", usuario.getPassword()),
				new Propiedad("nombre", usuario.getNombre()),
				new Propiedad("apellidos", usuario.getApellidos()),
				new Propiedad("fechaNac", dateFormat.format(usuario.getFechaNac())),
				new Propiedad("email", usuario.getEmail()),
				new Propiedad("recentVideo", String.valueOf(usuario.getRecentVideo().getId())),
				new Propiedad("misListas", concatenarIDListaVideos(usuario.getMisListas())))));
		
		entidadUsu = server.registrarEntidad(entidadUsu);
		
		usuario.setCodigo(entidadUsu.getId());
	}

	@Override
	public void borrarUsuario(Usuario usuario) {
		AdaptadorListaVideosTDS adaptadorLV = AdaptadorListaVideosTDS.getInstancia();
		for (ListaVideos listaVideos : usuario.getMisListas())
			adaptadorLV.borrarListaVideos(listaVideos);
		adaptadorLV.borrarListaVideos(usuario.getRecentVideo());
		Entidad entidadUsu = server.recuperarEntidad(usuario.getId());
		server.borrarEntidad(entidadUsu);
		
	}

	@Override
	public void modificarUsuario(Usuario usuario) {
		Entidad entidadUsu = server.recuperarEntidad(usuario.getId());
		server.eliminarPropiedadEntidad(entidadUsu, "filtro");
		server.anadirPropiedadEntidad(entidadUsu, "filtro", usuario.getFiltro().getClass().getSimpleName());
		server.eliminarPropiedadEntidad(entidadUsu, "premium");
		server.anadirPropiedadEntidad(entidadUsu, "premium", String.valueOf(usuario.isPremium()));
		server.eliminarPropiedadEntidad(entidadUsu, "login");
		server.anadirPropiedadEntidad(entidadUsu, "login", usuario.getLogin());
		server.eliminarPropiedadEntidad(entidadUsu, "password");
		server.anadirPropiedadEntidad(entidadUsu, "password", usuario.getPassword());
		server.eliminarPropiedadEntidad(entidadUsu, "nombre");
		server.anadirPropiedadEntidad(entidadUsu, "nombre", usuario.getNombre());
		server.eliminarPropiedadEntidad(entidadUsu, "apellidos");
		server.anadirPropiedadEntidad(entidadUsu, "apellidos", usuario.getApellidos());
		server.eliminarPropiedadEntidad(entidadUsu, "fechaNac");
		server.anadirPropiedadEntidad(entidadUsu, "fechaNac", dateFormat.format(usuario.getFechaNac()));
		server.eliminarPropiedadEntidad(entidadUsu, "email");
		server.anadirPropiedadEntidad(entidadUsu, "email", usuario.getEmail());
		server.eliminarPropiedadEntidad(entidadUsu, "recentVideo");
		server.anadirPropiedadEntidad(entidadUsu, "recentVideo", String.valueOf(usuario.getRecentVideo().getId()));
		server.eliminarPropiedadEntidad(entidadUsu, "misListas");
		server.anadirPropiedadEntidad(entidadUsu, "misListas", concatenarIDListaVideos(usuario.getMisListas()));
	}

	@Override
	public Usuario recuperarUsuario(int codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> recuperarTodosUsuarios() {
		List<Usuario> usuarios = new LinkedList<Usuario>();
		List<Entidad> eUsuarios = server.recuperarEntidades("usuario");
		for (Entidad entidadUsu : eUsuarios)
			usuarios.add(recuperarUsuario(entidadUsu.getId()));
		return usuarios;
	}

	public static AdaptadorListaVideosTDS getInstancia() {
		return instancia;
	}

	public static void setInstancia(AdaptadorListaVideosTDS instancia) {
		AdaptadorUsuarioTDS.instancia = instancia;
	}
	
	
	
	
	

}
