package es.uned.ped14.account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interfaz RoleRepositoryInterface, proporciona automáticamente los métodos
 * proporcionados por spring-data.
 */
@Transactional
public interface RoleRepositoryInterface extends JpaRepository<Role, Long> {

	/**
	 * Find by descripcion. Encuentra una serie de roles dada la descripción
	 * asociada a los mismos.
	 * 
	 * @param descripcion
	 *            cadena de texto con la descripción.
	 * @return lista de roles.
	 */
	List<Role> findByDescripcion(String descripcion);

	/**
	 * Find by descripcion and user. Encuentra una serie de roles dada la
	 * descripción y el usuario asociados a los mismos.
	 * 
	 * @param descripcion
	 *            cadena de texto con la descripción.
	 * @param user
	 *            objeto usuario de clase Account.
	 * @return lista de roles.
	 */
	Role findByDescripcionAndUser(String descripcion, Account user);

}
