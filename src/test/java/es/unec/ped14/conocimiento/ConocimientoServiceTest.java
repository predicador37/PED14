package es.unec.ped14.conocimiento;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.uned.ped14.config.ApplicationConfig;
import es.uned.ped14.config.JpaConfig;
import es.uned.ped14.config.SecurityConfig;
import es.uned.ped14.conocimiento.Conocimiento;
import es.uned.ped14.conocimiento.ConocimientoNotFoundException;
import es.uned.ped14.conocimiento.ConocimientoRepositoryInterface;
import es.uned.ped14.conocimiento.ConocimientoService;
import es.uned.ped14.curriculum.Curriculum;
import es.uned.ped14.curriculum.CurriculumNotFoundException;
import es.uned.ped14.curriculum.CurriculumService;
import es.uned.ped14.curriculum.CurriculumServiceTest;
import es.uned.ped14.experiencia.ExperienciaProfesional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, JpaConfig.class, SecurityConfig.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ConocimientoServiceTest {

	@Autowired
	private ConocimientoService conocimientoService;
	
	@Autowired
	private ConocimientoRepositoryInterface conocimientoRepository;
	
	@Autowired
	private CurriculumService curriculumService;
	
		
	@Test
	public void shouldReturnConocimientoByCurriculum() throws CurriculumNotFoundException, ConocimientoNotFoundException {
		 Logger logger = LoggerFactory.getLogger(ConocimientoServiceTest.class);
		logger.info("shouldReturnConocimientoByCurriculum");
		// arrange
		
		Curriculum curriculum = curriculumService.findByUserEmail("jose@gmail.com");
		// act
		List<Conocimiento> conocimientos = conocimientoRepository.findByCurriculum(curriculum);
		

		// assert
		assertThat(conocimientos.iterator().next().getDescripcion()).isEqualTo("unix");
		
	}
	
	

}
