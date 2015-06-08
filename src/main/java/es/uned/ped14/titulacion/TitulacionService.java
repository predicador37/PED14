package es.uned.ped14.titulacion;

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
public class TitulacionService {
	Logger logger = LoggerFactory.getLogger(TitulacionService.class);
	 
	@Autowired
	private TitulacionRepositoryInterface cursoRepository;
	
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
		
		Account user1 = new Account("jose@gmail.com", "jose", "jose", "Cuenca", "ROLE_USER");
		Curriculum curriculum = new Curriculum("Don José", "Pérez", "España", "Oviedo", "htp://localhost/imagen.png", "http://localhost/archivo.pdf");
		curriculum.setUser(user1);
		Titulacion titulacion = new Titulacion("Ingeniero de obras públicas");
		titulacion.setCurriculum(curriculum);
		accountRepository.save(user1);
		curriculumRepository.save(curriculum);
		cursoRepository.save(titulacion);
		
		
		
	}
	 
	
	public Titulacion findByCurriculum(Curriculum curriculum) throws TitulacionNotFoundException {
		Titulacion titulacion = cursoRepository.findByCurriculum(curriculum);
		if(titulacion == null) {
			throw new TitulacionNotFoundException("titulacion not found");
		}
		return titulacion;
	}
	
	public Titulacion findByDescripcion(String descripcion) throws TitulacionNotFoundException {
		Titulacion titulacion = cursoRepository.findByDescripcion(descripcion);
		if(titulacion == null) {
			throw new TitulacionNotFoundException("titulacion not found");
		}
		return titulacion;
	}
	
	}
