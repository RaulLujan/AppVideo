package persistencia;

public class FactoriaTDS extends FactoriaDAO {

	public AdaptadorUsuarioDAO getUsuarioDAO() {
		return AdaptadorUsuarioTDS.getInstancia();
	}
}
