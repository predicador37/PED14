package es.uned.ped14.experiencia;

import java.text.ParseException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uned.ped14.curriculum.Curriculum;

/**
 * Clase ExperienciaProfesionalService, servicio que encapsula la lógica de
 * negocio de experiencia e interactúa con las capas inferiores (repositorios).
 */
@Service
public class ExperienciaProfesionalService {
	/** Logger para la depuración de errores */
	Logger logger = LoggerFactory
			.getLogger(ExperienciaProfesionalService.class);

	@Autowired
	private ExperienciaProfesionalRepositoryInterface experienciaProfesionalRepository;

	/**
	 * Initialize. Carga los datos iniciales.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 */
	@PostConstruct
	protected void initialize() throws ParseException {
		logger.info("Inicializar data para servicio de experiencia profesional");

	}

	/**
	 * Find by curriculum. Busca experiencias dado un currículum asociado.
	 * 
	 * @param curriculum
	 *            , objeto currículum asociado.
	 * @return lista de experiencias profesionales.
	 * @throws ExperienciaProfesionalNotFoundException
	 *             , excepción lanzada en caso de no encontrar ninguna
	 *             experiencia profesional.
	 */
	public List<ExperienciaProfesional> findByCurriculum(Curriculum curriculum)
			throws ExperienciaProfesionalNotFoundException {
		List<ExperienciaProfesional> experiencias = experienciaProfesionalRepository
				.findByCurriculum(curriculum);
		if (experiencias.isEmpty()) {
			throw new ExperienciaProfesionalNotFoundException(
					"Experiencia not found");
		}
		return experiencias;
	}

	/**
	 * Find by empresa. Busca conocimientos dado el nombre de una empresa
	 * asociada.
	 * 
	 * @param descripcion
	 *            , nombre de la empresa.
	 * @return lista de experiencias profesionales.
	 * @throws ExperienciaProfesionalNotFoundException
	 *             , excepción lanzada en caso de no encontrar ninguna
	 *             experiencia profesional.
	 */
	public List<ExperienciaProfesional> findByEmpresa(String empresa)
			throws ExperienciaProfesionalNotFoundException {
		List<ExperienciaProfesional> experiencia = experienciaProfesionalRepository
				.findByEmpresa(empresa);
		if (experiencia == null) {
			throw new ExperienciaProfesionalNotFoundException(
					"Experiencia not found");
		}
		return experiencia;
	}

	/**
	 * Find all. Devuelve un listado con todas las experiencias profesionales
	 * existentes.
	 * 
	 * @return lista de experiencias profesionales.
	 * @throws ExperienciaProfesionalNotFoundException
	 *             , excepción lanzada en caso de no encontrar ninguna
	 *             experiencia profesional.
	 */
	public List<ExperienciaProfesional> findAll()
			throws ExperienciaProfesionalNotFoundException {
		List<ExperienciaProfesional> experiencias = experienciaProfesionalRepository
				.findAll();
		if (experiencias.isEmpty()) {
			throw new ExperienciaProfesionalNotFoundException(
					"No se encontraron experiencias profesionales");
		}
		return experiencias;
	}

	/**
	 * Save. Persiste o actualiza una experiencia profesional.
	 * 
	 * @param experiencia
	 *            , objeto de clase ExperienciaProfesional.
	 */
	public void save(ExperienciaProfesional experiencia) {
		experienciaProfesionalRepository.save(experiencia);

	}

	/**
	 * Delete. Elimina un objeto experiencia profesional de la base de datos.
	 * 
	 * @param conocimiento
	 *            , objeto de la clase ExperienciaProfesional.
	 */
	public void delete(ExperienciaProfesional experiencia) {
		experienciaProfesionalRepository.delete(experiencia);

	}

	/**
	 * Find one. Busca una experiencia profesional dado su identificador.
	 * 
	 * @param id
	 *            , entero largo con el identificador de la experiencia
	 *            profesional.
	 * @return experiencia,la experiencia profesional encontrada.
	 * @throws ExperienciaProfesionalNotFoundException
	 *             , excepción lanzada en caso de no encontrar ninguna
	 *             experiencia profesional.
	 */
	public ExperienciaProfesional findOne(Long id)
			throws ExperienciaProfesionalNotFoundException {
		ExperienciaProfesional experiencia = experienciaProfesionalRepository
				.findOne(id);
		if (experiencia == null) {
			throw new ExperienciaProfesionalNotFoundException(
					"No se encontraron experiencias profesionales");
		}
		return experiencia;
	}

}
