package persistencia;

import java.util.List;
import modelo.Usuario;

public interface AdaptadorUsuarioDAO {
	
	public void insertarUsuario(Usuario usuario);
	public void borrarUsuario(Usuario usuario);
	public void modificarUsuario(Usuario usuario);
	public Usuario consultarUsuario(int id);
	public List<Usuario> listarTodosUsuarios();
	
}
