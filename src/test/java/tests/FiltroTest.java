package tests;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import modelo.CatalogoEtiquetas;
import modelo.Etiqueta;
import modelo.FiltroImpopulares;
import modelo.FiltroMenores;
import modelo.FiltroNombresLargos;
import modelo.ListaVideos;
import modelo.NoFiltro;
import modelo.Usuario;
import modelo.Video;

public class FiltroTest {
	
	Usuario usuario;
	ListaVideos listaVideos;

	// Inicialización
	@Before
	public void init() {
		usuario = new Usuario("usuarioLogin", "pass", "nombreUsuario", "apellidosUsuario", new Date(), "usuario@email.com");
		
		Etiqueta adultos = new Etiqueta("Adultos");
		CatalogoEtiquetas.getInstancia().addEtiqueta(adultos);
		
		Video video1 = new Video("URL/video1", "Como implementar Junit con titulo largo");
		Video video2 = new Video("URL/video2", "video2");
		
		video1.setNumRepro(0);
		video1.addEtiqueta(adultos);
		
		video2.setNumRepro(10);
		
		listaVideos = new ListaVideos("lista1");
		listaVideos.addFirstVideo(video1);
		listaVideos.addFirstVideo(video2);
		
		usuario.addMiLista(listaVideos);
	}
	
	// Test relacionado con NoFiltro
	@Test
	public void testNoFiltro() {
		NoFiltro filtro = (NoFiltro) NoFiltro.getInstancia();
		
		usuario.setFiltro(filtro);
		
		assertEquals("Test de NoFiltro", 2, listaVideos.getVideos().stream()
				.filter(v -> filtro.esVideoOK(v, usuario)).collect(Collectors.toList()).size());	
	}
	
	// Test relacionado con FiltroImpopulares
	@Test
	public void testFiltroImpopulares() {
		FiltroImpopulares filtro = (FiltroImpopulares) FiltroImpopulares.getInstancia();
		
		usuario.setFiltro(filtro);
		
		assertEquals("Test de FiltroImpopulares", 1, listaVideos.getVideos().stream()
				.filter(v -> filtro.esVideoOK(v, usuario)).collect(Collectors.toList()).size());	
	}
	
	// Test relacionado con FiltroMenores
	@Test
	public void testFiltroMenores() {
		FiltroMenores filtro = (FiltroMenores) FiltroMenores.getInstancia();
		
		usuario.setFiltro(filtro);
		
		assertEquals("Test de FiltroMenores", 1, listaVideos.getVideos().stream()
				.filter(v -> filtro.esVideoOK(v, usuario)).collect(Collectors.toList()).size());	
	}
	
	// Test relacionado con FiltroNombreLargos
	@Test
	public void testFiltroNombresLargos() {
		FiltroNombresLargos filtro = (FiltroNombresLargos) FiltroNombresLargos.getInstancia();
		
		usuario.setFiltro(filtro);
		
		assertEquals("Test de FiltroNombresLargos", 1, listaVideos.getVideos().stream()
				.filter(v -> filtro.esVideoOK(v, usuario)).collect(Collectors.toList()).size());	
	}
}
