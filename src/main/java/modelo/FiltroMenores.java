package modelo;

public class FiltroMenores extends Filtro {
	
	private static Filtro instancia = new FiltroMenores();
	public static Filtro getInstancia() {
		return instancia;
	}
	
	public FiltroMenores() {
		super("Elimina los v�deos con la etiqueta \"Adultos\" si el usuario es menor de 18 a�os");
	}
	
	@Override
	public boolean esVideoOK(Video video, Usuario usuario) {
		CatalogoEtiquetas catalogo = CatalogoEtiquetas.getInstancia();
		if (usuario.getEdad() >= 18 || !catalogo.existsEtiqueta("Adultos"))
			return true;
		return !video.containsEtiqueta(catalogo.getEtiqueta("Adultos"));
	}

}
