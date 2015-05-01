package es.uned.ped14.curriculum;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.uned.ped14.config.ApplicationConfig;
import es.uned.ped14.config.JpaConfig;
import es.uned.ped14.config.SecurityConfig;
import es.uned.ped14.experiencia.ExperienciaProfesional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, JpaConfig.class, SecurityConfig.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class CurriculumTest {

	@Autowired
	private CurriculumRepository curriculumRepository;
	
	@Test
	public void shouldReturnExperiencia() {
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			ExperienciaProfesional experiencia1 = new ExperienciaProfesional("Becario", "ACME", "Gestión de cadena de producción", formatoFecha.parse("14/10/2008"), formatoFecha.parse("31/12/2012"));
			ExperienciaProfesional experiencia2 = new ExperienciaProfesional("Director General", "ACME", "Jefe supremo", formatoFecha.parse("01/01/2013"), formatoFecha.parse("14/10/2015"));
			Curriculum demoCurriculum1 = new Curriculum("Miguel", "Expósito", "España", "Santander", "htp://localhost/imagen.png", "http://localhost/archivo.pdf");
			
			demoCurriculum1.addExperiencia(experiencia1);
			demoCurriculum1.addExperiencia(experiencia2);
			
			curriculumRepository.save(demoCurriculum1);
			
			assertThat(demoCurriculum1.getExperiencia()).isEqualTo(85);
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
