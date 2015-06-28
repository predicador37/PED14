package es.uned.ped14.signup;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.uned.ped14.account.Account;
import es.uned.ped14.account.AccountRepository;
import es.uned.ped14.account.Role;
import es.uned.ped14.account.UserService;
import es.uned.ped14.support.web.MessageHelper;

/**
 * Clase SignupController, controlador de Spring MVC que gestiona las altas de
 * usuarios en el sistema.
 */
@Controller
public class SignupController {

	private static final String SIGNUP_VIEW_NAME = "signup/signup";

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "signup")
	public String signup(Model model) {
		model.addAttribute(new SignupForm());
		return SIGNUP_VIEW_NAME;
	}

	/**
	 * Acción que muestra una página de alta para los usuarios, permite darlos
	 * de alta y redirige a la creación de currículum.
	 *
	 * @param signupForm
	 *            , formulario DTO de captura de datos para un usuario.
	 * @param ra
	 *            , atributos a reenviar entre peticiones.
	 * 
	 * @return cadena de texto con el nombre de la vista de la creación de
	 *         currículum.
	 */
	@RequestMapping(value = "signup", method = RequestMethod.POST)
	public String signup(@Valid @ModelAttribute SignupForm signupForm,
			Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return SIGNUP_VIEW_NAME;
		}
		Account account = accountRepository.save(signupForm.createAccount());
		userService.flush();
		userService.signin(account);
		// see /WEB-INF/i18n/messages.properties and
		// /WEB-INF/views/homeSignedIn.html
		MessageHelper.addSuccessAttribute(ra, "signup.success");
		return "redirect:/curriculum/create";
	}
}
