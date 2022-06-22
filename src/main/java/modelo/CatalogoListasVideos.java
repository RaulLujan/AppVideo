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

	private Map<Integer, ListaVideos> mapa;

	private AdaptadorListaVideosDAO adaptador;

	private CatalogoListasVideos() {
		try {
			FactoriaDAO factoria = FactoriaDAO.getInstancia(FactoriaDAO.TDS_DAO);
			adaptador = factoria.getListaVideosDAO();
			mapa = new HashMap<Integer, ListaVideos>();
			this.cargarCatalogo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void cargarCatalogo() {
		List<ListaVideos> lista = adaptador.listarTodasListasVideos();
		for (ListaVideos lv : lista) {
			mapa.put(lv.getId(), lv);
		}
	}

	public void addListaVideos(ListaVideos lv) {
		adaptador.insertarListaVideos(lv);
		mapa.put(lv.getId(), lv);
	}
	public void removeListaVideos(ListaVideos lv) {
		mapa.remove(lv.getId());
		adaptador.borrarListaVideos(lv);
	}

	public ListaVideos getListaVideos(int id) {
		return mapa.get(id);
	}
	
	public List<ListaVideos> getListasVideos() {
		return new ArrayList<ListaVideos>(mapa.values());
	}

	
}
