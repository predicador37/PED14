package es.uned.ped14.home;

import java.security.Principal;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uned.ped14.account.Account;
import es.uned.ped14.account.UserService;
import es.uned.ped14.curriculum.Curriculum;
import es.uned.ped14.curriculum.CurriculumController;
import es.uned.ped14.curriculum.CurriculumNotFoundException;
import es.uned.ped14.curriculum.CurriculumSearchForm;
import es.uned.ped14.curriculum.CurriculumService;

/**
 * Clase HomeController, controlador de Spring MVC que gestiona la home (URL
 * principal de la aplicación).
 */
@Controller
public class HomeController {
	
	Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	CurriculumService curriculumService;

	@Autowired
	UserService userService;

	/**
	 * Acción que muestra la página general de entrada a la aplicación en
	 * función de si es usuario registrado o no y de si tiene dado de alta un
	 * currículum o no.
	 *
	 * @param curriculos
	 *            , una lista de todos los currículos para mostrar en la página
	 *            de entrada anónima.
	 * @param principal
	 *            , datos del usuario de spring security.
	 * @param model
	 *            , el modelo.
	 * 
	 * @return cadena de texto con el nombre de la vista en función de la
	 *         situación.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(
			@ModelAttribute("curriculos") ArrayList<Curriculum> curriculos,
			Principal principal, Model model) {

		if (curriculos.isEmpty()) {
			try {
				curriculos = (ArrayList<Curriculum>) curriculumService
						.findAll();
				model.addAttribute("curriculos", curriculos);
			} catch (CurriculumNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			model.addAttribute("curriculos", curriculos);
		}
		if (principal != null) {
			try {
		Account user = userService.findByEmail(principal.getName());
		if (user.getCurriculum() != null) {
			model.addAttribute("hasCurriculum", true);
			model.addAttribute("curriculumId", user.getCurriculum().getId());
			return "redirect:/curriculum/show/" + user.getId();
		} else {
			model.addAttribute("hasCurriculum", false);
			return "home/homeSignedIn";
		}
			}
			catch (UsernameNotFoundException e) {
				logger.error("usuario no encontrado... ignorando");
			}
		
		}
		model.addAttribute("curriculumSearchForm", new CurriculumSearchForm());

		return "home/homeNotSignedIn";
	}

	/**
	 * Acción que devuelve la página estática con información sobre el grupo de
	 * desarrollo.
	 * 
	 * @return vista con la página estática sobre el grupo de desarrollo.
	 */
	@RequestMapping(value = "/grupo", method = RequestMethod.GET)
	public String grupo() {
		return "home/grupo";
	}
}
