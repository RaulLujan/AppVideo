package persistencia;

import java.util.List;
import modelo.ListaVideos;

public interface AdaptadorListaVideosDAO {
	
	public void insertarListaVideos(ListaVideos listaVideos);
	public void borrarListaVideos(ListaVideos listaVideos);
	public void modificarListaVideos(ListaVideos listaVideos);
	public ListaVideos consultarListaVideos(int id);
	public List<ListaVideos> listarTodasListasVideos();
	
}
