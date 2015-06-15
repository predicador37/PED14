package es.uned.ped14.account;

import java.security.Principal;
import java.util.ConcurrentModificationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import es.uned.ped14.curriculum.Curriculum;
import es.uned.ped14.curriculum.CurriculumController;
import es.uned.ped14.curriculum.CurriculumService;

/**
 * Clase AccountController, controlador de Spring MVC para la clase Account o
 * cuenta de usuario. Gestiona todas las peticiones a URLs.
 */
@Controller
@Secured({ "ROLE_USER", "ROLE_RESTRICTED" })
class AccountController {
	
	/** Logger para la depuración de errores. */
	Logger logger = LoggerFactory.getLogger(CurriculumController.class);
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private RoleService roleService;

	@Autowired
	public AccountController(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	@Autowired
	private UserService userService;
	
	@Autowired
	private CurriculumService curriculumService;
	
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
	
	@RequestMapping(value = "account/userOut", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public String userOut(Principal principal) {
		logger.info("Remove user controller");
		Assert.notNull(principal);
		return "account/delete";
	}
	
	@RequestMapping(value = "account/delete", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public String delete(Principal principal, HttpServletRequest request, HttpServletResponse response, Authentication auth) {
		logger.info("Delete user controller"); 
		if (auth != null){
			 UserDetails userDetails = (UserDetails) auth.getPrincipal();
			 Account user = userService.findByEmail(userDetails.getUsername());
			 Curriculum curriculum = user.getCurriculum();
		     SecurityContextHolder.getContext().setAuthentication(null);
             new SecurityContextLogoutHandler().logout(request, response, auth);
             logger.info("User logged out");
             curriculum.setUser(null);
             curriculumService.save(curriculum);
             curriculumService.delete(curriculum.getId());
             curriculumService.flush();
             logger.info("Curriculum deleted");
             user.setCurriculum(null);
             user.setCurriculum(null);
             for (Role r : user.getRoles()){
            	 user.removeRole(r);
            	 roleService.delete(r);
            	 roleService.flush();
             }
             userService.merge(user);
             userService.flush();
             try {
             userService.delete(user.getId());
             
             }
             catch (ConcurrentModificationException e) {
            	 logger.error("Error de concurrencia... pasable");
             }
             userService.flush();
          }
		 
		
		
		return "account/confirmed";
	}
}
