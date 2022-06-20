package cargadorVideos;

import java.io.File;

public interface IBuscadorVideos {
	
	void setArchivoVideos(File fichero);
	
	void addVideosListener(VideosListener listener);
	
	void removeVideosListener(VideosListener listener);

}
