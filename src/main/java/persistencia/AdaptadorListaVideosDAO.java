package persistencia;

import java.util.List;
import modelo.ListaVideos;

public interface AdaptadorListaVideosDAO {
	
	public void registrarListaVideos(ListaVideos listaVideos);
	public void borrarListaVideos(ListaVideos listaVideos);
	public void modificarListaVideos(ListaVideos listaVideos);
	public ListaVideos recuperarListaVideos(int id);
	public List<ListaVideos> recuperarTodasListasVideos();
	
}
