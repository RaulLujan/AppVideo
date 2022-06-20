package cargadorVideos;

import java.io.File;
import java.util.Vector;

public class BuscadorVideos implements IBuscadorVideos {

	private Videos videos;
	private Vector<VideosListener> listListener;

	public BuscadorVideos() {
		this.listListener = new Vector<>();
		videos = null;
	}

	@Override
	public void setArchivoVideos(File fichero) {
		Videos videos = CargadorVideos.cargarVideos(fichero.getAbsolutePath());
		setNuevosVideos(videos);
	}

	public void setNuevosVideos(Videos nuevos) {
		Videos viejos = videos;
		videos = nuevos;
		if (!nuevos.equals(viejos)) {
			VideosEvent evento = new VideosEvent(this, nuevos);
			notificarNuevosVideos(evento);
		}
	}

	@SuppressWarnings("unchecked")
	private void notificarNuevosVideos(VideosEvent evento) {
		Vector<VideosListener> lista;
		synchronized (this) {
			lista = (Vector<VideosListener>) listListener.clone();
		}
		for (VideosListener listener : lista)
			listener.nuevosVideos(evento);
	}

	@Override
	public synchronized void addVideosListener(VideosListener listener) {
		listListener.addElement(listener);
	}

	@Override
	public synchronized void removeVideosListener(VideosListener listener) {
		listListener.removeElement(listener);
	}

}
