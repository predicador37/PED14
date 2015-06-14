package es.uned.ped14.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uned.ped14.account.RoleService;
import es.uned.ped14.account.UserService;

/**
 * Clase AdminController, controlador de Spring MVC para la clase Admin.
 * Gestiona todas las peticiones a URLs.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
	/** Logger para la depuración de errores. */
	Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	FuncionService funcionService;

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	/**
	 * Acción que permite visualizar y cambiar el estado de las funcionalidades
	 * de creación, edición y borrado de elementos de un currículum.
	 *
	 * @param model
	 *            , modelo para la vista.
	 * @param create
	 *            , booleano con el estado de la función de creación de
	 *            elementos.
	 * @param edit
	 *            , booleano con el estado de la función de edición de
	 *            elementos.
	 * @param delete
	 *            , booleano con el estado de la función de borrado de
	 *            elementos.
	 * 
	 * @return cadena de texto con el nombre de la vista.
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String index(Model model, Authentication authentication,
			@ModelAttribute("create") String create,
			@ModelAttribute("edit") String edit,
			@ModelAttribute("delete") String delete) {
		logger.info("Starting admin controller");
		// UserDetails userDetails = (UserDetails)
		// authentication.getPrincipal();
		// Account user = userService.findByEmail(userDetails.getUsername());

		if (create != null && !(create.trim()).isEmpty()) {
			funcionService.setCreate(Boolean.valueOf(create));
			userService.changeState("ROLE_CREATE", Boolean.valueOf(create));
		}

		if (edit != null && !(edit.trim()).isEmpty()) {
			funcionService.setEdit(Boolean.valueOf(edit));
			userService.changeState("ROLE_EDIT", Boolean.valueOf(create));

		}
		if (delete != null && !(delete.trim()).isEmpty()) {
			funcionService.setDelete(Boolean.valueOf(delete));
			userService.changeState("ROLE_DELETE", Boolean.valueOf(create));

		}
		model.addAttribute("create", funcionService.getCreate());
		model.addAttribute("edit", funcionService.getEdit());
		model.addAttribute("delete", funcionService.getDelete());
		return "admin/view";
	}

}
