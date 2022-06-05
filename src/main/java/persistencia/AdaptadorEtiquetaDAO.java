package persistencia;

import java.util.List;
import modelo.Etiqueta;

public interface AdaptadorEtiquetaDAO {
	
	public void registrarEtiqueta(Etiqueta etiqueta);
	public void borrarEtiqueta(Etiqueta etiqueta);
	public void modificarEtiqueta(Etiqueta etiqueta);
	public Etiqueta recuperarEtiqueta(int codigo);
	public List<Etiqueta> recuperarTodasEtiquetas();
	
}
