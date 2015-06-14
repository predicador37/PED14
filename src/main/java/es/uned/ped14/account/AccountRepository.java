package es.uned.ped14.account;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase AccountRepository: proporciona métodos específicos para la gestión de
 * las capas inferiores (conexión con la base de datos), usando el patrón
 * repository.
 */
@Repository
@Transactional(readOnly = true)
public class AccountRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	private PasswordEncoder passwordEncoder;

	/**
	 * Save. Método que persiste (pero no actualiza) un usuario.
	 * 
	 * @param account
	 *            , objeto Account con el usuario.
	 * @return el usuario.
	 */
	@Transactional
	public Account save(Account account) {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		entityManager.persist(account);
		return account;
	}

	/**
	 * Merge. Método que actualiza (pero no persiste) un usuario.
	 * 
	 * @param account
	 *            , objeto Account con el usuario.
	 * @return el usuario.
	 */
	@Transactional
	public Account merge(Account account) {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		entityManager.merge(account);
		return account;
	}

	/**
	 * Find by email. Método que encuentra un usuario dado su email (o login).
	 * 
	 * @param email
	 *            , cadena de texto con el email del usuario.
	 * @return account, el usuario.
	 */
	public Account findByEmail(String email) {
		try {
			return entityManager
					.createNamedQuery(Account.FIND_BY_EMAIL, Account.class)
					.setParameter("email", email).getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}

}
