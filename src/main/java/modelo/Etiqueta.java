package modelo;

public class Etiqueta {
	
	private int id;
	private String nombre;
	
	public Etiqueta(String nombre) {
		this.nombre = nombre;
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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Etiqueta otra = (Etiqueta) obj;
		return this.nombre.equals(otra.nombre);
	}
	
	@Override
	public int hashCode() {
		int primo = 31;
		int resultado = 1;
		resultado = primo*resultado + nombre.hashCode();
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName()
				+ "[nombre=" + nombre + "]";
	}
	
}
