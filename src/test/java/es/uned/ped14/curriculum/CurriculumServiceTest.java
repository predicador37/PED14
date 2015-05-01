package es.uned.ped14.curriculum;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
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

import es.uned.ped14.config.ApplicationConfig;
import es.uned.ped14.config.JpaConfig;
import es.uned.ped14.config.SecurityConfig;
import es.uned.ped14.experiencia.ExperienciaProfesionalRepository;

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
	public void shouldReturnCurriculumByPaisAndCiudadAndGreaterThanExperiencia() throws CurriculumNotFoundException, ParseException {
		// arrange
		
		// act
		List<Curriculum> curriculos = curriculumService.findByPaisAndCiudadAndGreaterThanExperiencia("España", "Santander", 5);
     
		// assert
		logger.info(curriculos.get(0).getNombre() + " : " + curriculos.get(0).getExperiencia()/12 + " años");
		logger.info(curriculos.get(1).getNombre() + " : " + curriculos.get(1).getExperiencia()/12 + " años");
		
		assertThat("Ana Patricia").isEqualTo(curriculos.get(0).getNombre());
		assertThat("Miguel").isEqualTo(curriculos.get(1).getNombre());
		assertThat(curriculos.size()).isEqualTo(2);
	}

	
}
