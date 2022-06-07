package persistencia;

import java.text.SimpleDateFormat;
import java.util.List;

import modelo.Usuario;
import modelo.Video;
import tds.driver.ServicioPersistencia;

public class AdaptadorVideoTDS implements AdaptadorVideoDAO {

	private static AdaptadorListaVideosTDS instancia = null;
	private ServicioPersistencia server;
	private SimpleDateFormat dateFormat;
	
	@Override
	public void registrarVideo(Video video) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void borrarVideo(Video video) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void modificarVideo(Video video) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Video recuperarVideo(int codigo) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Video> recuperarTodosVideos() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public static AdaptadorListaVideosTDS getInstancia() {
		return instancia;
	}
	
	
	public static void setInstancia(AdaptadorListaVideosTDS instancia) {
		AdaptadorVideoTDS.instancia = instancia;
	}
	
	
	

}
