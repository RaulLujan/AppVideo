package modelo;

import java.util.LinkedList;
import java.util.List;

public class ListaVideos {
	
	private int id;
	private String nombre;
	private List<Video> videos;
	
	public ListaVideos(String nombre) {
		this.nombre = nombre;
		this.videos = new LinkedList<Video>();
	}
	
	public ListaVideos() {
		this("");
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public boolean checkNombre(String nombre) {
		return this.nombre.equals(nombre);
	}
	
	public int getNumVideos() {
		return videos.size();
	}
	
	public List<Video> getVideos() {
		return videos;
	}
	public List<Integer> getIdVideos() {
		List<Integer> codigos = new LinkedList<>();
		for (Video video : videos)
			codigos.add(video.getId());
		return codigos;
	}
	public boolean containsVideo(Video video) {
		return videos.contains(video);
	}

	public void addFirstVideo(Video video) {
		videos.add(0,video);
	}
	
	public void addLastVideo(Video video) {
		videos.add(videos.size(),video);
	}
	
	public boolean removeVideo(Video video) {
		return videos.remove(video);
	}
	
	
	public Video removeLastVideo() {
		return videos.remove(videos.size()-1);
	}
	
	public void removeAll() {
		videos.clear();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		ListaVideos otra = (ListaVideos) obj;
		return this.nombre.equals(otra.nombre)
				&& this.videos.equals(otra.videos);
	}
	
	@Override
	public int hashCode() {
		int primo = 31;
		int resultado = 1;
		resultado = primo*resultado + nombre.hashCode();
		resultado = primo*resultado + videos.hashCode();
		return resultado;
	}
	
	@Override
	public String toString() {
		String cadena = getClass().getName()
				+ "[nombre=" + nombre
				+ ", videos=";
		for (Video video : videos)
			cadena += "\n\t" + video;
		cadena += "]";
		return cadena;
	}
	
}
