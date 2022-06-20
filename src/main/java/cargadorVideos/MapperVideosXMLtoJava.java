package cargadorVideos;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class MapperVideosXMLtoJava {

	public static Videos cargarVideos(String fichero) {

		JAXBContext jc;
		Videos canciones = null;
		try {
			jc = JAXBContext.newInstance("cargadorVideos");
			Unmarshaller u = jc.createUnmarshaller();
			File file = new File(fichero);
			canciones = (Videos) u.unmarshal(file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}	
		return canciones;
	}
}
