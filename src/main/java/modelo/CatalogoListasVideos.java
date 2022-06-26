package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistencia.AdaptadorListaVideosDAO;
import persistencia.FactoriaDAO;

public class CatalogoListasVideos {
	
	private static CatalogoListasVideos instancia = new CatalogoListasVideos();
	public static CatalogoListasVideos getInstancia() {
		return instancia;
	}

	private Map<Integer, ListaVideos> mapaPorID;

	private AdaptadorListaVideosDAO adaptador;

	private CatalogoListasVideos() {
		try {
			FactoriaDAO factoria = FactoriaDAO.getInstancia(FactoriaDAO.TDS_DAO);
			adaptador = factoria.getListaVideosDAO();
			mapaPorID = new HashMap<Integer, ListaVideos>();
			cargarCatalogo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void cargarCatalogo() {
		List<ListaVideos> lista = adaptador.listarTodasListasVideos();
		for (ListaVideos lv : lista) {
			mapaPorID.put(lv.getId(), lv);
		}
	}

	public boolean addListaVideos(ListaVideos lv) {
		adaptador.insertarListaVideos(lv);
		mapaPorID.put(lv.getId(), lv);
		return true;
	}
	public boolean removeListaVideos(ListaVideos lv) {
		mapaPorID.remove(lv.getId());
		adaptador.borrarListaVideos(lv);
		return true;
	}

	public boolean existsVideo(int id) {
		return mapaPorID.containsKey(id);
	}
	public ListaVideos getListaVideos(int id) {
		return mapaPorID.get(id);
	}
	
	public List<ListaVideos> getListasVideos() {
		return new ArrayList<ListaVideos>(mapaPorID.values());
	}

	
}
