package modelo;

public class FiltroMenores extends Filtro {
	
	private Usuario usuario;
	
	public FiltroMenores(Usuario usuario) {
		super("Elimina los v�deos con la etiqueta \"Adultos\" si el usuario es menor de 18 a�os");
		this.usuario = usuario;
	}
	
	@Override
	public boolean esVideoOK(Video video) {
		CatalogoEtiquetas catalogo = CatalogoEtiquetas.getInstancia();
		if (usuario.getEdad() >= 18 || !catalogo.existsEtiqueta("Adultos"))
			return true;
		return video.containsEtiqueta(catalogo.getEtiqueta("Adultos"));
	}

}
