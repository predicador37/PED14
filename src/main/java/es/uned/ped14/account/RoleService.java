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
import org.springframework.stereotype.Service;

import es.uned.ped14.curriculum.Curriculum;
@Service
public class RoleService {

	
	@Autowired
	private RoleRepositoryInterface roleRepository;
	
	@PostConstruct	
	protected void initialize() {
		
	}
	
	
	public void  save(Role role) {
		roleRepository.save(role);
		
	}
	
	public Role  findOne(Long id) {
		return roleRepository.findOne(id);
		
	}
	
	public void  flush() {
		roleRepository.flush();
		
	}
	
	public void  delete(Role role) {
		roleRepository.delete(role);
		
	}

	public Role findByDescripcionAndUser(String descripcion, Account user) {
		return roleRepository.findByDescripcionAndUser(descripcion, user);
	}
	
	public List<Role> findByDescripcion(String descripcion) {
		return roleRepository.findByDescripcion(descripcion);
	}
}
