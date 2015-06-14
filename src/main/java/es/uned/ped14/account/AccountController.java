package es.uned.ped14.account;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Clase AccountController, controlador de Spring MVC para la clase Account o
 * cuenta de usuario. Gestiona todas las peticiones a URLs.
 */
@Controller
@Secured({ "ROLE_USER", "ROLE_RESTRICTED" })
class AccountController {

	private AccountRepository accountRepository;

	@Autowired
	public AccountController(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	/**
	 * Acción que devuelve el usuario actual loggeado en el sistema.
	 *
	 * @param principal
	 *            , datos de usuario de spring security.
	 * 
	 * @return el usuario en un objeto de tipo Account.
	 */
	@RequestMapping(value = "account/current", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Account accounts(Principal principal) {
		Assert.notNull(principal);
		return accountRepository.findByEmail(principal.getName());
	}
}
