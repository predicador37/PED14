package es.uned.ped14.curso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uned.ped14.account.Account;
import es.uned.ped14.account.AccountRepository;
import es.uned.ped14.account.Role;
import es.uned.ped14.account.RoleRepositoryInterface;
import es.uned.ped14.curriculum.Curriculum;
import es.uned.ped14.curriculum.CurriculumRepositoryInterface;
import es.uned.ped14.titulacion.Titulacion;

/**
 * Clase CursoFormacionService, servicio que encapsula la lógica de negocio de
 * curso e interactúa con las capas inferiores (repositorios).
 */
@Service
public class CursoFormacionService {

	/** Logger para la depuración de errores */
	Logger logger = LoggerFactory.getLogger(CursoFormacionService.class);

	@Autowired
	private CursoFormacionRepositoryInterface cursoFormacionRepository;

	@Autowired
	private CurriculumRepositoryInterface curriculumRepository;

	@Autowired
	private AccountRepository accountRepository;

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
		logger.info("Inicializar data para servicio de cursos");
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		Role role1 = new Role("ROLE_USER");
		Account user1 = new Account("miguel@gmail.com", "miguel");
		user1.addRole(role1);
		Curriculum curriculum = new Curriculum("Manuel", "Gutiérrez", "España",
				"Valladolid", "htp://localhost/imagen.png",
				"http://localhost/archivo.pdf");
		curriculum.setUser(user1);
		CursoFormacion curso = new CursoFormacion(
				"Curso de desarrollo ágil de software", (Integer) 20,
				formatoFecha.parse("05/06/2015"));
		curso.setCurriculum(curriculum);
		accountRepository.save(user1);
		curriculumRepository.save(curriculum);
		cursoFormacionRepository.save(curso);

	}

	/**
	 * Find by curriculum. Busca cursos dado un currículum asociado.
	 * 
	 * @param curriculum
	 *            , objeto currículum asociado.
	 * @return lista de conocimientos.
	 * @throws CursoFormacionNotFoundException
	 *             , excepción lanzada en caso de no encontrar ningún curso.
	 */
	public List<CursoFormacion> findByCurriculum(Curriculum curriculum)
			throws CursoFormacionNotFoundException {
		List<CursoFormacion> cursos = cursoFormacionRepository
				.findByCurriculum(curriculum);
		if (cursos.isEmpty()) {
			throw new CursoFormacionNotFoundException("curso not found");
		}
		return cursos;
	}

	/**
	 * Find by descripcion. Busca cursos dada una descripción asociada.
	 * 
	 * @param descripcion
	 *            , descripción asociada al curso.
	 * @return lista de cursos.
	 * @throws CursoFormacionNotFoundException
	 *             , excepción lanzada en caso de no encontrar ningún curso.
	 */
	public List<CursoFormacion> findByDescripcion(String descripcion)
			throws CursoFormacionNotFoundException {
		List<CursoFormacion> curso = cursoFormacionRepository
				.findByDescripcion(descripcion);
		if (curso == null) {
			throw new CursoFormacionNotFoundException("curso not found");
		}
		return curso;
	}

	/**
	 * Find all. Devuelve un listado con todos los cursos existentes.
	 * 
	 * @return lista de cursos.
	 * @throws CursoFormacionNotFoundException
	 *             , excepción lanzada en caso de no encontrar ningún curso.
	 */
	public List<CursoFormacion> findAll()
			throws CursoFormacionNotFoundException {
		List<CursoFormacion> cursos = cursoFormacionRepository.findAll();
		if (cursos.isEmpty()) {
			throw new CursoFormacionNotFoundException(
					"No se encontraron cursos de formación.");
		}
		return cursos;
	}

	/**
	 * Save. Persiste o actualiza un curso.
	 * 
	 * @param curso
	 *            , objeto de clase CursoFormacion.
	 */
	public void save(CursoFormacion curso) {
		cursoFormacionRepository.save(curso);

	}

	/**
	 * Delete. Elimina un objeto curso de la base de datos.
	 * 
	 * @param curso
	 *            , objeto de la clase CursoFormacion.
	 */
	public void delete(CursoFormacion curso) {
		cursoFormacionRepository.delete(curso);

	}

	/**
	 * Find one. Busca un curso dado su identificador.
	 * 
	 * @param id
	 *            , entero largo con el identificador del curso.
	 * @return curso, el curso encontrado.
	 * @throws CursoFormacionNotFoundException
	 *             , excepción lanzada en caso de no encontrar ningún curso.
	 */
	public CursoFormacion findOne(Long id)
			throws CursoFormacionNotFoundException {

		CursoFormacion curso = cursoFormacionRepository.findOne(id);
		if (curso == null) {
			throw new CursoFormacionNotFoundException(
					"Curso de formación no encontrado");
		}
		return curso;

	}
	
	/**
	 * Like. Incrementa el número de likes del elemento.
	 * 
	 * @param curso
	 *            , objeto de clase Curso cuyo número de likes se incrementará.
	 * @return entero con el número de likes.
	 * @throws CursoFormacionNotFoundException
	 *             , excepción lanzada en caso de no encontrar ningún curso.
	 */
	public Integer like(CursoFormacion curso) {
		
		curso.like();
		cursoFormacionRepository.save(curso);
		return curso.getLikes();
	}

}
