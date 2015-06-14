package es.uned.ped14.curso;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import es.uned.ped14.curriculum.Curriculum;

/**
 * Interfaz CursoFormacionRepositoryInterface, proporciona automáticamente los
 * métodos proporcionados por spring-data.
 */
@Transactional
public interface CursoFormacionRepositoryInterface extends
		JpaRepository<CursoFormacion, Long> {
	/**
	 * Find by curriculum. Encuentra una serie de cursos dado el currículum
	 * asociado a los mismos.
	 * 
	 * @param curriculum
	 *            objeto con el currículum asociado
	 * @return lista de cursos
	 */
	List<CursoFormacion> findByCurriculum(Curriculum curriculum);

	/**
	 * Find by descripcion. Encuentra una serie de cursos dada la descripción
	 * asociada a los mismos.
	 * 
	 * @param descripcionm
	 *            cadena de texto con la descripción.
	 * @return lista de cursos.
	 */
	List<CursoFormacion> findByDescripcion(String descripcion);

}
