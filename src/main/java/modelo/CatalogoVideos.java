package modelo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import persistencia.AdaptadorVideoDAO;
import persistencia.FactoriaDAO;

public class CatalogoVideos {
	private static final int TOP_VIDEOS = 10;

	private Map<Integer, Video> videos;
	private static CatalogoVideos instancia = new CatalogoVideos();

	private FactoriaDAO factoria;
	private AdaptadorVideoDAO adaptadorVideo;

	private CatalogoVideos() {
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.TDS_DAO);
			adaptadorVideo = factoria.getVideoDAO();
			videos = new HashMap<Integer, Video>();
			this.cargarCatalogo();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static CatalogoVideos getUnicaInstancia() {
		return instancia;
	}

	public List<Video> getVideos() {
		List<Video> lista = new ArrayList<Video>();
		for (Video v : videos.values())
			lista.add(v);
		return lista;
	}

	public Video getVideo(int id) {
		return videos.get(id);
	}

	public void addVideo(Video v) {
		videos.put(v.getId(), v);
	}

	public void removeVideo(Video v) {
		videos.remove(v.getId());
	}

	private void cargarCatalogo() {
		List<Video> videosBD = adaptadorVideo.listarTodosVideos();
		for (Video v : videosBD) {
			videos.put(v.getId(), v);
		}
	}

	public List<Video> consultarVideosPorPalabra(String busqueda) {
		return videos.values().stream().filter(v -> v.getTitulo().contains(busqueda)).collect(Collectors.toList());
	}

	public List<Video> consultarVideosPorEtiqueta(String etiqueta) {
		return videos.values().stream().filter(v -> v.containsEtiqueta(etiqueta)).collect(Collectors.toList());
	}

	// TODO FILTRO no está relacionado con nada.... queda pendiente esta busqueda
	public List<Video> consultarVideosPorFiltro(String filtro) {
		return videos.values().stream().filter(v -> v.containsEtiqueta(filtro)).collect(Collectors.toList());
	}

	public List<Video> getTopVideos(int numTop) {
		return videos.values().stream().sorted(Comparator.comparing(Video::getNumReproducciones).reversed())
				.limit(numTop).collect(Collectors.toList());
	}

	public List<Video> getTopVideos() {
		return getTopVideos(TOP_VIDEOS);
	}

	public boolean existeVideo(int id) {
		return videos.containsKey(id);
	}

	public boolean existeVideo(String url) {
		for (Video video : videos.values())
			if (video.getUrl().equals(url))
				return true;
		return false;
	}
}
