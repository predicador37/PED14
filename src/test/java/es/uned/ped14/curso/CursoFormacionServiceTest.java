package es.uned.ped14.curso;

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
import es.uned.ped14.curriculum.Curriculum;
import es.uned.ped14.curriculum.CurriculumNotFoundException;
import es.uned.ped14.curriculum.CurriculumService;
import es.uned.ped14.curriculum.CurriculumServiceTest;
import es.uned.ped14.experiencia.ExperienciaProfesional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, JpaConfig.class, SecurityConfig.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class CursoFormacionServiceTest {

	@Autowired
	private CursoFormacionService cursoService;
	
	@Autowired
	private CurriculumService curriculumService;
	
		
	@Test
	public void shouldReturnCursoByCurriculum() throws CurriculumNotFoundException, CursoFormacionNotFoundException {
		 Logger logger = LoggerFactory.getLogger(CursoFormacionServiceTest.class);
		logger.info("shouldReturnCursoByCurriculum");
		// arrange
		
		Curriculum curriculum = curriculumService.findByUserEmail("miguel@gmail.com");
		// act
		
		List<CursoFormacion> cursos = cursoService.findByCurriculum(curriculum);
		// assert
		assertThat(cursos.iterator().next().getDescripcion()).isEqualTo("Curso de desarrollo ágil de software");
		
	}

}
