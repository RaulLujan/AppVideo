package persistencia;

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
	public static AdaptadorVideoTDS getInstancia() {
		if (instancia == null)
			return new AdaptadorVideoTDS();
		return instancia;
	}

	private ServicioPersistencia server;

	private AdaptadorVideoTDS() {
		server = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	private String concatenarIDsEtiquetas(List<Etiqueta> listaE) {
		String idsE = "";
		for (Etiqueta e : listaE)
			idsE += e.getId() + " ";
		return idsE.trim();
	}
	private List<Etiqueta> obtenerEtiquetas(String idsE) {
		List<Etiqueta> listaE = new LinkedList<Etiqueta>();
		StringTokenizer strTok = new StringTokenizer(idsE, " ");
		AdaptadorEtiquetaTDS adaptadorE = AdaptadorEtiquetaTDS.getInstancia();
		while (strTok.hasMoreTokens()) {
			int idE = Integer.valueOf((String) strTok.nextElement());
			listaE.add(adaptadorE.consultarEtiqueta(idE));
		}
		return listaE;
	}

	@Override
	public void insertarVideo(Video video) {
		Entidad entidadV = null;
		boolean existe = true; 
		try {
			entidadV = server.recuperarEntidad(video.getId());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe)
			return;
		
		entidadV = new Entidad();
		entidadV.setNombre("video");
		entidadV.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad("url", video.getUrl()),
				new Propiedad("titulo", video.getTitulo()),
				new Propiedad("numRepro", String.valueOf(video.getNumRepro())),
				new Propiedad("etiquetas", concatenarIDsEtiquetas(video.getEtiquetas())))));

		entidadV = server.registrarEntidad(entidadV);
		
		video.setId(entidadV.getId());
	}

	@Override
	public void borrarVideo(Video video) {
		Entidad entidadV = server.recuperarEntidad(video.getId());
		server.borrarEntidad(entidadV);
	}

	@Override
	public void modificarVideo(Video video) {
		Entidad entidadV = server.recuperarEntidad(video.getId());
		server.eliminarPropiedadEntidad(entidadV, "url");
		server.anadirPropiedadEntidad(entidadV, "url", video.getUrl());
		server.eliminarPropiedadEntidad(entidadV, "titulo");
		server.anadirPropiedadEntidad(entidadV, "titulo", video.getTitulo());
		server.eliminarPropiedadEntidad(entidadV, "numRepro");
		server.anadirPropiedadEntidad(entidadV, "numRepro", String.valueOf(video.getNumRepro()));
		server.eliminarPropiedadEntidad(entidadV, "etiquetas");
		server.anadirPropiedadEntidad(entidadV, "etiquetas", concatenarIDsEtiquetas(video.getEtiquetas()));
	}

	@Override
	public Video consultarVideo(int id) {
		PoolDAO pool = PoolDAO.getInstancia();
		if (pool.contains(id))
			return (Video) pool.getObject(id);
		
		Entidad entidadV = server.recuperarEntidad(id);
		String url = server.recuperarPropiedadEntidad(entidadV, "url");
		String titulo = server.recuperarPropiedadEntidad(entidadV, "titulo");
		int numRepro = Integer.parseInt(server.recuperarPropiedadEntidad(entidadV, "numRepro"));
		
		Video video = new Video(url, titulo);
		video.setId(id);
		video.setNumRepro(numRepro);

		pool.addObject(id, video);
		
		List<Etiqueta> etiquetas = obtenerEtiquetas(server.recuperarPropiedadEntidad(entidadV, "etiquetas"));
		for (Etiqueta etiqueta : etiquetas)
			video.addEtiqueta(etiqueta);
		
		return video;
	}

	@Override
	public List<Video> listarTodosVideos() {
		List<Video> listaV = new LinkedList<Video>();
		List<Entidad> entidadesV = server.recuperarEntidades("video");
		for (Entidad entidadV : entidadesV)
			listaV.add(consultarVideo(entidadV.getId()));
		return listaV;
	}

}
