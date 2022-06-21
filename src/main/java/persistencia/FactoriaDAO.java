package persistencia;

public abstract class FactoriaDAO {
	
	public static final String TDS_DAO = "persistencia.FactoriaTDS";
	private static FactoriaDAO instancia = null;
	public static FactoriaDAO getInstancia(String tipo) {
		if (instancia == null) {
			try {
				//Constructor<?> constructor = Class.forName(tipo).getConstructor();
				//instancia = (FactoriaDAO) constructor.newInstance();
				
				instancia = (FactoriaDAO) Class.forName(tipo).newInstance();
			} catch (Exception e) {
				e.getMessage();
			}
		}

		return instancia;
	}
	public static FactoriaDAO getInstancia() {
		if (instancia == null)
			return getInstancia(FactoriaDAO.TDS_DAO);
		else
			return instancia;
	}

	protected FactoriaDAO() {
	}

	public abstract AdaptadorUsuarioDAO getUsuarioDAO();
	public abstract AdaptadorEtiquetaDAO getEtiquetaDAO();
	public abstract AdaptadorVideoDAO getVideoDAO();
	public abstract AdaptadorListaVideosDAO getListaVideosDAO();
}
