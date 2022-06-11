package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import modelo.Etiqueta;
import tds.driver.ServicioPersistencia;

public class AdaptadorEtiquetaTDS implements AdaptadorEtiquetaDAO {

	private static AdaptadorEtiquetaTDS instancia = null;
	private ServicioPersistencia server;

	@Override
	public void insertarEtiqueta(Etiqueta etiqueta) {
		Entidad entidadEtiqueta = null;
		if (server.recuperarEntidad(etiqueta.getId()) != null)
			return;

		entidadEtiqueta = new Entidad();
		entidadEtiqueta.setNombre("etiqueta");
		entidadEtiqueta
				.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("nombre", etiqueta.getNombre()))));

		entidadEtiqueta = server.registrarEntidad(entidadEtiqueta);

		etiqueta.setId(entidadEtiqueta.getId());
	}

	@Override
	public void borrarEtiqueta(Etiqueta etiqueta) {
		Entidad entidadEtiqueta = server.recuperarEntidad(etiqueta.getId());
		server.borrarEntidad(entidadEtiqueta);
	}

	@Override
	public void modificarEtiqueta(Etiqueta etiqueta) {
		Entidad entidadEtiqueta = server.recuperarEntidad(etiqueta.getId());
		server.eliminarPropiedadEntidad(entidadEtiqueta, "nombre");
		server.anadirPropiedadEntidad(entidadEtiqueta, "nombre", etiqueta.getNombre());
		
		// Escalable por si tiene más propiedades, de momento solo tiene una 
		for (Propiedad prop : entidadEtiqueta.getPropiedades()) {
			switch (prop.getNombre()) {
			case "nombre":
				prop.setValor(etiqueta.getNombre());
				break;

			default:
				break;
			}
		}
	}

	@Override
	public Etiqueta consultarEtiqueta(int id) {
		Entidad entidadEtiqueta = server.recuperarEntidad(id);
		String nombre = server.recuperarPropiedadEntidad(entidadEtiqueta, "nombre");
		Etiqueta etiqueta = new Etiqueta(nombre);
		etiqueta.setId(id);
		return etiqueta;
	}

	@Override
	public List<Etiqueta> listarTodasEtiquetas() {
		List<Etiqueta> entidadEtiqueta = new LinkedList<Etiqueta>();
		List<Entidad> eEtiquetas = server.recuperarEntidades("etiqueta");
		for (Entidad eEtiqueta : eEtiquetas)
			entidadEtiqueta.add(consultarEtiqueta(eEtiqueta.getId()));
		return entidadEtiqueta;
	}

	public static AdaptadorEtiquetaTDS getInstancia() {
		return instancia;
	}

	public static void setInstancia(AdaptadorEtiquetaTDS instancia) {
		AdaptadorEtiquetaTDS.instancia = instancia;
	}

}
