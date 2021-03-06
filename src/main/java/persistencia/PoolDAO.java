package persistencia;

import java.util.Hashtable;

public class PoolDAO {
	
	private static PoolDAO instancia;
	public static PoolDAO getInstancia() {
		if (instancia == null)
			instancia = new PoolDAO();
		return instancia;
		
	}
	
	private Hashtable<Integer, Object> pool;

	private PoolDAO() {
		pool = new Hashtable<Integer, Object>();
	}

	public Object getObject(int id) {
		return pool.get(id); // devuelve null si no lo encuentra
	}

	public void addObject(int id, Object o) {
		pool.put(id, o);
	}

	public boolean contains(int id) {
		return pool.containsKey(id);
	}
}
