package cargadorVideos;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class MapperVideosXMLtoJava {

	public static Videos cargarVideos(String fichero) {

		JAXBContext jc;
		Videos videos = null;
		try {
			jc = JAXBContext.newInstance("cargadorVideos");
			Unmarshaller u = jc.createUnmarshaller();
			File file = new File(fichero);
			videos = (Videos) u.unmarshal(file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}	
		return videos;
	}
}
