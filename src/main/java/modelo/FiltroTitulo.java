package modelo;

public class FiltroTitulo extends Filtro {
	
	private String subtitulo;
	
	public FiltroTitulo(String subtitulo) {
		super("Un usuario podr� buscar v�deos utilizando como filtros de b�squeda el t�tulo (o una parte de �l)");
		this.subtitulo = subtitulo;
	}

	@Override
	public boolean esVideoOK(Video video) {
		return video.getTitulo().contains(subtitulo);
	}

}
