package es.uned.ped14.experiencia;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import es.uned.ped14.curriculum.Curriculum;
/**
 * Interfaz ExperienciaProfesionalRepositoryInterface, proporciona automáticamente los
 * métodos proporcionados por spring-data.
 */
@Transactional
public interface ExperienciaProfesionalRepositoryInterface extends JpaRepository<ExperienciaProfesional, Long>{
	
	/**
	 * Find by curriculum. Encuentra una serie de cursos dado el currículum
	 * asociado a los mismos.
	 * 
	 * @param curriculum
	 *            objeto con el currículum asociado
	 * @return lista de cursos
	 */
	List<ExperienciaProfesional> findByCurriculum(Curriculum curriculum);
	
	/**
	 * Find by empresa. Encuentra una serie de experiencias dada la empresa
	 * asociada a los mismos.
	 * 
	 * @param empresa
	 *            cadena de texto con el nombre de la empresa.
	 * @return lista de experiencias profesionales.
	 */
	List<ExperienciaProfesional> findByEmpresa(String empresa);
	
		
	
}
