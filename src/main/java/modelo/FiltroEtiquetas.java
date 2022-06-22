package modelo;

import java.util.List;

public class FiltroEtiquetas extends Filtro {
	
	private List<Etiqueta> etiquetas;
	
	public FiltroEtiquetas(List<Etiqueta> etiquetas) {
		super("Un usuario podr� buscar v�deos utilizando como filtros de b�squeda una o m�s etiquetas");
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
