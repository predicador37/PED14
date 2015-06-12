package es.uned.ped14.conocimiento;

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
public class ConocimientoService {
	Logger logger = LoggerFactory.getLogger(ConocimientoService.class);
	 
	@Autowired
	private ConocimientoRepositoryInterface conocimientoRepository;
	
	@PostConstruct	
	protected void initialize() throws ParseException {
		logger.info("Inicializar data para servicio deconocimientos");
		
	}
	 
	
	public List<Conocimiento> findByCurriculum(Curriculum curriculum) throws ConocimientoNotFoundException {
		List<Conocimiento> conocimientos = conocimientoRepository.findByCurriculum(curriculum);
		if(conocimientos.isEmpty()) {
			throw new ConocimientoNotFoundException("Conocimiento no encontrado.");
		}
		return conocimientos;
	}
	
	public List<Conocimiento> findByDescripcion(String descripcion) throws ConocimientoNotFoundException {
		List<Conocimiento> conocimientos = conocimientoRepository.findByDescripcion(descripcion);
		if(conocimientos.isEmpty()) {
			throw new ConocimientoNotFoundException("Conocimiento no encontrado.");
		}
		return conocimientos;
	}
	
	public List<Conocimiento> findAll() throws ConocimientoNotFoundException {
		List<Conocimiento> conocimientos = conocimientoRepository.findAll();
		if(conocimientos.isEmpty()) {
			throw new ConocimientoNotFoundException("No se encontraron conocimientos.");
		}
		return conocimientos;
	}
	
	public void  save(Conocimiento conocimiento) {
		conocimientoRepository.save(conocimiento);
		
	}
	
	public void  delete(Conocimiento conocimiento) {
		
		conocimientoRepository.delete(conocimiento);
		
	}
	
public void  flush() {
		
		conocimientoRepository.flush();
		
	}
	
	public Conocimiento  findOne(Long id) throws ConocimientoNotFoundException {
		Conocimiento conocimiento = conocimientoRepository.findOne(id);
		if (conocimiento == null) {
			throw new ConocimientoNotFoundException("Conocimiento no encontrado");
		}
		return conocimiento;
	}
	
	}
