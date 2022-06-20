package cargadorVideos;


import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class CargadorVideos {
	public static Videos cargarVideos(String datos) {

		JAXBContext jc;
		Videos asistencias = null;
		try {
			jc = JAXBContext.newInstance("cargadorVideos");
			Unmarshaller u = jc.createUnmarshaller();
			File file = new File(datos);
			asistencias = (Videos) u.unmarshal(file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}	
		return asistencias;
	}
}
