package modelo;

public class NoFiltro extends Filtro {
	
	private static Filtro instancia = null;
	public static Filtro getInstancia() {
		if (instancia == null)
			instancia = new NoFiltro();
		return instancia;
	}
	
	private NoFiltro() {
		super("No se aplica ningun filtro a la busqueda");
	}

	@Override
	public boolean esVideoOK(Video video) {
		return true;
	}

}
