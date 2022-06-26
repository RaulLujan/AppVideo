package modelo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.*;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfWriter;

public class Usuario {

	private static final int NUM_RECENT = 5;
	
	private static final Font FUENTE_TITULO = new Font(FontFamily.TIMES_ROMAN, 22.0f, Font.BOLD);
	private static final Font FUENTE_SUBTITULO = new Font(FontFamily.TIMES_ROMAN, 18.0f, Font.ITALIC);
	private static final Font FUENTE_PARRAFO = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.NORMAL);

	private static final String NOMBRE_PDF = "listasVideos.pdf";

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

	public Usuario(String login, String password, String nombre, String apellidos, Date fechaNac, String email) {
		super();
		filtro = NoFiltro.getInstancia();
		premium = false;
		this.login = login;
		this.password = password;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNac = fechaNac;
		this.email = email;
		recentVideo = new ListaVideos("Recientes");
		misListas = new LinkedList<ListaVideos>();
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public boolean isLogin(String login) {
		return this.login.equals(login);
	}

	public String getPassword() {
		return password;
	}
	public boolean isPassword(String password) {
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
	
	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}
	
	public int getEdad() {
		LocalDate fechaActual = LocalDate.now();
		LocalDate fechaNacimiento = fechaNac.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return (int) ChronoUnit.YEARS.between(fechaNacimiento, fechaActual);
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
		if (recentVideo.getNumVideos() >= NUM_RECENT)
			recentVideo.removeLastVideo();
		recentVideo.addFirstVideo(video);
	}

	public List<ListaVideos> getMisListas() {
		return misListas;
	}
	public List<String> getNombresMisListas() {
		return misListas.stream()
				.map(lv -> lv.getNombre())
				.collect(Collectors.toList());
//		List<String> nombres = new LinkedList<>();
//		for (ListaVideos lista : misListas)
//			nombres.add(lista.getNombre());
//		return nombres;
	}
	public ListaVideos getMiLista(String nombre) {
		ListaVideos miLista = null;
		for (ListaVideos lista : misListas)
			if (lista.isNombre(nombre)) {
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
	
	private Map<String, Map<String, Integer>> getTablaMisListas() {
		Map<String, Map<String, Integer>> tabla = new HashMap<String, Map<String, Integer>>();
		for (ListaVideos lv : misListas) {
			Map<String, Integer> subtabla = new HashMap<>();
			for (Video v : lv.getVideos()) 
				subtabla.put(v.getTitulo(), v.getNumRepro());
			tabla.put(lv.getNombre(), subtabla);
		}
		return tabla;
	}
	private void addContenido(Document doc) throws DocumentException {
		doc.addTitle("Listas de videos");

		Map<String, Map<String, Integer>> tabla = getTablaMisListas();
		tabla.forEach((k, v) -> {
			try {
				doc.add(new Paragraph("Lista: " + k, FUENTE_SUBTITULO));
				v.forEach((k2, v2) -> {
					try {
						doc.add(new Paragraph("Video: " + k2 + ", numero de reproducciones:  " + v2, FUENTE_PARRAFO));
					} catch (DocumentException e) {
						e.printStackTrace();
					}
				});
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		});

	}
	public Document generarPDF() {
		Document documento = new Document(PageSize.A4, 50, 50, 50, 50);
		try {
			PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(NOMBRE_PDF));
			documento.open();
			addContenido(documento);
			documento.close();
			writer.close();
		} catch (FileNotFoundException | DocumentException fileNotFoundException) {
			System.out.println("No se encontro el fichero para generar el pdf)" + fileNotFoundException);
		}
		return documento;
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
		resultado = primo * resultado + new Boolean(premium).hashCode();
		resultado = primo * resultado + login.hashCode();
		resultado = primo * resultado + password.hashCode();
		resultado = primo * resultado + nombre.hashCode();
		resultado = primo * resultado + apellidos.hashCode();
		resultado = primo * resultado + fechaNac.hashCode();
		resultado = primo * resultado + email.hashCode();
		resultado = primo * resultado + recentVideo.hashCode();
		resultado = primo * resultado + misListas.hashCode();
		return resultado;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String toString() {
		String[] mes = { "Ene", "Feb", "Mar", "Abr", "May", "Jun",
						 "Jul", "Ago", "Sep", "Oct", "Nov", "Dic" };
		String cadena = getClass().getName()
				+ "[filtro=" + filtro.getClass().getSimpleName()
				+ ", premium=" + premium
				+ ", login=" + login
				+ ",\n\tnombre=" + nombre
				+ ", apellidos=" + apellidos
				+ ", fechaNac=" + Integer.toString(fechaNac.getDate())
					+ " " + mes[fechaNac.getMonth()] + " "
					+ Integer.toString(1990 + fechaNac.getYear())
				+ ",\n\temail=" + email
				+ ",\n\t" + recentVideo;
		for (ListaVideos lista : misListas)
			cadena += "\n\t" + lista;
		cadena += "]";
		return cadena;
	}

}
