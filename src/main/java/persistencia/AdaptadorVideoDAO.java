package persistencia;

import java.util.List;
import modelo.Video;

public interface AdaptadorVideoDAO {
	
	public void insertarVideo(Video video);
	public void borrarVideo(Video video);
	public void modificarVideo(Video video);
	public Video consultarVideo(int id);
	public List<Video> listarTodosVideos();
	
}
