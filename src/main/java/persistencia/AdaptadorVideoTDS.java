package persistencia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import modelo.Etiqueta;
import modelo.Video;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorVideoTDS implements AdaptadorVideoDAO {

	private static AdaptadorVideoTDS instancia = null;
	private ServicioPersistencia server;

	private String concatenarIDListaEtiquetas(List<Etiqueta> listaEtiquetas) {
		String idEtiquetas = "";
		for (Etiqueta e : listaEtiquetas)
			idEtiquetas += e.getId() + " ";
		return idEtiquetas;
	}

	private List<Etiqueta> obtenerEtiquetasDesdeID(String id) {
		List<Etiqueta> listaEtiquetas = new LinkedList<Etiqueta>();
		StringTokenizer strTok = new StringTokenizer(id, " ");
		AdaptadorEtiquetaTDS adaptadorEtiqueta = AdaptadorEtiquetaTDS.getInstancia();
		while (strTok.hasMoreTokens()) {
			int idEtiqueta = Integer.valueOf((String) strTok.nextElement());
			listaEtiquetas.add(adaptadorEtiqueta.consultarEtiqueta(idEtiqueta));
		}
		return listaEtiquetas;
	}

	private AdaptadorVideoTDS() {
		server = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	@Override
	public void insertarVideo(Video video) {
		Entidad entidadVideo = null;
		if (server.recuperarEntidad(video.getId()) != null)
			return;
		entidadVideo = new Entidad();
		entidadVideo.setNombre("video");
		entidadVideo.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad("url", video.getUrl()), new Propiedad("titulo", video.getTitulo()),
						new Propiedad("numReproducciones", String.valueOf(video.getNumReproducciones())),
						new Propiedad("etiquetas", concatenarIDListaEtiquetas(video.getEtiquetas())))));

		entidadVideo = server.registrarEntidad(entidadVideo);
		video.setId(entidadVideo.getId());
	}

	@Override
	public void borrarVideo(Video video) {
		Entidad entidadVideo = server.recuperarEntidad(video.getId());
		server.borrarEntidad(entidadVideo);
	}

	@Override
	public void modificarVideo(Video video) {
		Entidad entidadVideo = server.recuperarEntidad(video.getId());
		server.eliminarPropiedadEntidad(entidadVideo, "url");
		server.anadirPropiedadEntidad(entidadVideo, "url", video.getUrl());
		server.eliminarPropiedadEntidad(entidadVideo, "titulo");
		server.anadirPropiedadEntidad(entidadVideo, "titulo", video.getTitulo());
		server.eliminarPropiedadEntidad(entidadVideo, "numReproducciones");
		server.anadirPropiedadEntidad(entidadVideo, "numReproducciones", String.valueOf(video.getNumReproducciones()));
		server.eliminarPropiedadEntidad(entidadVideo, "etiquetas");
		server.anadirPropiedadEntidad(entidadVideo, "etiquetas", concatenarIDListaEtiquetas(video.getEtiquetas()));

		// Escalable por si tiene más propiedades
		for (Propiedad prop : entidadVideo.getPropiedades()) {
			switch (prop.getNombre()) {
			case "url":
				prop.setValor(video.getUrl());
				break;
			case "titulo":
				prop.setValor(video.getTitulo());
				break;
			case "numReproducciones":
				prop.setValor(String.valueOf(video.getNumReproducciones()));
				break;
			case "etiquetas":
				prop.setValor(concatenarIDListaEtiquetas(video.getEtiquetas()));
				break;
			default:
				break;
			}
		}
	}

	@Override
	public Video consultarVideo(int id) {
		Entidad eVideo = server.recuperarEntidad(id);
		String url = server.recuperarPropiedadEntidad(eVideo, "url");
		String titulo = server.recuperarPropiedadEntidad(eVideo, "titulo");
		int numReproducciones = Integer.parseInt(server.recuperarPropiedadEntidad(eVideo, "numReproducciones"));
		Video video = new Video(url, titulo);
		video.setId(id);
		video.setNumReproducciones(numReproducciones);
		List<Etiqueta> etiquetas = obtenerEtiquetasDesdeID(server.recuperarPropiedadEntidad(eVideo, "etiquetas"));
		for (Etiqueta etiqueta : etiquetas)
			video.addEtiqueta(etiqueta);
		return video;
	}

	@Override
	public List<Video> listarTodosVideos() {
		List<Video> videos = new LinkedList<Video>();
		List<Entidad> entidadVideo = server.recuperarEntidades("video");
		for (Entidad eVideo : entidadVideo)
			videos.add(consultarVideo(eVideo.getId()));
		return videos;
	}

	public static AdaptadorVideoTDS getInstancia() {
		if (instancia == null)
			return new AdaptadorVideoTDS();
		else
			return instancia;
	}

	public static void setInstancia(AdaptadorVideoTDS instancia) {
		AdaptadorVideoTDS.instancia = instancia;
	}

}
