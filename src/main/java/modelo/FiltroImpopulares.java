package modelo;

public class FiltroImpopulares extends Filtro {
	
	public FiltroImpopulares() {
		super("Elimina de la búsqueda los vídeos que han sido reproducidos menos de 5 veces");
	}

	@Override
	public boolean esVideoOK(Video video) {
		return video.getNumRepro() >= 5;
	}

}
