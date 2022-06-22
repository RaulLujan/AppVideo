package modelo;

public class FiltroTitulo extends Filtro {
	
	private String subtitulo;
	
	public FiltroTitulo(String subtitulo) {
		super("Un usuario podrá buscar vídeos utilizando como filtros de búsqueda el título (o una parte de él)");
		this.subtitulo = subtitulo;
	}

	@Override
	public boolean esVideoOK(Video video) {
		return video.getTitulo().contains(subtitulo);
	}

}
