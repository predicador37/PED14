package es.uned.ped14.curriculum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uned.ped14.experiencia.ExperienciaProfesional;
import es.uned.ped14.experiencia.ExperienciaProfesionalRepositoryInterface;
import es.uned.ped14.titulacion.Titulacion;
import es.uned.ped14.titulacion.TitulacionRepositoryInterface;
import es.uned.ped14.account.Account;
import es.uned.ped14.account.AccountRepository;
import es.uned.ped14.conocimiento.Conocimiento;
import es.uned.ped14.conocimiento.NivelConocimiento;
import es.uned.ped14.curriculum.CurriculumNotFoundException;

@Service
public class CurriculumService {
	Logger logger = LoggerFactory.getLogger(CurriculumService.class);
	 
	@Autowired
	private CurriculumRepositoryInterface curriculumRepository;
	
	@Autowired
	private TitulacionRepositoryInterface titulacionRepository;
	
	@Autowired
	private ExperienciaProfesionalRepositoryInterface experienciaProfesionalRepository;
	
	
	@Autowired
	private CurriculumRepository curriculumRepositoryImp;
	
	@Autowired
	private CurriculumRepositoryInterface curriculumRepositoryInterface;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@PostConstruct	
	protected void initialize() throws ParseException {
		logger.info("Inicializar data para servicio de currículos");
		
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		ExperienciaProfesional experiencia1 = new ExperienciaProfesional("Becario", "ACME", "Gestión de cadena de producción", formatoFecha.parse("14/10/2008"), formatoFecha.parse("31/12/2012"));
		ExperienciaProfesional experiencia2 = new ExperienciaProfesional("Becario", "ACME", "Gestión de cadena de producción", formatoFecha.parse("14/10/2008"), formatoFecha.parse("31/12/2012"));
		ExperienciaProfesional experiencia3 = new ExperienciaProfesional("Director General", "ACME", "Jefe supremo", formatoFecha.parse("01/01/2013"), formatoFecha.parse("14/10/2015"));
		ExperienciaProfesional experiencia4 = new ExperienciaProfesional("Becaria", "Banco Santander", "Caja",  formatoFecha.parse("01/01/2000"), formatoFecha.parse("01/06/2010"));
		ExperienciaProfesional experiencia5 = new ExperienciaProfesional("Presidenta del Consejo de Administración", "Banco Santander", "Dueña del banco",  formatoFecha.parse("01/07/2010"), formatoFecha.parse("27/04/2015"));
		ExperienciaProfesional experiencia6 = new ExperienciaProfesional("Becario", "ACME", "Gestión de cadena de producción", formatoFecha.parse("14/10/2008"), formatoFecha.parse("31/12/2012"));
		
		Titulacion titulacion1 = new Titulacion("Ingeniero Informático", 2005);
		Titulacion titulacion2 = new Titulacion("Ingeniero de Telecomunicaciones", 2004);
		Titulacion titulacion3 = new Titulacion("Ingeniero Informático", 2005);
		Titulacion titulacion4 = new Titulacion("Ingeniero de Telecomunicaciones", 2004);
		
		Conocimiento conocimiento1 = new Conocimiento("java", NivelConocimiento.ALTO);
		Conocimiento conocimiento2 = new Conocimiento("unix", NivelConocimiento.ALTO);
		Conocimiento conocimiento3 = new Conocimiento("java", NivelConocimiento.BAJO);
		Conocimiento conocimiento4 = new Conocimiento("unix", NivelConocimiento.BAJO);
		
		Account user1 = new Account("miguel.exposito@gmail.com", "miguel", "ROLE_USER");
		Account user2 = new Account("anapa@gmail.com", "anapa", "ROLE_USER");
		
		Curriculum demoCurriculum1 = new Curriculum("Miguel", "Expósito", "España", "Santander", "htp://localhost/imagen.png", "http://localhost/archivo.pdf");
		Curriculum demoCurriculum2 = new Curriculum("Héctor", "Garnacho", "España", "Valladolid", "htp://localhost/imagen.png", "http://localhost/archivo.pdf");
		Curriculum demoCurriculum3 = new Curriculum("Marcos", "Azorí", "España", "Madrid", "htp://localhost/imagen.png", "http://localhost/archivo.pdf");
		Curriculum demoCurriculum4 = new Curriculum("Ana Patricia", "Botín", "España", "Santander", "htp://localhost/imagen.png", "http://localhost/archivo.pdf");
		Curriculum demoCurriculum5 = new Curriculum("Lucía", "Expósito", "España", "Santander", "htp://localhost/imagen.png", "http://localhost/archivo.pdf");
		
		accountRepository.save(user1);
		accountRepository.save(user2);
		titulacionRepository.save(titulacion1);
		titulacionRepository.save(titulacion2);
		titulacionRepository.save(titulacion1);
		titulacionRepository.save(titulacion2);
		curriculumRepository.save(demoCurriculum1);
		curriculumRepository.save(demoCurriculum2);
		curriculumRepository.save(demoCurriculum3);
		curriculumRepository.save(demoCurriculum4);
		curriculumRepository.save(demoCurriculum5);
		
		
		
		
		demoCurriculum1.setUser(user1);
		demoCurriculum1.addExperiencia(experiencia1);
		demoCurriculum1.addTitulacion(titulacion2);
		demoCurriculum1.addConocimiento(conocimiento2);
		demoCurriculum2.addExperiencia(experiencia2);
		demoCurriculum2.addTitulacion(titulacion1);
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
		
		//accountRepository.save(user1);
		curriculumRepository.save(demoCurriculum1);
		curriculumRepository.save(demoCurriculum2);
		curriculumRepository.save(demoCurriculum3);
		curriculumRepository.save(demoCurriculum4);
		curriculumRepository.save(demoCurriculum5);
		
	}
	 
	
	public Curriculum find(Long id) throws CurriculumNotFoundException {
		Curriculum curriculum = curriculumRepository.findOne(id);
		if(curriculum == null) {
			throw new CurriculumNotFoundException("curriculum not found");
		}
		return curriculum;
	}
	
	public Curriculum findByUserEmail(String email) throws CurriculumNotFoundException {
		Curriculum curriculum = curriculumRepositoryImp.findByUserEmail(email);
		if(curriculum == null) {
			throw new CurriculumNotFoundException("curriculum not found");
		}
		return curriculum;
	}
	
	/**
	 * Servicio que devuelve una lista con todos los currículos existentes
	 * @return List<Curriculum>
	 * @throws CurriculumNotFoundException
	 */
	public List<Curriculum> findAll() throws CurriculumNotFoundException {
		List<Curriculum> curriculos = curriculumRepository.findAll();
		if(curriculos.isEmpty()) {
			throw new CurriculumNotFoundException("curriculum not found");
		}
		return curriculos;
	}
	
	/**
	 * Servicio que devuelve una lista de currículos filtrados por país, ciudad de origen, experiencia
	 * total en años, titulación y conocimiento. Los parámetros pueden ser nulos, en cuyo caso no se filtrará por ellos.
	 * @param pais: String con el país de origen
	 * @param ciudad: String con la ciudad de origen
	 * @param experiencia: Integer con la experiencia en años
	 * @param titulacion: String con la titulación deseada; busca también si es una subcadena de la titulación
	 * @param conocimiento: String con el conocimiento deseado; busca también si es una subcadena del conocimiento
	 * @return List<Curriculum>
	 * @throws CurriculumNotFoundException
	 */
	public List<Curriculum> findByOptionalParameters(String pais, String ciudad, Integer experiencia, String titulacion, String conocimiento) throws CurriculumNotFoundException {
		
		Curriculum miguel = curriculumRepository.findByNombre("Miguel");
	    Curriculum anapa = curriculumRepository.findByNombre("Ana Patricia");
		List<Curriculum> curriculos = curriculumRepositoryImp.findByOptionalParameters(pais, ciudad, experiencia, titulacion, conocimiento);
		if(curriculos.isEmpty()) {
			throw new CurriculumNotFoundException("curriculum not found");
		}
		return curriculos;
	}
	
	/**
	 * Servicio que persiste un currículum dado este y sus experiencia profesional, titulación y conocimiento asociados
	 * @param curriculum: Curriculum con sus campos poblados
	 * @param experienciaProfesional: ExperienciaProfesional que se va a asociar al currículum. Puede ser null.
	 * @param titulacion: Titulación que se va a asociar al currículum. Puede ser null.
	 * @param conocimiento: Conocimiento que se va a asociar al currículum. Puede ser null.
	 * @throws CurriculumEmptyException
	 */
	public void save(Curriculum curriculum, Account usuario, ExperienciaProfesional experienciaProfesional, Titulacion titulacion, Conocimiento conocimiento) throws CurriculumEmptyException {
		
		logger.info("Inicializado servicio de almacenamiento de currículo");
		
		if (curriculum == null) throw new CurriculumEmptyException("Los datos para el currículum no existen");
		
		if (usuario != null) {
			curriculum.setUser(usuario);
		}
		if (experienciaProfesional != null){
		curriculum.addExperiencia(experienciaProfesional);
		}
		if (titulacion != null){
		titulacionRepository.save(titulacion);
		curriculumRepository.save(curriculum);
		curriculum.addTitulacion(titulacion);
		}
		if (conocimiento != null){
		curriculum.addConocimiento(conocimiento);
		}
		
		curriculumRepository.save(curriculum);
	}
	
	public void  save(Curriculum curriculum) {
		curriculumRepositoryInterface.save(curriculum);
		
	}
	

	public void  delete(Long id) {
		curriculumRepositoryInterface.delete(id);
		
	}
	
	public Curriculum  findOne(Long id) {
		return curriculumRepositoryInterface.findOne(id);
		
	}

}
