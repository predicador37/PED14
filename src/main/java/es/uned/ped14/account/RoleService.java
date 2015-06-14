package es.uned.ped14.account;

import java.text.ParseException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Clase RoleService, servicio que encapsula la lógica de negocio de role e
 * interactúa con las capas inferiores (repositorios).
 */
@Service
public class RoleService {

	@Autowired
	private RoleRepositoryInterface roleRepository;

	/**
	 * Initialize. Carga los datos iniciales.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 */
	@PostConstruct
	protected void initialize() throws ParseException {

	}

	/**
	 * Save. Persiste o actualiza un rol.
	 * 
	 * @param role
	 *            , objeto de clase Role.
	 */
	public void save(Role role) {
		roleRepository.save(role);

	}

	/**
	 * Find one. Busca un conocimiento dado su identificador.
	 * 
	 * @param id
	 *            , entero largo con el identificador delrol.
	 * @return rol, el rol encontrado.
	 * 
	 */
	public Role findOne(Long id) {
		return roleRepository.findOne(id);

	}

	/**
	 * Flush. Obliga a la capa de persistencia a escribir cualquier cambio en la
	 * base de datos.
	 */
	public void flush() {
		roleRepository.flush();

	}

	/**
	 * Delete. Elimina un objeto rol de la base de datos.
	 * 
	 * @param role
	 *            , objeto de la clase Role.
	 */
	public void delete(Role role) {
		roleRepository.delete(role);

	}

	/**
	 * Find by descripcion and user. Busca un conocimiento dada una descripción
	 * y un usuario asociados.
	 * 
	 * @param descripcion
	 *            , descripción asociada al rol.
	 * @param user
	 *            , cuenta de usuario asociada al rol.
	 * @return lista de roles.
	 */
	public Role findByDescripcionAndUser(String descripcion, Account user) {
		return roleRepository.findByDescripcionAndUser(descripcion, user);
	}

	/**
	 * Find by descripcion. Busca roles dada una descripción asociada.
	 * 
	 * @param descripcion
	 *            , descripción asociada al rol.
	 * @return lista de roles.
	 */
	public List<Role> findByDescripcion(String descripcion) {
		return roleRepository.findByDescripcion(descripcion);
	}
}
