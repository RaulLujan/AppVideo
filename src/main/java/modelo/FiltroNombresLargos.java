package modelo;

public class FiltroNombresLargos extends Filtro {
	
	private static Filtro instancia = new FiltroNombresLargos();
	public static Filtro getInstancia() {
		return instancia;
	}
	
	public FiltroNombresLargos() {
		super("Elimina de las b�squedas los v�deos cuyo titulo tenga m�s de 16 caracteres");
	}

	@Override
	public boolean esVideoOK(Video video, Usuario usuario) {
		return video.getTitulo().length() <= 16;
	}

}
