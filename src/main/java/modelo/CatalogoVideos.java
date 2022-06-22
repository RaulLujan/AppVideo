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
	private static CatalogoVideos instancia = new CatalogoVideos();

	public static CatalogoVideos getInstancia() {
		return instancia;
	}

	private Map<Integer, Video> mapaPorID;

	private AdaptadorVideoDAO adaptador;

	private CatalogoVideos() {
		try {
			FactoriaDAO factoria = FactoriaDAO.getInstancia(FactoriaDAO.TDS_DAO);
			adaptador = factoria.getVideoDAO();
			mapaPorID = new HashMap<Integer, Video>();
			this.cargarCatalogo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void cargarCatalogo() {
		List<Video> lista = adaptador.listarTodosVideos();
		for (Video video : lista)
			mapaPorID.put(video.getId(), video);
	}

	public void addVideo(Video video) {
		adaptador.insertarVideo(video);
		mapaPorID.put(video.getId(), video);
	}

	public void removeVideo(Video video) {
		mapaPorID.remove(video.getId());
		adaptador.borrarVideo(video);
	}

	public boolean existsVideo(String url) {
		for (Video video : mapaPorID.values())
			if (video.getUrl().equals(url))
				return true;
		return false;
	}

	public boolean existsVideo(int id) {
		return mapaPorID.containsKey(id);
	}

	public Video getVideo(int id) {
		return mapaPorID.get(id);
	}

	public List<Video> getVideos() {
		return new ArrayList<Video>(mapaPorID.values());
	}

	public List<Video> getVideosOK(Usuario usuario, String subtitulo, List<Etiqueta> etiquetas) {
		Filtro f1 = new FiltroTitulo(subtitulo);
		Filtro f2 = new FiltroEtiquetas(etiquetas);
		Filtro f3 = usuario.getFiltro();
		return mapaPorID.values().stream().filter(v -> f1.esVideoOK(v) && f2.esVideoOK(v) && f3.esVideoOK(v))
				.collect(Collectors.toList());
	}

	public List<Video> getTopVideos() {
		return getTopVideos(TOP_VIDEOS);
	}

	public List<Video> getTopVideos(int numTop) {
		return mapaPorID.values().stream().sorted(Comparator.comparing(Video::getNumRepro).reversed()).limit(numTop)
				.collect(Collectors.toList());
	}

}
