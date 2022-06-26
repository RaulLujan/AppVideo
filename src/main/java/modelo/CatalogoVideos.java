package modelo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.*;

import persistencia.AdaptadorVideoDAO;
import persistencia.FactoriaDAO;

public class CatalogoVideos {

	private static final int TOP_VIDEOS = 10;
	private static CatalogoVideos instancia = new CatalogoVideos();
	public static CatalogoVideos getInstancia() {
		return instancia;
	}

	private Map<String, Video> mapaPorURL;

	private AdaptadorVideoDAO adaptador;

	private CatalogoVideos() {
		try {
			FactoriaDAO factoria = FactoriaDAO.getInstancia(FactoriaDAO.TDS_DAO);
			adaptador = factoria.getVideoDAO();
			mapaPorURL = new HashMap<String, Video>();
			cargarCatalogo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void cargarCatalogo() {
		List<Video> lista = adaptador.listarTodosVideos();
		for (Video video : lista)
			mapaPorURL.put(video.getUrl(), video);
	}

	public boolean addVideo(Video video) {
		if (existsVideo(video.getUrl()))
			return false;
		
		adaptador.insertarVideo(video);
		mapaPorURL.put(video.getUrl(), video);
		return true;
	}
	public boolean removeVideo(Video video) {
		if (!existsVideo(video.getUrl()))
			return false;
		
		mapaPorURL.remove(video.getUrl());
		adaptador.borrarVideo(video);
		return true;
	}

	public boolean existsVideo(String url) {
		return mapaPorURL.containsKey(url);
	}
	public Video getVideo(String url) {
		return mapaPorURL.get(url);
	}
	
	public List<Video> getVideos() {
		return new ArrayList<Video>(mapaPorURL.values());
	}
	public List<Video> getVideosOK(Usuario usuario, String subtitulo, List<Etiqueta> etiquetas) {
		Filtro f1 = FiltroTitulo.getInstancia(subtitulo);
		Filtro f2 = FiltroEtiquetas.getInstancia(etiquetas);
		Filtro f3 = usuario.getFiltro();
		return mapaPorURL.values().stream()
				.filter(v -> f1.esVideoOK(v, usuario)
						&& f2.esVideoOK(v, usuario)
						&& f3.esVideoOK(v, usuario))
				.collect(Collectors.toList());
	}
	public List<Video> getTopVideos() {
		return mapaPorURL.values().stream()
				.sorted(Comparator.comparing(Video::getNumRepro).reversed())
				.limit(TOP_VIDEOS)
				.collect(Collectors.toList());
	}

}
