package modelo;

import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;

public class Video {

	private int id;
	private String url;
	private String titulo;
	private int numReproducciones;
	private List<Etiqueta> etiquetas;

	private ImageIcon icono = null;

	public Video(String url, String titulo) {
		this.url = url;
		this.titulo = titulo;
		this.numReproducciones = 0;
		this.etiquetas = new LinkedList<Etiqueta>();
	}

	public boolean isTituloCorto() {
		return titulo.length() < 17;
	}

	public ImageIcon getIcono() {
		return icono;
	}

	public void setIcono(ImageIcon icono) {
		this.icono = icono;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getTituloCorto() {
		if (titulo.length() > 31)
			return titulo.substring(0, 30) + "...";
		else
			return titulo;
	}

	public boolean containsSubtitulo(String subtitulo) {
		return titulo.contains(subtitulo);
	}

	public int getNumReproducciones() {
		return numReproducciones;
	}

	public void setNumReproducciones(int numReproducciones) {
		this.numReproducciones = numReproducciones;
	}

	public void incrementarNumReproducciones() {
		numReproducciones++;
	}

	public List<Etiqueta> getEtiquetas() {
		return etiquetas;
	}

	public List<String> getNombresEtiquetas() {
		List<String> nombres = new LinkedList<>();
		for (Etiqueta etiqueta : etiquetas)
			nombres.add(etiqueta.getNombre());
		return nombres;
	}

	public boolean containsEtiqueta(Etiqueta etiqueta) {
		return etiquetas.contains(etiqueta);
	}

	public boolean addEtiqueta(Etiqueta etiqueta) {
		if (!etiquetas.contains(etiqueta)) {
			etiquetas.add(etiqueta);
			return true;
		}
		return false;
	}

	public boolean filtrarVideo(String subtitulo) {
		return !containsSubtitulo(subtitulo);
	}

	public boolean filtrarVideo(List<Etiqueta> etiquetas) {
		for (Etiqueta etiqueta : etiquetas)
			if (!containsEtiqueta(etiqueta))
				return true;
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Video otro = (Video) obj;
		return this.url.equals(otro.url) && this.titulo.equals(otro.titulo)
				&& this.numReproducciones == otro.numReproducciones && this.etiquetas.equals(otro.etiquetas);
	}

	@Override
	public int hashCode() {
		int primo = 31;
		int resultado = 1;
		resultado = primo * resultado + url.hashCode();
		resultado = primo * resultado + titulo.hashCode();
		resultado = primo * resultado + numReproducciones;
		resultado = primo * resultado + etiquetas.hashCode();
		return resultado;
	}

	@Override
	public String toString() {
		String cadena = getClass().getName() + "[url=" + url + ",\n\ttitulo=" + titulo + ", numRep=" + numReproducciones
				+ ", etiquetas=";
		for (Etiqueta etiqueta : etiquetas)
			cadena += "\n\t" + etiqueta;
		cadena += "]";
		return cadena;
	}

}
