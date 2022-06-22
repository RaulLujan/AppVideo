package modelo;

public class FiltroNombresLargos extends Filtro {
	
	public FiltroNombresLargos() {
		super("Elimina de las b�squedas los v�deos cuyo titulo tenga m�s de 16 caracteres");
	}

	@Override
	public boolean esVideoOK(Video video) {
		return video.getTitulo().length() <= 16;
	}

}
