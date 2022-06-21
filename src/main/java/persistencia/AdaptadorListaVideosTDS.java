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
	
	private String concatenarIDVideos(List<Video> listaLV) {
		String idsLV = "";
		for (Video lv : listaLV)
			idsLV += lv.getId() + " ";
		return idsLV;
	}

	private List<Video> obtenerVideosDesdeID(String id) {
		List<Video> listaVideos = new LinkedList<Video>();
		StringTokenizer strTok = new StringTokenizer(id, " ");
		AdaptadorVideoTDS adaptadorVideo = AdaptadorVideoTDS.getInstancia();
		while (strTok.hasMoreTokens()) {
			int idVideo = Integer.valueOf((String) strTok.nextElement());
			listaVideos.add(adaptadorVideo.consultarVideo(idVideo));
		}
		return listaVideos;
	}

	@Override
	public void insertarListaVideos(ListaVideos listaVideos) {
		Entidad entidadLV = null;
		if (server.recuperarEntidad(listaVideos.getId()) != null)
			return;

		entidadLV = new Entidad();
		AdaptadorVideoTDS adaptadorVideo = AdaptadorVideoTDS.getInstancia();
		for (Video video : listaVideos.getVideos())
			adaptadorVideo.insertarVideo(video);
		entidadLV = new Entidad();
		entidadLV.setNombre("listaVideos");
		entidadLV
				.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("nombre", listaVideos.getNombre()),
						new Propiedad("videos", concatenarIDVideos(listaVideos.getVideos())))));

		entidadLV = server.registrarEntidad(entidadLV);
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
		server.eliminarPropiedadEntidad(entidadLV, "numVideos");
		server.anadirPropiedadEntidad(entidadLV, "numVideos", String.valueOf(listaVideos.getNumVideos()));
		server.eliminarPropiedadEntidad(entidadLV, "videos");
		server.anadirPropiedadEntidad(entidadLV, "videos", concatenarIDVideos(listaVideos.getVideos()));

		// Escalable por si tiene más propiedades
		for (Propiedad prop : entidadLV.getPropiedades()) {
			switch (prop.getNombre()) {
			case "nombre":
				prop.setValor(listaVideos.getNombre());
				break;
			case "numVideos":
				prop.setValor(String.valueOf(listaVideos.getNumVideos()));
				break;
			case "videos":
				prop.setValor(concatenarIDVideos(listaVideos.getVideos()));
				break;
			case "usuario":
				prop.setValor(String.valueOf(listaVideos.getUsuarioId()));
				break;
			default:
				break;
			}
		}
	}

	@Override
	public ListaVideos consultarListaVideos(int id) {
		
		if (PoolDAO.getUnicaInstancia().contains(id))
			return (ListaVideos) PoolDAO.getUnicaInstancia().getObject(id);
		
		Entidad entidadLV = server.recuperarEntidad(id);
		String nombre = server.recuperarPropiedadEntidad(entidadLV, "nombre");
		ListaVideos listaVideos = new ListaVideos(nombre);
		listaVideos.setId(id);
		
		PoolDAO.getUnicaInstancia().addObject(id, listaVideos);
		
		List<Video> videos = obtenerVideosDesdeID(server.recuperarPropiedadEntidad(entidadLV, "videos"));
		for (Video video : videos)
			listaVideos.addLastVideo(video);
		
		return listaVideos;
	}

	@Override
	public List<ListaVideos> listarTodasListasVideos() {
		List<ListaVideos> listasVideos = new LinkedList<ListaVideos>();
		List<Entidad> entidadLV = server.recuperarEntidades("listaVideos");
		for (Entidad eListaVideos : entidadLV)
			listasVideos.add(consultarListaVideos(eListaVideos.getId()));
		return listasVideos;
	}

}
