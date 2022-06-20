package cargadorVideos;

import java.util.EventObject;

@SuppressWarnings("serial")
public class VideosEvent extends EventObject {

	private Videos newVideos;

	public VideosEvent(Object fuente, Videos nuevos) {
		super(fuente);
		this.newVideos = nuevos;
	}

	public Videos getListaVideos() {
		return newVideos;
	}
	
}