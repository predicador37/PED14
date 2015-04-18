package es.uned.ped14.curriculum;

import java.util.Collections;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;

public class CurriculumService {
	
	@Autowired
	private CurriculumRepository curriculumRepository;
	
	@PostConstruct	
	protected void initialize() {
		curriculumRepository.save(new Curriculum("Miguel", "Expósito Martín", "España", "Santander", "htp://localhost/imagen.png", "http://localhost/archivo.pdf"));
		
	}
	 
	
	public Curriculum find(Long id) throws CurriculumNotFoundException {
		Curriculum curriculum = curriculumRepository.findOne(id);
		if(curriculum == null) {
			throw new CurriculumNotFoundException("curriculum not found");
		}
		return curriculum;
	}
	


}
