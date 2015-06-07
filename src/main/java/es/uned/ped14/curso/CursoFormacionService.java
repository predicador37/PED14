package es.uned.ped14.curso;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uned.ped14.experiencia.ExperienciaProfesional;
import es.uned.ped14.titulacion.Titulacion;
import es.uned.ped14.account.Account;
import es.uned.ped14.account.AccountRepository;
import es.uned.ped14.conocimiento.Conocimiento;
import es.uned.ped14.conocimiento.NivelConocimiento;
import es.uned.ped14.curriculum.Curriculum;
import es.uned.ped14.curriculum.CurriculumNotFoundException;
import es.uned.ped14.curriculum.CurriculumRepository;
import es.uned.ped14.curriculum.CurriculumService;

@Service
public class CursoFormacionService {
	Logger logger = LoggerFactory.getLogger(CursoFormacionService.class);
	 
	@Autowired
	private CursoFormacionRepositoryInterface cursoRepository;
	
	@Autowired
	private CurriculumRepository curriculumRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private CurriculumService curriculumService;
	
	@PostConstruct	
	protected void initialize() throws ParseException {
		logger.info("Inicializar data para servicio de currículos");
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		
		Account user1 = new Account("miguel@gmail.com", "miguel", "ROLE_USER");
		Curriculum curriculum = new Curriculum("Manuel", "Gutiérrez", "España", "Valladolid", "htp://localhost/imagen.png", "http://localhost/archivo.pdf");
		curriculum.setUser(user1);
		CursoFormacion curso = new CursoFormacion("Curso de desarrollo ágil de software",(Integer) 20 , formatoFecha.parse("05/06/2015"));
		curso.setCurriculum(curriculum);
		accountRepository.save(user1);
		curriculumRepository.save(curriculum);
		cursoRepository.save(curso);
		
		
		
	}
	 
	
	public CursoFormacion findByCurriculum(Curriculum curriculum) throws CursoFormacionNotFoundException {
		CursoFormacion curso = cursoRepository.findByCurriculum(curriculum);
		if(curso == null) {
			throw new CursoFormacionNotFoundException("curso not found");
		}
		return curso;
	}
	
	public CursoFormacion findByDescripcion(String descripcion) throws CursoFormacionNotFoundException {
		CursoFormacion curso = cursoRepository.findByDescripcion(descripcion);
		if(curso == null) {
			throw new CursoFormacionNotFoundException("curso not found");
		}
		return curso;
	}
	
	}