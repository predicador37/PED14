package es.uned.ped14.titulacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import es.uned.ped14.curriculum.Curriculum;

/**
 * Interfaz ExperienciaProfesionalRepositoryInterface, proporciona
 * automáticamente los métodos proporcionados por spring-data.
 */
@Transactional
public interface TitulacionRepositoryInterface extends
		JpaRepository<Titulacion, Long> {

	/**
	 * Find by descripcion. Encuentra una serie de titulaciones dada la
	 * descripción asociada a las mismas.
	 * 
	 * @param descripcion
	 *            cadena de texto con la descripción.
	 * @return lista de titulaciones.
	 */
	List<Titulacion> findByDescripcion(String descripcion);

	/**
	 * Find by curriculum. Encuentra una serie de titulaciones dado el
	 * currículum asociado a las mismas.
	 * 
	 * @param curriculum
	 *            objeto con el currículum asociado,
	 * @return lista de titulaciones.
	 */
	List<Titulacion> findByCurriculum(Curriculum curriculum);

}
