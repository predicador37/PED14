package es.uned.ped14.experiencia;

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
public class ExperienciaProfesionalService {
	Logger logger = LoggerFactory.getLogger(ExperienciaProfesionalService.class);
	 
	@Autowired
	private ExperienciaProfesionalRepositoryInterface experienciaProfesionalRepository;
	
	@PostConstruct	
	protected void initialize() throws ParseException {
		logger.info("Inicializar data para servicio de experiencia profesional");
	
		
	}
	 
	
	public ExperienciaProfesional findByCurriculum(Curriculum curriculum) throws ExperienciaProfesionalNotFoundException {
		ExperienciaProfesional experiencia = experienciaProfesionalRepository.findByCurriculum(curriculum);
		if(experiencia == null) {
			throw new ExperienciaProfesionalNotFoundException("Experiencia not found");
		}
		return experiencia;
	}
	
	public ExperienciaProfesional findByEmpresa(String empresa) throws ExperienciaProfesionalNotFoundException {
		ExperienciaProfesional experiencia = experienciaProfesionalRepository.findByEmpresa(empresa);
		if(experiencia == null) {
			throw new ExperienciaProfesionalNotFoundException("Experiencia not found");
		}
		return experiencia;
	}
	
	public ExperienciaProfesional findByCargo(String cargo) throws ExperienciaProfesionalNotFoundException {
		ExperienciaProfesional experiencia = experienciaProfesionalRepository.findByCargo(cargo);
		if(experiencia == null) {
			throw new ExperienciaProfesionalNotFoundException("Experiencia not found");
		}
		return experiencia;
	}
	
	public List<ExperienciaProfesional> findAll() throws ExperienciaProfesionalNotFoundException {
		List<ExperienciaProfesional> experiencias = experienciaProfesionalRepository.findAll();
		if(experiencias.isEmpty()) {
			throw new ExperienciaProfesionalNotFoundException("No se encontraron titulaciones");
		}
		return experiencias;
	}
	
	public void  save(ExperienciaProfesional experiencia) {
		experienciaProfesionalRepository.save(experiencia);
		
	}
	
	}
