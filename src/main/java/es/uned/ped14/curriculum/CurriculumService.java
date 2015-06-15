package es.uned.ped14.curriculum;

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
import es.uned.ped14.conocimiento.Conocimiento;
import es.uned.ped14.conocimiento.NivelConocimiento;
import es.uned.ped14.curso.CursoFormacion;
import es.uned.ped14.experiencia.ExperienciaProfesional;
import es.uned.ped14.experiencia.ExperienciaProfesionalRepositoryInterface;
import es.uned.ped14.titulacion.Titulacion;
import es.uned.ped14.titulacion.TitulacionRepositoryInterface;

/**
 * Clase CurriculumService, servicio que encapsula la lógica de negocio de
 * currículum e interactúa con las capas inferiores (repositorios).
 */
@Service
public class CurriculumService {

	/** Logger para la depuración de errores */
	Logger logger = LoggerFactory.getLogger(CurriculumService.class);

	@Autowired
	private CurriculumRepositoryInterface curriculumRepository;

	@Autowired
	private TitulacionRepositoryInterface titulacionRepository;

	@Autowired
	private ExperienciaProfesionalRepositoryInterface experienciaProfesionalRepository;

	@Autowired
	private RoleRepositoryInterface roleRepository;

	@Autowired
	private CurriculumRepository curriculumRepositoryImp;

	@Autowired
	private CurriculumRepositoryInterface curriculumRepositoryInterface;

	@Autowired
	private AccountRepository accountRepository;

	/**
	 * Inicialización con los datos de prueba.
	 *
	 * @throws ParseException
	 *             excepción de parseo.
	 */
	@PostConstruct
	protected void initialize() throws ParseException {
		logger.info("Inicializar data para servicio de currículos");

		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		ExperienciaProfesional experiencia1 = new ExperienciaProfesional(
				"Becario", "ACME", "Gestión de cadena de producción",
				formatoFecha.parse("14/10/2008"),
				formatoFecha.parse("31/12/2012"));
		ExperienciaProfesional experiencia2 = new ExperienciaProfesional(
				"Becario", "ACME", "Gestión de cadena de producción",
				formatoFecha.parse("14/10/2008"),
				formatoFecha.parse("31/12/2012"));
		ExperienciaProfesional experiencia3 = new ExperienciaProfesional(
				"Director General", "ACME", "Jefe supremo",
				formatoFecha.parse("01/01/2013"),
				formatoFecha.parse("14/10/2015"));
		ExperienciaProfesional experiencia4 = new ExperienciaProfesional(
				"Becaria", "Banco Santander", "Caja",
				formatoFecha.parse("01/01/2000"),
				formatoFecha.parse("01/06/2010"));
		ExperienciaProfesional experiencia5 = new ExperienciaProfesional(
				"Presidenta del Consejo de Administración", "Banco Santander",
				"Dueña del banco", formatoFecha.parse("01/07/2010"),
				formatoFecha.parse("27/04/2015"));
		ExperienciaProfesional experiencia6 = new ExperienciaProfesional(
				"Becario", "ACME", "Gestión de cadena de producción",
				formatoFecha.parse("14/10/2008"),
				formatoFecha.parse("31/12/2012"));

		Titulacion titulacion1 = new Titulacion("Ingeniero Informático", 2005);
		Titulacion titulacion2 = new Titulacion(
				"Ingeniero de Telecomunicaciones", 2004);
		Titulacion titulacion3 = new Titulacion("Ingeniero Informático", 2005);
		Titulacion titulacion4 = new Titulacion(
				"Ingeniero de Telecomunicaciones", 2004);

		CursoFormacion curso1 = new CursoFormacion(
				"Curso de desarrollo ágil de software", (Integer) 20,
				formatoFecha.parse("05/06/2015"));
		CursoFormacion curso2 = new CursoFormacion("Curso de java",
				(Integer) 20, formatoFecha.parse("05/06/2015"));

		Conocimiento conocimiento1 = new Conocimiento("java",
				NivelConocimiento.ALTO);
		Conocimiento conocimiento2 = new Conocimiento("unix",
				NivelConocimiento.ALTO);
		Conocimiento conocimiento3 = new Conocimiento("java",
				NivelConocimiento.BAJO);
		Conocimiento conocimiento4 = new Conocimiento("unix",
				NivelConocimiento.BAJO);

		Role role1 = new Role("ROLE_USER");
		Role role2 = new Role("ROLE_USER");
		Role role3 = new Role("ROLE_CREATE");
		Role role4 = new Role("ROLE_CREATE");
		Role role5 = new Role("ROLE_EDIT");
		Role role6 = new Role("ROLE_EDIT");
		Role role7 = new Role("ROLE_DELETE");
		Role role8 = new Role("ROLE_DELETE");
		Account user1 = new Account("miguel.exposito@gmail.com", "miguel");
		Account user2 = new Account("anapa@gmail.com", "anapa");
		user1.addRole(role1);
		user2.addRole(role2);
		user1.addRole(role3);
		user2.addRole(role4);
		user1.addRole(role5);
		user2.addRole(role6);
		user1.addRole(role7);
		user2.addRole(role8);

		Curriculum demoCurriculum1 = new Curriculum("Miguel", "Expósito",
				"España", "Santander", "htp://localhost/imagen.png",
				"http://localhost/archivo.pdf");
		Curriculum demoCurriculum2 = new Curriculum("Héctor", "Garnacho",
				"España", "Valladolid", "htp://localhost/imagen.png",
				"http://localhost/archivo.pdf");
		Curriculum demoCurriculum3 = new Curriculum("Marcos", "Azorí",
				"España", "Madrid", "htp://localhost/imagen.png",
				"http://localhost/archivo.pdf");
		Curriculum demoCurriculum4 = new Curriculum("Ana Patricia", "Botín",
				"España", "Santander", "htp://localhost/imagen.png",
				"http://localhost/archivo.pdf");
		Curriculum demoCurriculum5 = new Curriculum("Lucía", "Expósito",
				"España", "Santander", "htp://localhost/imagen.png",
				"http://localhost/archivo.pdf");

		accountRepository.save(user1);
		accountRepository.save(user2);

		demoCurriculum1.setUser(user1);
		demoCurriculum1.addExperiencia(experiencia1);
		demoCurriculum1.addTitulacion(titulacion2);
		demoCurriculum1.addConocimiento(conocimiento2);
		demoCurriculum2.addExperiencia(experiencia2);
		demoCurriculum2.addTitulacion(titulacion1);
		demoCurriculum2.addCurso(curso1);
		demoCurriculum2.addConocimiento(conocimiento1);
		demoCurriculum1.addExperiencia(experiencia3);
		demoCurriculum4.addExperiencia(experiencia4);
		demoCurriculum4.setUser(user2);
		demoCurriculum4.addTitulacion(titulacion4);
		demoCurriculum4.addConocimiento(conocimiento4);
		demoCurriculum4.addExperiencia(experiencia5);
		demoCurriculum5.addExperiencia(experiencia6);
		demoCurriculum3.addTitulacion(titulacion3);
		demoCurriculum3.addConocimiento(conocimiento3);

		// accountRepository.save(user1);
		curriculumRepository.save(demoCurriculum1);
		curriculumRepository.save(demoCurriculum2);
		curriculumRepository.save(demoCurriculum3);
		curriculumRepository.save(demoCurriculum4);
		curriculumRepository.save(demoCurriculum5);

	}

	/**
	 * Find. busca un currículum dado su identificador.
	 *
	 * @param id
	 *            , entero con el identificador.
	 * @return currículum encontrado, de clase Curriculum.
	 * @throws CurriculumNotFoundException
	 *             , excepción en caso de no encontrar ningún currículum.
	 */
	public Curriculum find(Long id) throws CurriculumNotFoundException {
		Curriculum curriculum = curriculumRepository.findOne(id);
		if (curriculum == null) {
			throw new CurriculumNotFoundException("curriculum not found");
		}
		return curriculum;
	}

	/**
	 * Find by user email. Busca un currículo dado el email de su usuario
	 * asociado.
	 *
	 * @param email
	 *            , cadena de texto con el email.
	 * @return curriculum asociado.
	 * @throws CurriculumNotFoundException
	 *             , excepción en caso de no encontrar ningún currículum.
	 */
	public Curriculum findByUserEmail(String email)
			throws CurriculumNotFoundException {
		Curriculum curriculum = curriculumRepositoryImp.findByUserEmail(email);
		if (curriculum == null) {
			throw new CurriculumNotFoundException("curriculum not found");
		}
		return curriculum;
	}

	/**
	 * Servicio que devuelve una lista con todos los currículos existentes.
	 *
	 * @return lista de currículos ordenada descendentemente por experiencia.
	 * @throws CurriculumNotFoundException
	 *             , excepción en caso de no encontrar ningún currículum.
	 */
	public List<Curriculum> findAll() throws CurriculumNotFoundException {
		List<Curriculum> curriculos = curriculumRepository.findAllByOrderByExperienciaDesc();
		if (curriculos.isEmpty()) {
			throw new CurriculumNotFoundException("curriculum not found");
		}
		return curriculos;
	}

	/**
	 * Servicio que devuelve una lista de currículos filtrados por país, ciudad
	 * de origen, experiencia total en años, titulación y conocimiento. Los
	 * parámetros pueden ser nulos, en cuyo caso no se filtrará por ellos.
	 *
	 * @param pais
	 *            , cadena de texto con el país a buscar.
	 * @param ciudad
	 *            , cadena de texto con la ciudad a buscar.
	 * @param experiencia
	 *            , entero con la experiencia mínima a partir de la cual
	 *            recuperar currículos.
	 * @param titulacion
	 *            , cadena de texto con la titulación a buscar.
	 * @param conocimiento
	 *            , cadena de texto con el conocimiento a buscar.
	 * @return lista de currículos.
	 */
	public List<Curriculum> findByOptionalParameters(String pais,
			String ciudad, Integer experiencia, String titulacion,
			String conocimiento) {

		List<Curriculum> curriculos = curriculumRepositoryImp
				.findByOptionalParameters(pais, ciudad, experiencia,
						titulacion, conocimiento);
		return curriculos;
	}

	/**
	 * Servicio que persiste un currículum dado este y sus experiencia
	 * profesional, titulación y conocimiento asociados.
	 *
	 * @param curriculum
	 *            , objeto con el currículum a guardar.
	 * @param usuario
	 *            , objeto de clase Account, con el usuario asociado.
	 * @param experienciaProfesional
	 *            , objeto con la experiencia profesional a guardar.
	 * @param titulacion
	 *            , objeto con la titulación a guardar.
	 * @param conocimiento
	 *            , objeto con el conocimiento a guardar.
	 * @throws CurriculumEmptyException
	 *             , excepción en caso de que los datos del currículum estén
	 *             vacíos.
	 */
	public void save(Curriculum curriculum, Account usuario,
			ExperienciaProfesional experienciaProfesional,
			Titulacion titulacion, Conocimiento conocimiento)
			throws CurriculumEmptyException {

		logger.info("Inicializado servicio de almacenamiento de currículo");

		if (curriculum == null)
			throw new CurriculumEmptyException(
					"Los datos para el currículum no existen");

		if (usuario != null) {
			curriculum.setUser(usuario);
		}
		if (experienciaProfesional != null) {
			curriculum.addExperiencia(experienciaProfesional);
		}
		if (titulacion != null) {
			titulacionRepository.save(titulacion);
			curriculumRepository.save(curriculum);
			curriculum.addTitulacion(titulacion);
		}
		if (conocimiento != null) {
			curriculum.addConocimiento(conocimiento);
		}

		curriculumRepository.save(curriculum);
	}

	/**
	 * Save. Persiste o actualiza un objeto de tipo currículum.
	 *
	 * @param curriculum
	 * 
	 */
	public void save(Curriculum curriculum) {
		curriculumRepositoryInterface.save(curriculum);

	}

	/**
	 * Delete. Elimina un objeto de tipo currículum dado su identificador.
	 * 
	 * @param id
	 */
	public void delete(Long id) {
		curriculumRepositoryInterface.delete(id);

	}

	/**
	 * Flush. Obliga a la capa de persistencia a escribir los datos en la base
	 * de datos.
	 */
	public void flush() {
		curriculumRepositoryInterface.flush();

	}

	/**
	 * Find one. Busca un único currículo dado su identificador.
	 * 
	 * @param id
	 *            , el identificador.
	 * @return curriculum, el objeto currículum encontrado.
	 * @throws CurriculumNotFoundException
	 *             , excepción a lanzar en caso de no encontrar currículum.
	 */
	public Curriculum findOne(Long id) throws CurriculumNotFoundException {

		Curriculum curriculum = curriculumRepositoryInterface.findOne(id);
		if (curriculum == null) {
			throw new CurriculumNotFoundException(
					"No se encuentra el curriculum especificado");
		}
		return curriculum;
	}

}
