package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import modelo.Etiqueta;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorEtiquetaTDS implements AdaptadorEtiquetaDAO {

	private static AdaptadorEtiquetaTDS instancia = null;
	public static AdaptadorEtiquetaTDS getInstancia() {
		if (instancia == null)
			return new AdaptadorEtiquetaTDS();
		return instancia;
	}
	
	private ServicioPersistencia server;

	private AdaptadorEtiquetaTDS() {
		server = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	@Override
	public void insertarEtiqueta(Etiqueta etiqueta) {
		if (server.recuperarEntidad(etiqueta.getId()) != null)
			return;

		Entidad entidadE = new Entidad();
		entidadE.setNombre("etiqueta");
		entidadE.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad("nombre", etiqueta.getNombre()))));

		entidadE = server.registrarEntidad(entidadE);

		etiqueta.setId(entidadE.getId());
	}

	@Override
	public void borrarEtiqueta(Etiqueta etiqueta) {
		Entidad entidadE = server.recuperarEntidad(etiqueta.getId());
		server.borrarEntidad(entidadE);
	}

	@Override
	public void modificarEtiqueta(Etiqueta etiqueta) {
		Entidad entidadE = server.recuperarEntidad(etiqueta.getId());
		for (Propiedad prop : entidadE.getPropiedades()) {
			if (prop.getNombre().equals("nombre")) {
				prop.setValor(etiqueta.getNombre());
			}
			server.modificarPropiedad(prop);
		}
	}

	@Override
	public Etiqueta consultarEtiqueta(int id) {
		PoolDAO pool = PoolDAO.getInstancia();
		if (pool.contains(id))
			return (Etiqueta) pool.getObject(id);
		
		Entidad entidadE = server.recuperarEntidad(id);
		String nombre = server.recuperarPropiedadEntidad(entidadE, "nombre");
		
		Etiqueta etiqueta = new Etiqueta(nombre);
		etiqueta.setId(id);
		
		pool.addObject(id, etiqueta);
		
		return etiqueta;
	}

	@Override
	public List<Etiqueta> listarTodasEtiquetas() {
		List<Etiqueta> listaE = new LinkedList<Etiqueta>();
		List<Entidad> entidadesE = server.recuperarEntidades("etiqueta");
		for (Entidad entidadE : entidadesE)
			listaE.add(consultarEtiqueta(entidadE.getId()));
		return listaE;
	}

}
