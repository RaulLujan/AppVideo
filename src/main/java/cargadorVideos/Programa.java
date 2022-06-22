package cargadorVideos;

public class Programa {

	public static void main(String[] args) {

		Videos videos = MapperVideosXMLtoJava
				.cargarVideos("xml/videos.xml"); //Obtener fichero a cargar mediante JFileChooser en Swing
	
		for (Video video : videos.getVideo()) {
			
			System.out.println("Titulo: " + video.getTitulo());
			System.out.println(" URL : " + video.getURL());
			for (String etiqueta : video.getEtiqueta()) {
				System.out.println(" Tag : " + etiqueta);
			}
			System.out.println("***** ***** *****");
		}
	}

}
