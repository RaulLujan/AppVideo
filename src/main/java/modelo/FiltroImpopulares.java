package modelo;

public class FiltroImpopulares extends Filtro {
	
	public FiltroImpopulares() {
		super("Elimina de la b�squeda los v�deos que han sido reproducidos menos de 5 veces");
	}

	@Override
	public boolean esVideoOK(Video video) {
		return video.getNumRepro() >= 5;
	}

}
