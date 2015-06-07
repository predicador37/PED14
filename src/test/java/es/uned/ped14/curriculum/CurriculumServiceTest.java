package es.uned.ped14.curriculum;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.uned.ped14.account.Account;
import es.uned.ped14.account.AccountRepository;
import es.uned.ped14.config.ApplicationConfig;
import es.uned.ped14.config.JpaConfig;
import es.uned.ped14.config.SecurityConfig;
import es.uned.ped14.conocimiento.Conocimiento;
import es.uned.ped14.conocimiento.NivelConocimiento;
import es.uned.ped14.experiencia.ExperienciaProfesional;
import es.uned.ped14.experiencia.ExperienciaProfesionalRepository;
import es.uned.ped14.titulacion.Titulacion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, JpaConfig.class, SecurityConfig.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class CurriculumServiceTest {
	 Logger logger = LoggerFactory.getLogger(CurriculumServiceTest.class);

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private CurriculumRepository curriculumRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private CurriculumService curriculumService;
	
	@Autowired
	private ExperienciaProfesionalRepository experienciaProfesionalRepository;

	@Before
	public void setUp() throws ParseException {
		
	}

	@Test
	public void shouldReturnCurriculumById() throws CurriculumNotFoundException {
		logger.info("shouldReturnCurriculumById");
		// arrange
		Curriculum demoCurriculum = new Curriculum("Miguel", "Expósito Martín", "España", "Santander", "htp://localhost/imagen.png", "http://localhost/archivo.pdf");

		// act
		Curriculum curriculum = curriculumService.find(1L);

		// assert
		assertThat(demoCurriculum.getNombre()).isEqualTo(curriculum.getNombre());
		assertThat(demoCurriculum.getCiudad()).isEqualTo(curriculum.getCiudad());
	}
	
	@Test
	public void shouldReturnCurriculumByUserId() throws CurriculumNotFoundException {
		logger.info("shouldReturnCurriculumByUserId");
		// arrange
		

		// act
		Curriculum curriculum = curriculumService.findByUserEmail("miguel.exposito@gmail.com");

		// assert
		logger.info("email del usuario asociado al currículo: " + curriculum.getUser().getEmail());
		assertThat("Miguel").isEqualTo(curriculum.getNombre());
		assertThat("Santander").isEqualTo(curriculum.getCiudad());
	}
	
	@Test
	public void shouldReturnAllCurriculos() throws CurriculumNotFoundException {
		logger.info("shouldReturnAllCurriculos");
		// arrange

		// act
		List<Curriculum> curriculos = curriculumService.findAll();

		// assert
		logger.info("Curriculos size: " + curriculos.size());
		assertThat(curriculos.size()).isEqualTo(5);
		
	}
	
	@Test
	public void shouldReturnCurriculumByOptionalParameters() throws CurriculumNotFoundException, ParseException {
		// arrange
		
		// act
		List<Curriculum> curriculos = curriculumService.findByOptionalParameters("España", "Santander", 5, "Telecomunicaciones", "unix");
		logger.info("número de resultados: " + curriculos.size());
		// assert
		logger.info(curriculos.get(0).getNombre() + " : " + curriculos.get(0).getExperiencia()/12 + " años" +  curriculos.get(0).getTitulaciones().toString());
		logger.info(curriculos.get(1).getNombre() + " : " + curriculos.get(1).getExperiencia()/12 + " años"+  curriculos.get(1).getTitulaciones().toString());
		
		assertThat("Ana Patricia").isEqualTo(curriculos.get(0).getNombre());
		assertThat("Miguel").isEqualTo(curriculos.get(1).getNombre());
		assertThat(curriculos.size()).isEqualTo(2);
	}
	
	@Test
	public void shouldSaveCurriculumAndItsRelatedParameters() throws CurriculumEmptyException, ParseException, CurriculumNotFoundException {
		// arrange
		Curriculum curriculum = new Curriculum("Pepito", "Pérez", "Francia", "París", "htp://localhost/imagen.png", "http://localhost/archivo.pdf");
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		ExperienciaProfesional experiencia = new ExperienciaProfesional("Project Manager", "EuroDisney", "Gestión de proyectos", formatoFecha.parse("14/10/2000"), formatoFecha.parse("31/12/2014"));
		Titulacion titulacion = new Titulacion("Licenciado en Administración y Dirección de Empresas");
		Conocimiento conocimiento = new Conocimiento("gestión de proyectos", NivelConocimiento.ALTO);
		Account usuario = accountRepository.save(new Account("nuevo.usuario@gmail.com", "usuario", "ROLE_USER"));
		// act
		curriculumService.save(curriculum, usuario, experiencia, titulacion, conocimiento);
		
		// assert
		
		assertThat("Pepito").isEqualTo(curriculumService.findByUserEmail("nuevo.usuario@gmail.com").getNombre());
		logger.info("titulación del usuario cuyo currículo está asociado al email de test: " + curriculumService.findByUserEmail("nuevo.usuario@gmail.com").getTitulaciones().iterator().next().getDescripcion());
		//assertThat("Miguel").isEqualTo(curriculos.get(1).getNombre());
		//assertThat(curriculos.size()).isEqualTo(2);
	}
	

	
}
