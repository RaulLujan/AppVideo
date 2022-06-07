package persistencia;

import java.util.List;
import modelo.Usuario;

public interface AdaptadorUsuarioDAO {
	
	public void registrarUsuario(Usuario usuario);
	public void borrarUsuario(Usuario usuario);
	public void modificarUsuario(Usuario usuario);
	public Usuario recuperarUsuario(int id);
	public List<Usuario> recuperarTodosUsuarios();
	
}
