package persistencia;

public class FactoriaTDS extends FactoriaDAO {
	
	public FactoriaTDS() {}

	@Override
	public AdaptadorUsuarioDAO getUsuarioDAO() {
		return AdaptadorUsuarioTDS.getInstancia();
	}
	
	@Override
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
