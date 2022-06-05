package persistencia;

import java.util.List;
import modelo.Video;

public interface AdaptadorVideoDAO {
	
	public void registrarVideo(Video video);
	public void borrarVideo(Video video);
	public void modificarVideo(Video video);
	public Video recuperarVideo(int codigo);
	public List<Video> recuperarTodosVideos();
	
}
