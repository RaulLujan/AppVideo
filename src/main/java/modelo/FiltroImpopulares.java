package modelo;

public class FiltroImpopulares extends Filtro {
	
	private static Filtro instancia = new FiltroImpopulares();
	public static Filtro getInstancia() {
		return instancia;
	}
	
	public FiltroImpopulares() {
		super("Elimina de la b�squeda los v�deos que han sido reproducidos menos de 5 veces");
	}

	@Override
	public boolean esVideoOK(Video video, Usuario usuario) {
		return video.getNumRepro() >= 5;
	}

}
