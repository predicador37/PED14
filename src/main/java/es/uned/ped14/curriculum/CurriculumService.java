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
@Service
public class CurriculumService {
	Logger logger = LoggerFactory.getLogger(CurriculumServiceTest.class);
	 
	@Autowired
	private CurriculumRepository curriculumRepository;
	
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
		
		Curriculum demoCurriculum1 = new Curriculum("Miguel", "Expósito", "España", "Santander", "htp://localhost/imagen.png", "http://localhost/archivo.pdf");
		Curriculum demoCurriculum2 = new Curriculum("Héctor", "Garnacho", "España", "Valladolid", "htp://localhost/imagen.png", "http://localhost/archivo.pdf");
		Curriculum demoCurriculum3 = new Curriculum("Marcos", "Azorí", "España", "Madrid", "htp://localhost/imagen.png", "http://localhost/archivo.pdf");
		Curriculum demoCurriculum4 = new Curriculum("Ana Patricia", "Botín", "España", "Santander", "htp://localhost/imagen.png", "http://localhost/archivo.pdf");
		Curriculum demoCurriculum5 = new Curriculum("Lucía", "Expósito", "España", "Santander", "htp://localhost/imagen.png", "http://localhost/archivo.pdf");
		
		demoCurriculum1.addExperiencia(experiencia1);
		demoCurriculum2.addExperiencia(experiencia2);
		demoCurriculum1.addExperiencia(experiencia3);
		demoCurriculum4.addExperiencia(experiencia4);
		demoCurriculum4.addExperiencia(experiencia5);
		demoCurriculum5.addExperiencia(experiencia6);		
		
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
	 * Servicio que devuelve una lista de currículos filtrados por país y ciudad de origen así como experiencia
	 * total en años. Los parámetros pueden ser nulos, en cuyo caso no se filtrará por ellos.
	 * @param pais: String con el país de origen
	 * @param ciudad: String con la ciudad de origen
	 * @param experiencia: Integer con la experiencia en años
	 * @return List<Curriculum>
	 * @throws CurriculumNotFoundException
	 */
	public List<Curriculum> findByPaisAndCiudadAndGreaterThanExperiencia(String pais, String ciudad, Integer experiencia) throws CurriculumNotFoundException {
		List<Curriculum> curriculos = curriculumRepository.findByPaisAndCiudadAndGreaterThanExperiencia(pais, ciudad, experiencia);
		if(curriculos.isEmpty()) {
			throw new CurriculumNotFoundException("curriculum not found");
		}
		return curriculos;
	}
	


}
