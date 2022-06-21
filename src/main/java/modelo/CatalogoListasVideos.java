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

	private Map<Integer, ListaVideos> listasVideos;

	private FactoriaDAO factoria;
	private AdaptadorListaVideosDAO adaptadorLsitaVideos;

	private CatalogoListasVideos() {
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.TDS_DAO);
			adaptadorLsitaVideos = factoria.getListaVideosDAO();
			listasVideos = new HashMap<Integer, ListaVideos>();
			this.cargarCatalogo();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<ListaVideos> getListasVideos() {
		List<ListaVideos> lista = new ArrayList<ListaVideos>();
		for (ListaVideos lv : listasVideos.values())
			lista.add(lv);
		return lista;
	}

	public ListaVideos getListaVideos(int id) {
		return listasVideos.get(id);
	}

	public void addListaVideos(ListaVideos lv) {
		listasVideos.put(lv.getId(), lv);
	}

	public void removeListaVideos(ListaVideos lv) {
		listasVideos.remove(lv.getId());
	}

	private void cargarCatalogo() {
		List<ListaVideos> listasVideosBD = adaptadorLsitaVideos.listarTodasListasVideos();
		for (ListaVideos lv : listasVideosBD) {
			listasVideos.put(lv.getId(), lv);
		}
	}
	
}
