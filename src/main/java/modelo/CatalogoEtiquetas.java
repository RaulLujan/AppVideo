package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistencia.AdaptadorEtiquetaDAO;
import persistencia.FactoriaDAO;

public class CatalogoEtiquetas {
	
	private static CatalogoEtiquetas instancia = new CatalogoEtiquetas();
	public static CatalogoEtiquetas getInstancia() {
		return instancia;
	}

	private Map<String, Etiqueta> mapaPorNombre;

	private AdaptadorEtiquetaDAO adaptador;

	private CatalogoEtiquetas() {
		try {
			FactoriaDAO factoria = FactoriaDAO.getInstancia(FactoriaDAO.TDS_DAO);
			adaptador = factoria.getEtiquetaDAO();
			mapaPorNombre = new HashMap<String, Etiqueta>();
			this.cargarCatalogo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void cargarCatalogo() {
		List<Etiqueta> lista = adaptador.listarTodasEtiquetas();
		for (Etiqueta etiqueta : lista)
			mapaPorNombre.put(etiqueta.getNombre(), etiqueta);
	}
	
	public void addEtiqueta(Etiqueta etiqueta) {
		adaptador.insertarEtiqueta(etiqueta);
		mapaPorNombre.put(etiqueta.getNombre(), etiqueta);
	}
	public void removeEtiqueta(Etiqueta etiqueta) {
		mapaPorNombre.remove(etiqueta.getNombre());
		adaptador.borrarEtiqueta(etiqueta);
	}
	
	public boolean existsEtiqueta(String nombre) {
		return mapaPorNombre.containsKey(nombre);
	}
	public Etiqueta getEtiqueta(String nombre) {
		return mapaPorNombre.get(nombre);
	}

	public List<Etiqueta> getEtiquetas() {
		return new ArrayList<Etiqueta>(mapaPorNombre.values());
	}
	
}
