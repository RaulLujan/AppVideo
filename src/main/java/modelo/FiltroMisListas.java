package modelo;

public class FiltroMisListas extends Filtro {
	
	private static Filtro instancia = new FiltroMisListas();
	public static Filtro getInstancia() {
		return instancia;
	}
	
	public FiltroMisListas() {
		super("Elimina de las b�squedas los v�deos que ya est�n contenidos en alguna lista del usuario");
	}

	@Override
	public boolean esVideoOK(Video video, Usuario usuario) {
		for (ListaVideos videos : usuario.getMisListas())
			if (videos.containsVideo(video))
				return false;
		return true;
	}

}
