package modelo;

public class FiltroNombresLargos extends Filtro {
	
	public FiltroNombresLargos() {
		super("Elimina de las búsquedas los vídeos cuyo titulo tenga más de 16 caracteres");
	}

	@Override
	public boolean esVideoOK(Video video) {
		return video.getTitulo().length() <= 16;
	}

}
