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
import modelo.CatalogoFiltros;
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
		return instancia;
	}

	private ServicioPersistencia server;
	private SimpleDateFormat dateFormat;
	
	private AdaptadorUsuarioTDS() {
		server = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}

	private String concatenarIDsListasVideos(List<ListaVideos> listaLV) {
		String idsLV = "";
		for (ListaVideos lv : listaLV)
			idsLV += lv.getId() + " ";
		return idsLV.trim();
	}
	private List<ListaVideos> obtenerListasVideos(String idsLV) {
		List<ListaVideos> listaLV = new LinkedList<ListaVideos>();
		StringTokenizer strTok = new StringTokenizer(idsLV, " ");
		AdaptadorListaVideosTDS adaptadorLV = AdaptadorListaVideosTDS.getInstancia();
		while (strTok.hasMoreTokens()) {
			int idLV = Integer.valueOf((String) strTok.nextElement());
			listaLV.add(adaptadorLV.consultarListaVideos(idLV));
		}
		return listaLV;
	}

	@Override
	public void insertarUsuario(Usuario usuario) {
		Entidad entidadU = null;
		boolean existe = true;
		try {
			entidadU = server.recuperarEntidad(usuario.getId());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe)
			return;

		AdaptadorListaVideosTDS adaptadorLV = AdaptadorListaVideosTDS.getInstancia();
		for (ListaVideos listaVideos : usuario.getMisListas())
			adaptadorLV.insertarListaVideos(listaVideos);
		adaptadorLV.insertarListaVideos(usuario.getRecentVideo());

		entidadU = new Entidad();
		entidadU.setNombre("usuario");
		entidadU.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad("filtro", usuario.getFiltro().getClass().getSimpleName()),
				new Propiedad("premium", String.valueOf(usuario.isPremium())),
				new Propiedad("login", usuario.getLogin()),
				new Propiedad("password", usuario.getPassword()),
				new Propiedad("nombre", usuario.getNombre()),
				new Propiedad("apellidos", usuario.getApellidos()),
				new Propiedad("fechaNac", dateFormat.format(usuario.getFechaNac())),
				new Propiedad("email", usuario.getEmail()),
				new Propiedad("recentVideo", String.valueOf(usuario.getRecentVideo().getId())),
				new Propiedad("misListas", concatenarIDsListasVideos(usuario.getMisListas())))));

		entidadU = server.registrarEntidad(entidadU);

		usuario.setId(entidadU.getId());
	}

	@Override
	public void borrarUsuario(Usuario usuario) {
		AdaptadorListaVideosTDS adaptadorLV = AdaptadorListaVideosTDS.getInstancia();
		for (ListaVideos listaVideos : usuario.getMisListas())
			adaptadorLV.borrarListaVideos(listaVideos);
		adaptadorLV.borrarListaVideos(usuario.getRecentVideo());
		Entidad entidadU = server.recuperarEntidad(usuario.getId());
		server.borrarEntidad(entidadU);

	}

	@Override
	public void modificarUsuario(Usuario usuario) {
		Entidad entidadU = server.recuperarEntidad(usuario.getId());
		server.eliminarPropiedadEntidad(entidadU, "filtro");
		server.anadirPropiedadEntidad(entidadU, "filtro", usuario.getFiltro().getClass().getSimpleName());
		server.eliminarPropiedadEntidad(entidadU, "premium");
		server.anadirPropiedadEntidad(entidadU, "premium", String.valueOf(usuario.isPremium()));
		server.eliminarPropiedadEntidad(entidadU, "login");
		server.anadirPropiedadEntidad(entidadU, "login", usuario.getLogin());
		server.eliminarPropiedadEntidad(entidadU, "password");
		server.anadirPropiedadEntidad(entidadU, "password", usuario.getPassword());
		server.eliminarPropiedadEntidad(entidadU, "nombre");
		server.anadirPropiedadEntidad(entidadU, "nombre", usuario.getNombre());
		server.eliminarPropiedadEntidad(entidadU, "apellidos");
		server.anadirPropiedadEntidad(entidadU, "apellidos", usuario.getApellidos());
		server.eliminarPropiedadEntidad(entidadU, "fechaNac");
		server.anadirPropiedadEntidad(entidadU, "fechaNac", dateFormat.format(usuario.getFechaNac()));
		server.eliminarPropiedadEntidad(entidadU, "email");
		server.anadirPropiedadEntidad(entidadU, "email", usuario.getEmail());
		server.eliminarPropiedadEntidad(entidadU, "recentVideo");
		server.anadirPropiedadEntidad(entidadU, "recentVideo", String.valueOf(usuario.getRecentVideo().getId()));
		server.eliminarPropiedadEntidad(entidadU, "misListas");
		server.anadirPropiedadEntidad(entidadU, "misListas", concatenarIDsListasVideos(usuario.getMisListas()));
	}

	@Override
	public Usuario consultarUsuario(int id) {
		PoolDAO pool = PoolDAO.getInstancia();
		if (pool.contains(id))
			return (Usuario) pool.getObject(id);

		Entidad entidadUsu = server.recuperarEntidad(id);
		CatalogoFiltros catalogo = CatalogoFiltros.getInstancia();
		Filtro filtro = catalogo.getFiltro(server.recuperarPropiedadEntidad(entidadUsu, "filtro"));
		boolean premium = Boolean.parseBoolean(server.recuperarPropiedadEntidad(entidadUsu, "premium"));
		String login = server.recuperarPropiedadEntidad(entidadUsu, "login");
		String password = server.recuperarPropiedadEntidad(entidadUsu, "password");
		String nombre = server.recuperarPropiedadEntidad(entidadUsu, "nombre");
		String apellidos = server.recuperarPropiedadEntidad(entidadUsu, "apellidos");
		Date fechaNac = null;
		try {
			fechaNac = dateFormat.parse(server.recuperarPropiedadEntidad(entidadUsu, "fechaNac"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String email = server.recuperarPropiedadEntidad(entidadUsu, "email");
		
		Usuario usuario = new Usuario(login, password, nombre, apellidos, fechaNac, email);
		usuario.setId(id);
		usuario.setFiltro(filtro);
		usuario.setPremium(premium);
		
		pool.addObject(id, usuario);
		
		AdaptadorListaVideosTDS adaptadorLV = AdaptadorListaVideosTDS.getInstancia();
		int idLV = Integer.valueOf(server.recuperarPropiedadEntidad(entidadUsu, "recentVideo"));
		ListaVideos recentVideo = adaptadorLV.consultarListaVideos(idLV);
		List<ListaVideos> misListas = obtenerListasVideos(server.recuperarPropiedadEntidad(entidadUsu, "misListas"));
		
		usuario.setRecentVideo(recentVideo);
		for (ListaVideos listaViedos : misListas)
			usuario.addMiLista(listaViedos);

		return usuario;
	}

	@Override
	public List<Usuario> listarTodosUsuarios() {
		List<Usuario> listaU = new LinkedList<Usuario>();
		List<Entidad> entidadesU = server.recuperarEntidades("usuario");
		for (Entidad entidadU : entidadesU)
			listaU.add(consultarUsuario(entidadU.getId()));
		return listaU;
	}

}
