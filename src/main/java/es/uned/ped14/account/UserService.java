package es.uned.ped14.account;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;

import es.uned.ped14.admin.AdminController;
import es.uned.ped14.curriculum.Curriculum;

public class UserService implements UserDetailsService {
	Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountRepositoryInterface accountRepositoryInt;
	
	@Autowired
	private RoleRepositoryInterface roleRepository;
	
	@Autowired
	private AccountRepositoryInterface accountRepositoryInterface;
	
	@PostConstruct	
	protected void initialize() {
		Role role1 = new Role("ROLE_ADMIN");
		Role role2 = new Role("ROLE_USER");
		Role role3 = new Role("ROLE_CREATE");
		Role role4 = new Role("ROLE_EDIT");
		Role role5 = new Role("ROLE_DELETE");
		Account user1 = new Account("user", "demo");
		Account user2 = new Account("admin@admin.com", "admin");
		user1.addRole(role1);
		user2.addRole(role2);
		user2.addRole(role3);
		user2.addRole(role4);
		user2.addRole(role5);
		accountRepository.save(user1);
		accountRepository.save(user2);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByEmail(username);
		if(account == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return createUser(account);
	}
	
	
	public Account findByEmail(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByEmail(username);
		if(account == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return account;
	}
	
	public void signin(Account account) {
		SecurityContextHolder.getContext().setAuthentication(authenticate(account));
	}
	
	private Authentication authenticate(Account account) {
		return new UsernamePasswordAuthenticationToken(createUser(account), null, createAuthority(account));		
	}
	
	private User createUser(Account account) {
		return new User(account.getEmail(), account.getPassword(), createAuthority(account));
	}

	private List<GrantedAuthority> createAuthority(Account account) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Role role : account.getRoles()){
			authorities.add(new SimpleGrantedAuthority(role.toString()));
		}
		return authorities;
	}
	
	public void  save(Account user) {
		accountRepository.save(user);
		
	}
	
	public void  merge(Account user) {
		accountRepository.merge(user);
		
	}
	
	public void  flush() {
		accountRepositoryInterface.flush();
		
	}
	
	public List<Account> findAll(){
		return accountRepositoryInt.findAll();
	}
	
	public void addRole(String role){
		List<Account> users = findAll();
		for (Account user : users){
			Role r = new Role(role);
			r.setUser(user);
			user.addRole(r);
			merge(user);
		}
	}
	
	public void removeRole(String role) {
		List<Role> roles = roleRepository.findByDescripcion(role);
		for (Role r : roles){
			roleRepository.delete(r);
		}
	}
	
	public void changeState(String role, Boolean state) {
		
		if (Boolean.valueOf(state)){
			addRole(role);
		}
		else {
			removeRole(role);
		}
	}

}
