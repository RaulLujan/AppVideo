package modelo;

import java.util.List;

public class FiltroEtiquetas extends Filtro {
	
	private static FiltroEtiquetas instancia = new FiltroEtiquetas();
	public static Filtro getInstancia(List<Etiqueta> etiquetas) {
		instancia.etiquetas = etiquetas;
		return instancia;
	}
	
	private List<Etiqueta> etiquetas;
	
	private FiltroEtiquetas() {
		super("Un usuario podr� buscar v�deos utilizando como filtros de b�squeda una o m�s etiquetas");
	}

	@Override
	public boolean esVideoOK(Video video, Usuario usuario) {
		for (Etiqueta etiqueta : etiquetas)
			if (!video.containsEtiqueta(etiqueta))
				return false;
		return true;
	}

}
