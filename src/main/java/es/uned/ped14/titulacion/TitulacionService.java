package es.uned.ped14.titulacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uned.ped14.experiencia.ExperienciaProfesional;
import es.uned.ped14.titulacion.Titulacion;
import es.uned.ped14.account.Account;
import es.uned.ped14.account.AccountRepository;
import es.uned.ped14.account.Role;
import es.uned.ped14.account.UserService;
import es.uned.ped14.conocimiento.Conocimiento;
import es.uned.ped14.conocimiento.ConocimientoRepositoryInterface;
import es.uned.ped14.conocimiento.NivelConocimiento;
import es.uned.ped14.curriculum.Curriculum;
import es.uned.ped14.curriculum.CurriculumNotFoundException;
import es.uned.ped14.curriculum.CurriculumRepository;
import es.uned.ped14.curriculum.CurriculumRepositoryInterface;
import es.uned.ped14.curriculum.CurriculumService;

@Service
public class TitulacionService {
	
	
	Logger logger = LoggerFactory.getLogger(TitulacionService.class);
	 
	@Autowired
	private TitulacionRepositoryInterface titulacionRepository;
	
	@Autowired
	private ConocimientoRepositoryInterface conocimientoRepository;

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private CurriculumRepositoryInterface curriculumRepository;
	
	@PostConstruct
	 @Transactional
	protected void initialize() throws ParseException {
		logger.info("Inicializar data para servicio de titulaciones");
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		Role role1 = new Role("ROLE_USER");
		Account user1 = new Account("jose@gmail.com", "jose");
		user1.addRole(role1);
		Curriculum curriculum = new Curriculum("Don José", "Pérez", "España", "Oviedo", "htp://localhost/imagen.png", "http://localhost/archivo.pdf");
		curriculum.setUser(user1);
		Titulacion titulacion = new Titulacion("Ingeniero de obras públicas", 2008);
		Conocimiento conocimiento = new Conocimiento("unix", NivelConocimiento.ALTO);
		accountRepository.save(user1);
		conocimientoRepository.save(conocimiento);
		titulacionRepository.save(titulacion);
		curriculumRepository.save(curriculum);
		curriculum.addTitulacion(titulacion);
		curriculum.addConocimiento(conocimiento);
		titulacionRepository.save(titulacion);
		curriculumRepository.save(curriculum);

	}

	public Titulacion findByDescripcion(String descripcion) throws TitulacionNotFoundException {
		Titulacion titulacion = titulacionRepository.findByDescripcion(descripcion);
		if(titulacion == null) {
			throw new TitulacionNotFoundException("titulacion not found");
		}
		return titulacion;
	}
	
	public List<Titulacion> findAll() throws TitulacionNotFoundException {
		List<Titulacion> titulaciones = titulacionRepository.findAll();
		if(titulaciones.isEmpty()) {
			throw new TitulacionNotFoundException("No se encontraron titulaciones");
		}
		return titulaciones;
	}
	
	public void  save(Titulacion titulacion) {
		titulacionRepository.save(titulacion);
		
	}
	
	public void  delete(Titulacion titulacion) {
		titulacionRepository.delete(titulacion);
		
	}
	
	
	public Titulacion  findOne(Long id) throws TitulacionNotFoundException {
		Titulacion titulacion = titulacionRepository.findOne(id);
		if(titulacion == null) {
			throw new TitulacionNotFoundException("titulacion not found");
		}
		return titulacion;
	}
	
	public List<Titulacion> findByCurriculum(Curriculum curriculum) throws TitulacionNotFoundException {
		List<Titulacion> titulaciones = titulacionRepository.findByCurriculum(curriculum);
		if(titulaciones.isEmpty()) {
			throw new TitulacionNotFoundException("No se encontraron titulaciones");
		}
		return titulaciones;
	}
	
	
	
	public Integer like(Titulacion titulacion) {
		
		titulacion.like();
		titulacionRepository.save(titulacion);
		return titulacion.getLikes();
	}
	
	}
