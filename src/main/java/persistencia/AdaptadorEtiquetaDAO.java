package persistencia;

import java.util.List;
import modelo.Etiqueta;

public interface AdaptadorEtiquetaDAO {
	
	public void insertarEtiqueta(Etiqueta etiqueta);
	public void borrarEtiqueta(Etiqueta etiqueta);
	public void modificarEtiqueta(Etiqueta etiqueta);
	public Etiqueta consultarEtiqueta(int id);
	public List<Etiqueta> listarTodasEtiquetas();
	
}
