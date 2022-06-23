package modelo;

public class FiltroTitulo extends Filtro {
	
	private static FiltroTitulo instancia = new FiltroTitulo();
	public static Filtro getInstancia(String subtitulo) {
		instancia.subtitulo = subtitulo;
		return instancia;
	}
	
	private String subtitulo;

	private FiltroTitulo() {
		super("Un usuario podr� buscar v�deos utilizando como filtros de b�squeda el t�tulo (o una parte de �l)");
	}
	
	@Override
	public boolean esVideoOK(Video video, Usuario usuario) {
		return video.getTitulo().contains(subtitulo);
	}

}
