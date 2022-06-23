package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatalogoFiltros {
	
	private static CatalogoFiltros instancia = new CatalogoFiltros();
	public static CatalogoFiltros getInstancia() {
		return instancia;
	}
	
	private Map<String,Filtro> mapaPorNombre;
	
	
	private CatalogoFiltros() {
		mapaPorNombre = new HashMap<String,Filtro>();
		cargarCatalogo();
	}
	private void cargarCatalogo() {
		mapaPorNombre.put(NoFiltro.class.getSimpleName(), NoFiltro.getInstancia());
		mapaPorNombre.put(FiltroMisListas.class.getSimpleName(), FiltroMisListas.getInstancia());
		mapaPorNombre.put(FiltroMenores.class.getSimpleName(), FiltroMenores.getInstancia());
		mapaPorNombre.put(FiltroImpopulares.class.getSimpleName(), FiltroImpopulares.getInstancia());
		mapaPorNombre.put(FiltroNombresLargos.class.getSimpleName(), FiltroNombresLargos.getInstancia());
	}
	
	public boolean existsFiltro(String nombre) {
		return mapaPorNombre.containsKey(nombre);
	}
	public Filtro getFiltro(String nombre) {
		return mapaPorNombre.get(nombre); 
	}
	
	public List<String> getFiltros() {
		return new ArrayList<String>(mapaPorNombre.keySet());
	}

}
