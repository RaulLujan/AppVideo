package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import modelo.ListaVideos;
import modelo.Video;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorListaVideosTDS implements AdaptadorListaVideosDAO {

	private static AdaptadorListaVideosTDS instancia = null;
	public static AdaptadorListaVideosTDS getInstancia() {
		if (instancia == null)
			instancia = new AdaptadorListaVideosTDS();
		return instancia;
	}
	
	private ServicioPersistencia server;

	private AdaptadorListaVideosTDS() {
		server = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	private String concatenarIDsVideos(List<Video> listaV) {
		String idsV = "";
		for (Video v : listaV)
			idsV += v.getId() + " ";
		return idsV.trim();
	}
	private List<Video> obtenerVideos(String idsV) {
		List<Video> listaV = new LinkedList<Video>();
		StringTokenizer strTok = new StringTokenizer(idsV, " ");
		AdaptadorVideoTDS adaptadorV = AdaptadorVideoTDS.getInstancia();
		while (strTok.hasMoreTokens()) {
			int idV = Integer.valueOf((String) strTok.nextElement());
			listaV.add(adaptadorV.consultarVideo(idV));
		}
		return listaV;
	}

	@Override
	public void insertarListaVideos(ListaVideos listaVideos) {
		Entidad entidadLV = null;
		boolean existe = true; 
		try {
			entidadLV = server.recuperarEntidad(listaVideos.getId());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe)
			return;
		System.out.println(existe);
		System.out.println(listaVideos.getNumVideos());

		AdaptadorVideoTDS adaptadorV = AdaptadorVideoTDS.getInstancia();
		for (Video video : listaVideos.getVideos())
			adaptadorV.insertarVideo(video);
		
		System.out.println(entidadLV);
		entidadLV = new Entidad();
		System.out.println(entidadLV);
		entidadLV.setNombre("listaVideos");
		entidadLV.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad("nombre", listaVideos.getNombre()),
				new Propiedad("videos", concatenarIDsVideos(listaVideos.getVideos())))));
		System.out.println(entidadLV);

		entidadLV = server.registrarEntidad(entidadLV);
		System.out.println(entidadLV);
		
		listaVideos.setId(entidadLV.getId());

	}

	@Override
	public void borrarListaVideos(ListaVideos listaVideos) {
		Entidad entidadLV = server.recuperarEntidad(listaVideos.getId());
		server.borrarEntidad(entidadLV);

	}

	@Override
	public void modificarListaVideos(ListaVideos listaVideos) {
		Entidad entidadLV = server.recuperarEntidad(listaVideos.getId());
		server.eliminarPropiedadEntidad(entidadLV, "nombre");
		server.anadirPropiedadEntidad(entidadLV, "nombre", listaVideos.getNombre());
		server.eliminarPropiedadEntidad(entidadLV, "videos");
		server.anadirPropiedadEntidad(entidadLV, "videos", concatenarIDsVideos(listaVideos.getVideos()));
	}

	@Override
	public ListaVideos consultarListaVideos(int id) {
		PoolDAO pool = PoolDAO.getInstancia();
		if (pool.contains(id))
			return (ListaVideos) pool.getObject(id);
		
		Entidad entidadLV = server.recuperarEntidad(id);
		String nombre = server.recuperarPropiedadEntidad(entidadLV, "nombre");
		
		ListaVideos listaVideos = new ListaVideos(nombre);
		listaVideos.setId(id);
		
		pool.addObject(id, listaVideos);
		
		List<Video> videos = obtenerVideos(server.recuperarPropiedadEntidad(entidadLV, "videos"));
		for (Video video : videos)
			listaVideos.addLastVideo(video);
		
		return listaVideos;
	}

	@Override
	public List<ListaVideos> listarTodasListasVideos() {
		List<ListaVideos> listaLV = new LinkedList<ListaVideos>();
		List<Entidad> entidadesLV = server.recuperarEntidades("listaVideos");
		for (Entidad entidadLV : entidadesLV)
			listaLV.add(consultarListaVideos(entidadLV.getId()));
		return listaLV;
	}

}
