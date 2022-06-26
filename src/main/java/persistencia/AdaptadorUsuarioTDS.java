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
		if (server.recuperarEntidad(usuario.getId()) != null)
			return;

		AdaptadorListaVideosTDS adaptadorLV = AdaptadorListaVideosTDS.getInstancia();
		for (ListaVideos listaVideos : usuario.getMisListas())
			adaptadorLV.insertarListaVideos(listaVideos);
		adaptadorLV.insertarListaVideos(usuario.getRecentVideo());

		Entidad entidadU = new Entidad();
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
		for (Propiedad prop : entidadU.getPropiedades()) {
			if (prop.getNombre().equals("filtro")) {
				prop.setValor(usuario.getFiltro().getClass().getSimpleName());
			} else if (prop.getNombre().equals("premium")) {
				prop.setValor(String.valueOf(usuario.isPremium()));
			} else if (prop.getNombre().equals("login")) {
				prop.setValor(usuario.getLogin());
			} else if (prop.getNombre().equals("password")) {
				prop.setValor(usuario.getPassword());
			} else if (prop.getNombre().equals("nombre")) {
				prop.setValor(usuario.getNombre());
			} else if (prop.getNombre().equals("apellidos")) {
				prop.setValor(usuario.getApellidos());
			} else if (prop.getNombre().equals("fechaNac")) {
				prop.setValor(dateFormat.format(usuario.getFechaNac()));
			} else if (prop.getNombre().equals("email")) {
				prop.setValor(usuario.getEmail());
			} else if (prop.getNombre().equals("recentVideo")) {
				prop.setValor(String.valueOf(usuario.getRecentVideo().getId()));
			} else if (prop.getNombre().equals("misListas")) {
				prop.setValor(concatenarIDsListasVideos(usuario.getMisListas()));
			}
			server.modificarPropiedad(prop);
		}
	}

	@Override
	public Usuario consultarUsuario(int id) {
		PoolDAO pool = PoolDAO.getInstancia();
		if (pool.contains(id))
			return (Usuario) pool.getObject(id);

		Entidad entidadU = server.recuperarEntidad(id);
		CatalogoFiltros catalogo = CatalogoFiltros.getInstancia();
		Filtro filtro = catalogo.getFiltro(server.recuperarPropiedadEntidad(entidadU, "filtro"));
		boolean premium = Boolean.parseBoolean(server.recuperarPropiedadEntidad(entidadU, "premium"));
		String login = server.recuperarPropiedadEntidad(entidadU, "login");
		String password = server.recuperarPropiedadEntidad(entidadU, "password");
		String nombre = server.recuperarPropiedadEntidad(entidadU, "nombre");
		String apellidos = server.recuperarPropiedadEntidad(entidadU, "apellidos");
		Date fechaNac = null;
		try {
			fechaNac = dateFormat.parse(server.recuperarPropiedadEntidad(entidadU, "fechaNac"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String email = server.recuperarPropiedadEntidad(entidadU, "email");
		
		Usuario usuario = new Usuario(login, password, nombre, apellidos, fechaNac, email);
		usuario.setId(id);
		usuario.setFiltro(filtro);
		usuario.setPremium(premium);
		
		pool.addObject(id, usuario);
		
		AdaptadorListaVideosTDS adaptadorLV = AdaptadorListaVideosTDS.getInstancia();
		int idLV = Integer.valueOf(server.recuperarPropiedadEntidad(entidadU, "recentVideo"));
		ListaVideos recentVideo = adaptadorLV.consultarListaVideos(idLV);
		List<ListaVideos> misListas = obtenerListasVideos(server.recuperarPropiedadEntidad(entidadU, "misListas"));
		
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
