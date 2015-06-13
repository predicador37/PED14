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

public class UserService implements UserDetailsService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private RoleRepositoryInterface roleRepository;
	
	@Autowired
	private AccountRepositoryInterface accountRepositoryInterface;
	
	@PostConstruct	
	protected void initialize() {
		Role role1 = new Role("ROLE_ADMIN");
		Role role2 = new Role("ROLE_USER");
		Account user1 = new Account("user", "demo");
		Account user2 = new Account("admin@admin.com", "admin");
		user1.addRole(role1);
		user2.addRole(role2);
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
	
	public void  flush() {
		accountRepositoryInterface.flush();
		
	}

}
