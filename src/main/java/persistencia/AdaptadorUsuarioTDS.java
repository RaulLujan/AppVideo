package persistencia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import modelo.Filtro;
import modelo.ListaVideos;
import modelo.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorUsuarioTDS implements AdaptadorUsuarioDAO {

	private static AdaptadorUsuarioTDS instancia = null;
	public static AdaptadorUsuarioTDS getInstancia() {
		if (instancia == null)
			return new AdaptadorUsuarioTDS();
		else
			return instancia;
	}

	private ServicioPersistencia server;
	private SimpleDateFormat dateFormat;
	
	private AdaptadorUsuarioTDS() {
		server = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}

	private String concatenarIDListaVideos(List<ListaVideos> listaLV) {
		String idsLV = "";
		for (ListaVideos lv : listaLV)
			idsLV += lv.getId() + " ";
		return idsLV;
	}

	private List<ListaVideos> obtenerListasVideosDesdeID(String id) {
		List<ListaVideos> listaLV = new LinkedList<ListaVideos>();
		StringTokenizer strTok = new StringTokenizer(id, " ");
		AdaptadorListaVideosTDS adaptadorLV = AdaptadorListaVideosTDS.getInstancia();
		while (strTok.hasMoreTokens()) {
			int idLV = Integer.valueOf((String) strTok.nextElement());
			listaLV.add(adaptadorLV.consultarListaVideos(idLV));
		}
		return listaLV;
	}

	@Override
	public void insertarUsuario(Usuario usuario) {
		Entidad entidadUsu = null;
		if (server.recuperarEntidad(usuario.getId()) != null)
			return;

		AdaptadorListaVideosTDS adaptadorLV = AdaptadorListaVideosTDS.getInstancia();
		for (ListaVideos listaVideos : usuario.getMisListas())
			adaptadorLV.insertarListaVideos(listaVideos);
		adaptadorLV.insertarListaVideos(usuario.getRecentVideo());

		entidadUsu = new Entidad();
		entidadUsu.setNombre("usuario");
		entidadUsu.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad("filtro", usuario.getFiltro().getClass().getSimpleName()),
				new Propiedad("premium", String.valueOf(usuario.isPremium())),
				new Propiedad("login", usuario.getLogin()), new Propiedad("password", usuario.getPassword()),
				new Propiedad("nombre", usuario.getNombre()), new Propiedad("apellidos", usuario.getApellidos()),
				new Propiedad("fechaNac", dateFormat.format(usuario.getFechaNac())),
				new Propiedad("email", usuario.getEmail()),
				new Propiedad("recentVideo", String.valueOf(usuario.getRecentVideo().getId())),
				new Propiedad("misListas", concatenarIDListaVideos(usuario.getMisListas())))));

		entidadUsu = server.registrarEntidad(entidadUsu);

		usuario.setId(entidadUsu.getId());
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

		// Escalable por si tiene mas propiedades
		for (Propiedad prop : entidadUsu.getPropiedades()) {
			switch (prop.getNombre()) {
			case "filtro":
				prop.setValor(usuario.getFiltro().getClass().getSimpleName());
				break;
			case "premium":
				prop.setValor(String.valueOf(usuario.isPremium()));
				break;
			case "login":
				prop.setValor(usuario.getLogin());
				break;
			case "password":
				prop.setValor(usuario.getPassword());
				break;
			case "nombre":
				prop.setValor(usuario.getNombre());
				break;
			case "apellidos":
				prop.setValor(usuario.getApellidos());
				break;
			case "email":
				prop.setValor(usuario.getEmail());
				break;
			case "recentVideo":
				prop.setValor(String.valueOf(usuario.getRecentVideo().getId()));
				break;
			case "misListas":
				prop.setValor(concatenarIDListaVideos(usuario.getMisListas()));
				break;
			case "fechaNac":
				prop.setValor(dateFormat.format(usuario.getFechaNac()));
				break;
			default:
				break;
			}
		}
	}

	@Override
	public Usuario consultarUsuario(int id) {

		if (PoolDAO.getUnicaInstancia().contains(id))
			return (Usuario) PoolDAO.getUnicaInstancia().getObject(id);

		// Falta tratamiento del POOL
		Entidad entidadUsu = server.recuperarEntidad(id);
		// Obtener filtro a partir de id XXXX FALTA XXXXX
		/* Filtro */ String Filtro = server.recuperarPropiedadEntidad(entidadUsu, "filtro");
		Filtro filtroX = null;
		boolean premium = Boolean.parseBoolean(server.recuperarPropiedadEntidad(entidadUsu, "premium"));
		String login = server.recuperarPropiedadEntidad(entidadUsu, "login");
		String password = server.recuperarPropiedadEntidad(entidadUsu, "password");
		String nombre = server.recuperarPropiedadEntidad(entidadUsu, "nombre");
		String apellidos = server.recuperarPropiedadEntidad(entidadUsu, "apellidos");
		// Mejorar uso de fecha
		Date fechaNac = new Date();
		try {
			fechaNac = dateFormat.parse(server.recuperarPropiedadEntidad(entidadUsu, "fechaNac"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String email = server.recuperarPropiedadEntidad(entidadUsu, "email");
		Usuario usuario = new Usuario(login, password, nombre, apellidos, fechaNac, email);
		usuario.setId(id);
		usuario.setFiltro(filtroX);
		usuario.setPremium(premium);
		int idLV = Integer.valueOf(server.recuperarPropiedadEntidad(entidadUsu, "recentVideo"));
		AdaptadorListaVideosTDS adaptadorLV = AdaptadorListaVideosTDS.getInstancia();
		ListaVideos recentVideo = adaptadorLV.consultarListaVideos(idLV);
		List<ListaVideos> misListas = obtenerListasVideosDesdeID(
				server.recuperarPropiedadEntidad(entidadUsu, "misListas"));
		usuario.setRecentVideo(recentVideo);

		PoolDAO.getUnicaInstancia().addObject(id, usuario);

		for (ListaVideos listaViedos : misListas)
			usuario.addMiLista(listaViedos);

		return usuario;
	}

	@Override
	public List<Usuario> listarTodosUsuarios() {
		List<Usuario> usuarios = new LinkedList<Usuario>();
		List<Entidad> eUsuarios = server.recuperarEntidades("usuario");
		for (Entidad entidadUsu : eUsuarios)
			usuarios.add(consultarUsuario(entidadUsu.getId()));
		return usuarios;
	}

}
