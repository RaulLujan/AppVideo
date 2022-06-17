package persistencia;

public class FactoriaTDS extends FactoriaDAO {

	public AdaptadorUsuarioDAO getUsuarioDAO() {
		return AdaptadorUsuarioTDS.getInstancia();
	}
	
	public AdaptadorVideoDAO getVideoDAO() {
		return AdaptadorVideoTDS.getInstancia();
	}

	@Override
	public AdaptadorEtiquetaDAO getEtiquetaDAO() {
		return AdaptadorEtiquetaTDS.getInstancia();
	}

	@Override
	public AdaptadorListaVideosDAO getListaVideosDAO() {
		return  AdaptadorListaVideosTDS.getInstancia();
	}
}
