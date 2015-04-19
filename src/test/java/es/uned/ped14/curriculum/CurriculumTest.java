package es.uned.ped14.curriculum;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import es.uned.ped14.experiencia.ExperienciaProfesional;

public class CurriculumTest {

	@Test
	public void shouldReturnExperiencia() {
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			ExperienciaProfesional experiencia1 = new ExperienciaProfesional("Becario", "ACME", "Gestión de cadena de producción", formatoFecha.parse("14/10/2008"), formatoFecha.parse("31/12/2012"));
			ExperienciaProfesional experiencia2 = new ExperienciaProfesional("Director General", "ACME", "Jefe supremo", formatoFecha.parse("01/01/2013"), formatoFecha.parse("14/10/2015"));
			Curriculum demoCurriculum1 = new Curriculum("Miguel", "Expósito", "España", "Santander", "htp://localhost/imagen.png", "http://localhost/archivo.pdf");
			experiencia1.setCurriculum(demoCurriculum1);
			List<ExperienciaProfesional> listaExperiencias = new ArrayList<ExperienciaProfesional>();
			listaExperiencias.add(experiencia1);
			listaExperiencias.add(experiencia2);
			demoCurriculum1.setExperiencias(listaExperiencias);
			assertThat(demoCurriculum1.getExperiencia()).isEqualTo(85);
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
