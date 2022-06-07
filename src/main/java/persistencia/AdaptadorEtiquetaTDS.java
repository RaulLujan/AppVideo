package persistencia;

import java.text.SimpleDateFormat;
import java.util.List;

import modelo.Etiqueta;
import modelo.Usuario;
import tds.driver.ServicioPersistencia;

public class AdaptadorEtiquetaTDS implements AdaptadorEtiquetaDAO {

	private static AdaptadorListaVideosTDS instancia = null;
	private ServicioPersistencia server;
	private SimpleDateFormat dateFormat;
	
	
	@Override
	public void registrarEtiqueta(Etiqueta etiqueta) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void borrarEtiqueta(Etiqueta etiqueta) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void modificarEtiqueta(Etiqueta etiqueta) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Etiqueta recuperarEtiqueta(int codigo) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Etiqueta> recuperarTodasEtiquetas() {
		// TODO Auto-generated method stub
		return null;
	}
	public static AdaptadorListaVideosTDS getInstancia() {
		return instancia;
	}
	public static void setInstancia(AdaptadorListaVideosTDS instancia) {
		AdaptadorEtiquetaTDS.instancia = instancia;
	}
	
	
	

}
