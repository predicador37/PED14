package es.uned.ped14.conocimiento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import es.uned.ped14.curriculum.Curriculum;

/**
 * Interfaz CurriculumRepositoryInterface, proporciona automáticamente los
 * métodos proporcionados por spring-data.
 */
@Transactional
public interface ConocimientoRepositoryInterface extends
		JpaRepository<Conocimiento, Long> {
	/**
	 * Find by curriculum. Encuentra una serie de conocimientos dado el
	 * currículum asociado a los mismos.
	 * 
	 * @param curriculum
	 *            objeto con el currículum asociado
	 * @return lista de conocimientos
	 */
	List<Conocimiento> findByCurriculum(Curriculum curriculum);

	/**
	 * Find by descripcion. Encuentra una serie de conocimientos dada la
	 * descripción asociada a los mismos.
	 * 
	 * @param descripcion
	 *            cadena de texto con la descripción.
	 * @return lista de conocimientos.
	 */
	List<Conocimiento> findByDescripcion(String descripcion);

}
