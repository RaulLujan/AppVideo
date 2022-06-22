package modelo;

import java.util.List;

public class FiltroEtiquetas extends Filtro {
	
	private List<Etiqueta> etiquetas;
	
	public FiltroEtiquetas(List<Etiqueta> etiquetas) {
		super("Un usuario podrá buscar vídeos utilizando como filtros de búsqueda una o más etiquetas");
		this.etiquetas = etiquetas;
	}

	@Override
	public boolean esVideoOK(Video video) {
		for (Etiqueta etiqueta : etiquetas)
			if (!video.containsEtiqueta(etiqueta))
				return false;
		return true;
	}

}
