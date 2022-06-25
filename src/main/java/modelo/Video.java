package modelo;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.*;

import javax.swing.ImageIcon;

public class Video {

	private int id;
	private String url;
	private String titulo;
	private int numRepro;
	private List<Etiqueta> etiquetas;
	private Date ultimaRepro;

	private ImageIcon icono = null;

	public Video(String url, String titulo) {
		this.url = url;
		this.titulo = titulo;
		numRepro = 0;
		etiquetas = new LinkedList<Etiqueta>();
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
		if (titulo.length() > 21)
			return titulo.substring(0, 20) + "...";
		else
			return titulo;
	}

	public int getNumRepro() {
		return numRepro;
	}
	public void setNumRepro(int numRepro) {
		this.numRepro = numRepro;
	}
	public void incrNumRepro() {
		numRepro++;
	}

	public List<Etiqueta> getEtiquetas() {
		return etiquetas;
	}
	public List<String> getNombresEtiquetas() {
		return etiquetas.stream()
				.map(et -> et.getNombre())
				.collect(Collectors.toList());
	}
	public boolean containsEtiqueta(Etiqueta etiqueta) {
		return etiquetas.contains(etiqueta);
	}
	public boolean containsEtiqueta(String nombre) {
		return etiquetas.stream()
				.anyMatch(et -> et.isNombre(nombre));
	}
	public boolean addEtiqueta(Etiqueta etiqueta) {
		if (!etiquetas.contains(etiqueta)) {
			etiquetas.add(etiqueta);
			return true;
		}
		return false;
	}

	public Date getUltimaRepro() {
		return ultimaRepro;
	}
	public void setUltimaRepro(Date ultimaRepro) {
		this.ultimaRepro = ultimaRepro;
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
		return this.url.equals(otro.url)
				&& this.titulo.equals(otro.titulo)
				&& this.numRepro == otro.numRepro
				&& this.etiquetas.equals(otro.etiquetas);
	}

	@Override
	public int hashCode() {
		int primo = 31;
		int resultado = 1;
		resultado = primo * resultado + url.hashCode();
		resultado = primo * resultado + titulo.hashCode();
		resultado = primo * resultado + numRepro;
		resultado = primo * resultado + etiquetas.hashCode();
		return resultado;
	}

	@Override
	public String toString() {
		String cadena = getClass().getName()
				+ "[url=" + url
				+ ",\n\ttitulo=" + titulo +
				", numRep=" + numRepro
				+ ", etiquetas=";
		for (Etiqueta etiqueta : etiquetas)
			cadena += "\n\t" + etiqueta;
		cadena += "]";
		return cadena;
	}

}
