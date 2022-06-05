package modelo;

public abstract class Filtro {
	
	private String descripcion = "";
	
	public String getDescripcion() {
		return descripcion;
	}

	protected Filtro(String descripcion) {
		super();
		this.descripcion = descripcion;
	}
	public abstract boolean filtrarVideo(Video video, Usuario usuario);

}
