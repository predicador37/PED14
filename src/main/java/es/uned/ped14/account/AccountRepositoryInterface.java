package es.uned.ped14.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interfaz AccountRepositoryInterface, proporciona automáticamente los métodos
 * proporcionados por spring-data.
 */
@Transactional
public interface AccountRepositoryInterface extends
		JpaRepository<Account, Long> {

}
