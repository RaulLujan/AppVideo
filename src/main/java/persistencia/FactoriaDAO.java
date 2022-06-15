package persistencia;

import java.lang.reflect.Constructor;

import javax.swing.JPanel;

public abstract class FactoriaDAO {
	public static final String TDS_DAO = "persistencia.FactoriaTDS";
	private static FactoriaDAO instancia = null;
	
	public static FactoriaDAO getInstancia(String tipo) {
		if (instancia == null) {
			try {
				Constructor<?> constructor = Class.forName(tipo).getConstructor();
				instancia = (FactoriaDAO) constructor.newInstance();
				
			} catch (Exception e) {
				e.getMessage();
			}
		}
		
		return instancia;
	}
	
	public static FactoriaDAO getInstancia(){
		return getInstancia(FactoriaDAO.TDS_DAO);
	}

	protected FactoriaDAO (){}
	
	public abstract AdaptadorUsuarioDAO getUsuarioDAO();	
}