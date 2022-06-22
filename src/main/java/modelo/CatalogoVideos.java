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

	private Map<Integer, Video> mapa;

	private AdaptadorVideoDAO adaptador;

	private CatalogoVideos() {
		try {
			FactoriaDAO factoria = FactoriaDAO.getInstancia(FactoriaDAO.TDS_DAO);
			adaptador = factoria.getVideoDAO();
			mapa = new HashMap<Integer, Video>();
			this.cargarCatalogo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void cargarCatalogo() {
		List<Video> lista = adaptador.listarTodosVideos();
		for (Video video : lista)
			mapa.put(video.getId(), video);
	}

	public void addVideo(Video video) {
		adaptador.insertarVideo(video);
		mapa.put(video.getId(), video);
	}
	public void removeVideo(Video video) {
		mapa.remove(video.getId());
		adaptador.borrarVideo(video);
	}

	public boolean existsVideo(String url) {
		for (Video video : mapa.values())
			if (video.getUrl().equals(url))
				return true;
		return false;
	}
	public boolean existsVideo(int id) {
		return mapa.containsKey(id);
	}
	public Video getVideo(int id) {
		return mapa.get(id);
	}

	public List<Video> getVideos() {
		return new ArrayList<Video>(mapa.values());
//		List<Video> lista = new ArrayList<Video>();
//		for (Video video : mapa.values())
//			lista.add(video);
//		return lista;
	}
	
//	public List<Video> consultarVideosPorTitulo(String busqueda) {
//		return mapa.values().stream()
//				.filter(v -> v.getTitulo().contains(busqueda))
//				.collect(Collectors.toList());
//	}
//
//	public List<Video> consultarVideosPorEtiqueta(String etiqueta) {
//		return mapa.values().stream()
//				.filter(v -> v.containsEtiqueta(etiqueta))
//				.collect(Collectors.toList());
//	}
//
//	// TODO FILTRO no está relacionado con nada.... queda pendiente esta busqueda
//	public List<Video> consultarVideosPorFiltro(String filtro) {
//		return mapa.values().stream()
//				.filter(v -> v.containsEtiqueta(filtro))
//				.collect(Collectors.toList());
//	}
	
	public ListaVideos getVideosOK(Usuario usuario, String subtitulo, List<Etiqueta> etiquetas) {
		Filtro f1 = new FiltroTitulo(subtitulo);
		Filtro f2 = new FiltroEtiquetas(etiquetas);
		Filtro f3 = usuario.getFiltro();
		ListaVideos lista = new ListaVideos();
		for (Video video : mapa.values())
			if (f1.esVideoOK(video)
					&& f2.esVideoOK(video)
					&& f3.esVideoOK(video))
				lista.addLastVideo(video);
		return lista;
	}

	public List<Video> getTopVideos() {
		return mapa.values().stream()
				.sorted(Comparator.comparing(Video::getNumRepro).reversed())
				.limit(TOP_VIDEOS).collect(Collectors.toList());
	}

}
