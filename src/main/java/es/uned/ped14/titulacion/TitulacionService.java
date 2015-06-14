package es.uned.ped14.titulacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uned.ped14.experiencia.ExperienciaProfesional;
import es.uned.ped14.experiencia.ExperienciaProfesionalNotFoundException;
import es.uned.ped14.titulacion.Titulacion;
import es.uned.ped14.account.Account;
import es.uned.ped14.account.AccountRepository;
import es.uned.ped14.account.Role;
import es.uned.ped14.account.UserService;
import es.uned.ped14.conocimiento.Conocimiento;
import es.uned.ped14.conocimiento.ConocimientoRepositoryInterface;
import es.uned.ped14.conocimiento.NivelConocimiento;
import es.uned.ped14.curriculum.Curriculum;
import es.uned.ped14.curriculum.CurriculumNotFoundException;
import es.uned.ped14.curriculum.CurriculumRepository;
import es.uned.ped14.curriculum.CurriculumRepositoryInterface;
import es.uned.ped14.curriculum.CurriculumService;
import es.uned.ped14.curso.CursoFormacionNotFoundException;
/**
 * Clase TitulacionService, servicio que encapsula la lógica de
 * negocio de titulación e interactúa con las capas inferiores (repositorios).
 */
@Service
public class TitulacionService {
	
	/** Logger para la depuración de errores */
	Logger logger = LoggerFactory.getLogger(TitulacionService.class);
	 
	@Autowired
	private TitulacionRepositoryInterface titulacionRepository;
	
	@Autowired
	private ConocimientoRepositoryInterface conocimientoRepository;

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private CurriculumRepositoryInterface curriculumRepository;
	
	/**
	 * Initialize. Carga los datos iniciales.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 */
	@PostConstruct
	 @Transactional
	protected void initialize() throws ParseException {
		logger.info("Inicializar data para servicio de titulaciones");
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		Role role1 = new Role("ROLE_USER");
		Account user1 = new Account("jose@gmail.com", "jose");
		user1.addRole(role1);
		Curriculum curriculum = new Curriculum("Don José", "Pérez", "España", "Oviedo", "htp://localhost/imagen.png", "http://localhost/archivo.pdf");
		curriculum.setUser(user1);
		Titulacion titulacion = new Titulacion("Ingeniero de obras públicas", 2008);
		Conocimiento conocimiento = new Conocimiento("unix", NivelConocimiento.ALTO);
		accountRepository.save(user1);
		conocimientoRepository.save(conocimiento);
		titulacionRepository.save(titulacion);
		curriculumRepository.save(curriculum);
		curriculum.addTitulacion(titulacion);
		curriculum.addConocimiento(conocimiento);
		titulacionRepository.save(titulacion);
		curriculumRepository.save(curriculum);

	}
	/**
	 * Find by descripcion. Busca titulaciones dada una descripción asociada.
	 * 
	 * @param descripcion
	 *            , descripción asociada a la titulacion.
	 * @return lista de cursos.
	 * @throws TitulacionNotFoundException
	 *             , excepción lanzada en caso de no encontrar ningún titulación.
	 */
	public List<Titulacion> findByDescripcion(String descripcion) throws TitulacionNotFoundException {
		List<Titulacion> titulacion = titulacionRepository.findByDescripcion(descripcion);
		if(titulacion == null) {
			throw new TitulacionNotFoundException("titulacion not found");
		}
		return titulacion;
	}
	/**
	 * Find all. Devuelve un listado con todas las titulaciones
	 * existentes.
	 * 
	 * @return lista de titulaciones.
	 * @throws TitulacionNotFoundException
	 *             , excepción lanzada en caso de no encontrar ninguna
	 *             titulación.
	 */
	public List<Titulacion> findAll() throws TitulacionNotFoundException {
		List<Titulacion> titulaciones = titulacionRepository.findAll();
		if(titulaciones.isEmpty()) {
			throw new TitulacionNotFoundException("No se encontraron titulaciones");
		}
		return titulaciones;
	}
	/**
	 * Save. Persiste o actualiza una titulación.
	 * 
	 * @param curso
	 *            , objeto de clase Titulacion.
	 */
	public void  save(Titulacion titulacion) {
		titulacionRepository.save(titulacion);
		
	}
	/**
	 * Delete. Elimina un objeto titulacion de la base de datos.
	 * 
	 * @param curso
	 *            , objeto de la clase Titulacion.
	 */
	public void  delete(Titulacion titulacion) {
		titulacionRepository.delete(titulacion);
		
	}
	
	/**
	 * Find one. Busca una titulación dado su identificador.
	 * 
	 * @param id
	 *            , entero largo con el identificador de la titulación.
	 * @return curso, el curso encontrado.
	 * @throws TItulacionNotFoundException
	 *             , excepción lanzada en caso de no encontrar ninguna titulación.
	 */
	public Titulacion  findOne(Long id) throws TitulacionNotFoundException {
		Titulacion titulacion = titulacionRepository.findOne(id);
		if(titulacion == null) {
			throw new TitulacionNotFoundException("titulacion not found");
		}
		return titulacion;
	}
	/**
	 * Find by curriculum. Busca titulaciomnes dado un currículum asociado.
	 * 
	 * @param curriculum
	 *            , objeto currículum asociado.
	 * @return lista de titulaciones.
	 * @throws TitulacionNotFoundException
	 *             , excepción lanzada en caso de no encontrar ninguna
	 *             titulación.
	 */
	public List<Titulacion> findByCurriculum(Curriculum curriculum) throws TitulacionNotFoundException {
		List<Titulacion> titulaciones = titulacionRepository.findByCurriculum(curriculum);
		if(titulaciones.isEmpty()) {
			throw new TitulacionNotFoundException("No se encontraron titulaciones");
		}
		return titulaciones;
	}
	
	
	/**
	 * Like. Incrementa el número de likes del elemento.
	 * 
	 * @param curso
	 *            , objeto de clase Titulación cuyo número de likes se incrementará.
	 * @return entero con el número de likes.
	 * @throws TitulaciónNotFoundException
	 *             , excepción lanzada en caso de no encontrar ninguna titulación.
	 */
	public Integer like(Titulacion titulacion) {
		
		titulacion.like();
		titulacionRepository.save(titulacion);
		return titulacion.getLikes();
	}
	
	}
