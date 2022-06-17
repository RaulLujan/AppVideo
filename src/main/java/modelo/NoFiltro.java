package modelo;

public class NoFiltro extends Filtro {
	
	private static Filtro unicaInstancia = null;
	
	public static Filtro getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new NoFiltro();
		return unicaInstancia;
	}
	
	private NoFiltro() {
		super("No se aplica ningun filtro a la busqueda");
	}

	@Override
	public boolean filtrarVideo(Video video, Usuario usuario) {
		return false;
	}

}
