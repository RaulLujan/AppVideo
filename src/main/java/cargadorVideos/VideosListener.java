package cargadorVideos;

import java.util.EventListener;
import java.util.EventObject;

public interface VideosListener extends EventListener {
	
	public void nuevosVideos(EventObject e);
	
}
