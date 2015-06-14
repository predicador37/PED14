package es.uned.ped14.curriculum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Interfaz CurriculumRepositoryInterface, proporciona automáticamente los
 * métodos proporcionados por spring-data.
 */
@Transactional
public interface CurriculumRepositoryInterface extends
		JpaRepository<Curriculum, Long> {

	/**
	 * Find by user email. Encuentra un currículum dado el email del usuario
	 * asociado al mismo.
	 * 
	 * @param email
	 *            cadena con el email
	 * @return objeto de clase Curriculum
	 */
	Curriculum findByUserEmail(String email);

	/**
	 * Find by nombre. Encuentra un currículum dado el nombre del usuario
	 * asociado al mismo.
	 * 
	 * @param nombre
	 *            cadena de texto con el nombre del usuario
	 * @return primer objeto de clase Currículum encontrado.
	 */
	Curriculum findByNombre(String nombre);
}
