package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.*;

import modelo.ListaVideos;
import modelo.Usuario;
import modelo.Video;

public class UsuarioTest {
	
	Usuario usuario;
	ListaVideos listaVideos;
	
	// Inicialización del usuario
	@Before
	public void init() {
		usuario = new Usuario("usuarioLogin", "pass", "nombreUsuario", "apellidosUsuario", new Date(), "usuario@email.com");
		
		Video video1 = new Video("URL/video1", "video1");
		Video video2 = new Video("URL/video2", "video2");
		
		listaVideos = new ListaVideos("lista1");
		listaVideos.addFirstVideo(video1);
		listaVideos.addFirstVideo(video2);
		
		usuario.addMiLista(listaVideos);
	}
	
	// Tests relacionados con la fecha de nacimiento
	@Test
	public void testEdad() {
		usuario.getFechaNac().setYear(95);
		assertEquals("Test de edad", 27, usuario.getEdad());
	}
	
	@Test
	public void testIsCumple() {
		usuario.setFechaNac(new Date());
		assertTrue("Test de cumpleaños", usuario.isCumple());
	}
	
	@Test
	public void testIsNotCumple() {
		usuario.setFechaNac(new Date(1995, 1, 1));
		assertFalse("Test de que no es cumpleaños", usuario.isCumple());
	}
	
	// Tests relacionados con la lista de videos
	@Test
	public void testListasVideos() {
		assertEquals("Test de número de videos en lista", 2, usuario.getMiLista("lista1").getNumVideos());
	}
	
	@Test
	public void testListasRecintes() {
		usuario.addRecentVideo(new Video("URL/video1", "video1"));
		usuario.addRecentVideo(new Video("URL/video2", "video2"));
		usuario.addRecentVideo(new Video("URL/video3", "video3"));
		usuario.addRecentVideo(new Video("URL/video4", "video4"));
		usuario.addRecentVideo(new Video("URL/video5", "video5"));
		usuario.addRecentVideo(new Video("URL/video6", "video6"));
		usuario.addRecentVideo(new Video("URL/video7", "video7"));
		
		
		assertEquals("Test de número de videos en recientes", 5, usuario.getRecentVideo().getNumVideos());
	}
	
	@Test
	public void testListaVacia() {
		usuario.removeMiLista(listaVideos);
		assertFalse("Test de eliminación de lista", usuario.containsMiLista(listaVideos));
	}

}
