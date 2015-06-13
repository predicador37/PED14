package es.uned.ped14.account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;

import es.uned.ped14.curriculum.Curriculum;

public class RoleService {

	
	@Autowired
	private RoleRepositoryInterface roleRepository;
	
	@PostConstruct	
	protected void initialize() {
		
	}
	
	
	public void  save(Role role) {
		roleRepository.save(role);
		
	}
	
	public void  flush() {
		roleRepository.flush();
		
	}

}
