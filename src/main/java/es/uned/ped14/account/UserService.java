package es.uned.ped14.account;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import es.uned.ped14.curriculum.Curriculum;
import es.uned.ped14.curriculum.CurriculumNotFoundException;

/**
 * Clase UserService, servicio que encapsula la lógica de negocio de cuenta de
 * usuario e interactúa con las capas inferiores (repositorios).
 */
public class UserService implements UserDetailsService {
	
	/** Logger para la depuración de errores */
	Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private RoleRepositoryInterface roleRepository;

	@Autowired
	private AccountRepositoryInterface accountRepositoryInterface;

	/**
	 * Initialize. Carga los datos iniciales.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 */
	@PostConstruct
	protected void initialize() {
		Role role1 = new Role("ROLE_ADMIN");
		Role role2 = new Role("ROLE_USER");
		Role role3 = new Role("ROLE_CREATE");
		Role role4 = new Role("ROLE_EDIT");
		Role role5 = new Role("ROLE_DELETE");
		Account user1 = new Account("user", "demo");
		Account user2 = new Account("admin@admin.com", "admin");
		user1.addRole(role2);
		user2.addRole(role1);
		user2.addRole(role3);
		user2.addRole(role4);
		user2.addRole(role5);
		accountRepository.save(user1);
		accountRepository.save(user2);
	}

	/**
	 * Load user by username. Carga un usuario dado su nombre de usuario
	 * (email).
	 * 
	 * @param username
	 *            , cadena de texto con el email o nombre de usuario.
	 * @return un usuario de spring security creado.
	 * @throws UsernameNotFoundException
	 *             , excepción lanzada en caso de no encontrar ningún usuario
	 *             con ese email.
	 */
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Account account = accountRepository.findByEmail(username);
		if (account == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return createUser(account);
	}

	/**
	 * Find by email. Busca un usuario dado su email.
	 * 
	 * @param username
	 *            , cadena de texto con el email o nombre de usuario.
	 * @return una cuenta de usuario de tipo Account.
	 * @throws UserNameNotFoundException
	 *             , excepción lanzada en caso de no encontrar ningún usuario
	 *             para ese email.
	 */
	public Account findByEmail(String username)
			throws UsernameNotFoundException {
		Account account = accountRepository.findByEmail(username);
		if (account == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return account;
	}

	/**
	 * Sign in. Permite hacer log in en la aplicación al usuario a través de
	 * spring security.
	 * 
	 * @param account
	 *            , objeto Account con el usuario a hacer log in.
	 */
	public void signin(Account account) {
		SecurityContextHolder.getContext().setAuthentication(
				authenticate(account));
	}

	/**
	 * Authenticate. Permite autenticarse en la aplicación al usuario a través
	 * de spring security.
	 * 
	 * @param account
	 *            , objeto Account con el usuario a autenticar.
	 */
	private Authentication authenticate(Account account) {
		return new UsernamePasswordAuthenticationToken(createUser(account),
				null, createAuthority(account));
	}

	/**
	 * Create user. Permite crear un usuario de spring security.
	 * 
	 * @param account
	 *            , objeto Account con el usuario a crear.
	 */
	private User createUser(Account account) {
		return new User(account.getEmail(), account.getPassword(),
				createAuthority(account));
	}

	/**
	 * Create authority. Permite crear un conjunto de roles asociados a un
	 * usuario de spring security.
	 * 
	 * @param account
	 *            , objeto Account con el usuario cuyos roles provenientes de un
	 *            objeto cuenta de usuario se quieren añadir.
	 */
	private List<GrantedAuthority> createAuthority(Account account) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Role role : account.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.toString()));
		}
		return authorities;
	}

	/**
	 * Save. Persiste (pero no actualiza) una cuenta de usuario.
	 * 
	 * @param user
	 *            , objeto de clase Account.
	 */
	public void save(Account user) {
		accountRepository.save(user);

	}

	/**
	 * Merge. Actualiza (pero no persiste) una cuenta de usuario.
	 * 
	 * @param user
	 *            , objeto de clase Account.
	 */
	public void merge(Account user) {
		accountRepository.merge(user);

	}

	/**
	 * Flush. Obliga a la capa de persistencia a escribir cualquier cambio en la
	 * base de datos.
	 */
	public void flush() {
		accountRepositoryInterface.flush();

	}
	
	/**
	 * Flush. Obliga a la capa de persistencia a escribir cualquier cambio en la
	 * base de datos.
	 */
	public void delete(Long id) {
		accountRepositoryInterface.delete(id);

	}

	/**
	 * Find all. Devuelve un listado con todos los usuarios existentes.
	 * 
	 * @return lista de cuentas de usuario.
	 */
	public List<Account> findAll() {
		return accountRepositoryInterface.findAll();
	}

	/**
	 * Add role. Añade un rol a todos los usuarios del sistema.
	 * 
	 * @param role
	 *            , el rol a añadir.
	 */
	public void addRole(String role) {
		List<Account> users = findAll();
		for (Account user : users) {
			Role r = new Role(role);
			r.setUser(user);
			user.addRole(r);
			merge(user);
		}
	}

	/**
	 * Remove role. Quita un rol a todos los usuarios del sistema.
	 * 
	 * @param role
	 *            , el rol a quitar.
	 */
	public void removeRole(String role) {
		List<Role> roles = roleRepository.findByDescripcion(role);
		for (Role r : roles) {
			roleRepository.delete(r);
		}
	}

	/**
	 * Change state. Activa o desactiva funcionalidades de usuario según su
	 * estado actual.
	 * 
	 * @param role
	 *            , el rol o funcionalidad a desactivar.
	 * @param state
	 *            , booleano que indica si debe activarse o desactivarse dicha
	 *            funcionalidad.
	 */
	public void changeState(String role, Boolean state) {

		if (Boolean.valueOf(state)) {
			addRole(role);
		} else {
			removeRole(role);
		}
	}
	
	/**
	 * Find one. Busca un único usuario dado su identificador.
	 * 
	 * @param id
	 *            , el identificador.
	 * @return account, el objeto de clase Account encontrado.
	 * @throws AccountNotFoundException
	 *             , excepción a lanzar en caso de no encontrar el usuario.
	 */
	public Account findOne(Long id) throws AccountNotFoundException {

		Account usuario = accountRepositoryInterface.findOne(id);
		if (usuario == null) {
			throw new AccountNotFoundException(
					"No se encuentra el usuario especificado");
		}
		return usuario;
	}

}
