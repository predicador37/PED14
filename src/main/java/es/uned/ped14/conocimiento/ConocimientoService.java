package es.uned.ped14.conocimiento;

import java.text.ParseException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uned.ped14.curriculum.Curriculum;

/**
 * Clase ConocimientoService, servicio que encapsula la lógica de negocio de
 * conocimiento e interactúa con las capas inferiores (repositorios).
 */
@Service
public class ConocimientoService {

	/** Logger para la depuración de errores */
	Logger logger = LoggerFactory.getLogger(ConocimientoService.class);

	@Autowired
	private ConocimientoRepositoryInterface conocimientoRepository;

	/**
	 * Initialize. Carga los datos iniciales.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 */
	@PostConstruct
	protected void initialize() throws ParseException {
		logger.info("Inicializar data para servicio deconocimientos");

	}

	/**
	 * Find by curriculum. Busca conocimientos dado un currículum asociado.
	 * 
	 * @param curriculum
	 *            , objeto currículum asociado.
	 * @return lista de conocimientos.
	 * @throws ConocimientoNotFoundException
	 *             , excepción lanzada en caso de no encontrar ningún
	 *             conocimiento.
	 */
	public List<Conocimiento> findByCurriculum(Curriculum curriculum)
			throws ConocimientoNotFoundException {
		List<Conocimiento> conocimientos = conocimientoRepository
				.findByCurriculum(curriculum);
		if (conocimientos.isEmpty()) {
			throw new ConocimientoNotFoundException(
					"Conocimiento no encontrado.");
		}
		return conocimientos;
	}

	/**
	 * Find by descripcion. Busca conocimientos dada una descripción asociada.
	 * 
	 * @param descripcion
	 *            , descripción asociada al conocimiento.
	 * @return lista de conocimientos.
	 * @throws ConocimientoNotFoundException
	 *             , excepción lanzada en caso de no encontrar ningún
	 *             conocimiento.
	 */
	public List<Conocimiento> findByDescripcion(String descripcion)
			throws ConocimientoNotFoundException {
		List<Conocimiento> conocimientos = conocimientoRepository
				.findByDescripcion(descripcion);
		if (conocimientos.isEmpty()) {
			throw new ConocimientoNotFoundException(
					"Conocimiento no encontrado.");
		}
		return conocimientos;
	}

	/**
	 * Find all. Devuelve un listado con todos los conocimientos existentes.
	 * 
	 * @return lista de conocimientos.
	 * @throws ConocimientoNotFoundException
	 *             , excepción lanzada en caso de no encontrar ningún
	 *             conocimiento.
	 */
	public List<Conocimiento> findAll() throws ConocimientoNotFoundException {
		List<Conocimiento> conocimientos = conocimientoRepository.findAll();
		if (conocimientos.isEmpty()) {
			throw new ConocimientoNotFoundException(
					"No se encontraron conocimientos.");
		}
		return conocimientos;
	}

	/**
	 * Save. Persiste o actualiza un conocimiento.
	 * 
	 * @param conocimiento
	 *            , objeto de clase Conocimiento.
	 */
	public void save(Conocimiento conocimiento) {
		conocimientoRepository.save(conocimiento);

	}

	/**
	 * Delete. Elimina un objeto conocimiento de la base de datos.
	 * 
	 * @param conocimiento
	 *            , objeto de la clase Conocimiento.
	 */
	public void delete(Conocimiento conocimiento) {

		conocimientoRepository.delete(conocimiento);

	}

	/**
	 * Flush. Obliga a la capa de persistencia a escribir cualquier cambio en la
	 * base de datos.
	 */
	public void flush() {

		conocimientoRepository.flush();

	}

	/**
	 * Find one. Busca un conocimiento dado su identificador.
	 * 
	 * @param id
	 *            , entero largo con el identificador del conocimientro.
	 * @return conocimiento, el conocimiento encontrado.
	 * @throws ConocimientoNotFoundException
	 *             , excepción lanzada en caso de no encontrar ningún
	 *             conocimiento.
	 */
	public Conocimiento findOne(Long id) throws ConocimientoNotFoundException {
		Conocimiento conocimiento = conocimientoRepository.findOne(id);
		if (conocimiento == null) {
			throw new ConocimientoNotFoundException(
					"Conocimiento no encontrado");
		}
		return conocimiento;
	}

}
