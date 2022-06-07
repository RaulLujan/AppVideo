package persistencia;

import java.text.SimpleDateFormat;
import java.util.List;

import modelo.ListaVideos;
import modelo.Usuario;
import modelo.Video;
import tds.driver.ServicioPersistencia;

public class AdaptadorListaVideosTDS implements AdaptadorListaVideosDAO {

	private static AdaptadorListaVideosTDS instancia = null;
	private ServicioPersistencia server;
	private SimpleDateFormat dateFormat;

	

	public static AdaptadorListaVideosTDS getInstancia() {
		return instancia;
	}

	public static void setInstancia(AdaptadorListaVideosTDS instancia) {
		AdaptadorListaVideosTDS.instancia = instancia;
	}

	@Override
	public void registrarListaVideos(ListaVideos listaVideos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void borrarListaVideos(ListaVideos listaVideos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificarListaVideos(ListaVideos listaVideos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ListaVideos recuperarListaVideos(int codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ListaVideos> recuperarTodasListasVideos() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
