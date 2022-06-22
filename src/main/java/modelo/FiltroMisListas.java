package modelo;

public class FiltroMisListas extends Filtro {
	
	private Usuario usuario;
	
	public FiltroMisListas(Usuario usuario) {
		super("Elimina de las b�squedas los v�deos que ya est�n contenidos en alguna lista del usuario");
		this.usuario = usuario;
	}

	@Override
	public boolean esVideoOK(Video video) {
		for (ListaVideos videos : usuario.getMisListas())
			if (videos.containsVideo(video))
				return false;
		return true;
	}

}
