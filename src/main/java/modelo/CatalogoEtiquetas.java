package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistencia.AdaptadorEtiquetaDAO;
import persistencia.AdaptadorListaVideosDAO;
import persistencia.FactoriaDAO;

public class CatalogoEtiquetas {
	private Map<String, Etiqueta> listasEtiquetas;
	private static CatalogoEtiquetas instancia = new CatalogoEtiquetas();

	private FactoriaDAO factoria;
	private AdaptadorEtiquetaDAO adaptadorEtiquetas;

	private CatalogoEtiquetas() {
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.TDS_DAO);
			adaptadorEtiquetas = factoria.getEtiquetaDAO();
			listasEtiquetas = new HashMap<String, Etiqueta>();
			this.cargarCatalogo();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static CatalogoEtiquetas getUnicaInstancia() {
		return instancia;
	}

	public List<Etiqueta> getListaEtiquetas() {
		List<Etiqueta> lista = new ArrayList<Etiqueta>();
		for (Etiqueta lv : listasEtiquetas.values())
			lista.add(lv);
		return lista;
	}

	public Etiqueta getEtiqueta(String nombre) {
		return listasEtiquetas.get(nombre);
	}

	public void addEtiqueta(Etiqueta e) {
		listasEtiquetas.put(e.getNombre(), e);
	}

	public void removeEtiqueta(Etiqueta lv) {
		listasEtiquetas.remove(lv.getId());
	}

	private void cargarCatalogo() {
		List<Etiqueta> etiquetaBD = adaptadorEtiquetas.listarTodasEtiquetas();
		for (Etiqueta le : etiquetaBD) {
			listasEtiquetas.put(le.getNombre(), le);
		}
	}
	
	public boolean existeEtiqueta(String nombre) {
		return listasEtiquetas.containsKey(nombre);
	}
	
}
