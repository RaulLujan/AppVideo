package modelo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Usuario {
	
	private int id;
	private Filtro filtro;
	private boolean premium;
	private String login;
	private String password;
	private String nombre;
	private String apellidos;
	private Date fechaNac;
	private String email;
	private ListaVideos recentVideo;
	private List<ListaVideos> misListas;
	
	
	public int getId() {
		return id;
	}
	public void setCodigo(int id) {
		this.id = id;
	}
	
	public Filtro getFiltro() {
		return filtro;
	}
	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}
	
	public boolean isPremium() {
		return premium;
	}
	public void setPremium(boolean premium) {
		this.premium = premium;
	}
	
	public String getLogin() {
		return login;
	}
	public boolean checkLogin(String login) {
		return this.login.equals(login);
	}
	
	public String getPassword() {
		return password;
	}
	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getApellidos() {
		return apellidos;
	}
	
	public Date getFechaNac() {
		return fechaNac;
	}
	
	public String getEmail() {
		return email;
	}
	
	public ListaVideos getRecentVideo() {
		return recentVideo;
	}
	public void setRecentVideo(ListaVideos recentVideo) {
		this.recentVideo = recentVideo;
	}
	public void addRecentVideo(Video video) {
		if (recentVideo.containsVideo(video))
			recentVideo.removeVideo(video);
		if (recentVideo.getNumVideos() >= 5)
			recentVideo.removeLastVideo();
		recentVideo.addFirstVideo(video);
	}
	
	public List<ListaVideos> getMisListas() {
		return misListas;
	}
	
	public List<String> getNombresMisListas() {
		List<String> nombres = new LinkedList<>();
		for (ListaVideos lista : misListas)
			nombres.add(lista.getNombre());
		return nombres;
	}
	
	public ListaVideos getMiLista(String nombre) {
		ListaVideos miLista = null;
		for (ListaVideos lista : misListas)
			if (lista.checkNombre(nombre)) {
				miLista = lista;
				break;
			}
		return miLista;
	}
	
	public boolean containsMiLista(ListaVideos miLista) {
		return misListas.contains(miLista);
	}
	
	public void addMiLista(ListaVideos miLista) {
		misListas.add(miLista);
	}
	
	public void removeMiLista(ListaVideos miLista) {
		misListas.remove(miLista);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Usuario otro = (Usuario) obj;
		return this.filtro == otro.filtro
				&& this.premium == otro.premium
				&& this.login.equals(otro.login)
				&& this.password.equals(otro.password)
				&& this.nombre.equals(otro.nombre)
				&& this.apellidos.equals(otro.apellidos)
				&& this.fechaNac.equals(otro.fechaNac)
				&& this.email.equals(otro.email)
				&& this.recentVideo.equals(otro.recentVideo)
				&& this.misListas.equals(otro.misListas);
	}
	
	@Override
	public int hashCode() {
		int primo = 31;
		int resultado = 1;
		resultado = primo*resultado + new Boolean(premium).hashCode();
		resultado = primo*resultado + login.hashCode();
		resultado = primo*resultado + password.hashCode();
		resultado = primo*resultado + nombre.hashCode();
		resultado = primo*resultado + apellidos.hashCode();
		resultado = primo*resultado + fechaNac.hashCode();
		resultado = primo*resultado + email.hashCode();
		resultado = primo*resultado + recentVideo.hashCode();
		resultado = primo*resultado + misListas.hashCode();
		return resultado;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public String toString() {
		String[] mes = {"Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"};
		String cadena = getClass().getName()
				+ "[filtro=" + filtro.getClass().getSimpleName()
				+ ", premium=" + premium
				+ ", login=" + login
				+ ",\n\tnombre=" + nombre
				+ ", apellidos=" + apellidos
				+ ", fechaNac="
					+ Integer.toString(fechaNac.getDate())
					+ " " + mes[fechaNac.getMonth()] + " "
					+ Integer.toString(1990+fechaNac.getYear())
				+ ",\n\temail=" + email
				+ ",\n\t" + recentVideo;
		for (ListaVideos lista : misListas)
			cadena += "\n\t" + lista;
		cadena += "]";
		return cadena;
	}
	
}
