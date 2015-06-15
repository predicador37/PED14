package es.uned.ped14.admin;

import java.util.ConcurrentModificationException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.uned.ped14.account.Account;
import es.uned.ped14.account.AccountNotFoundException;
import es.uned.ped14.account.Role;
import es.uned.ped14.account.RoleService;
import es.uned.ped14.account.UserService;
import es.uned.ped14.curriculum.Curriculum;
import es.uned.ped14.curriculum.CurriculumService;
import es.uned.ped14.support.web.MessageHelper;

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
	CurriculumService curriculumService;

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

	/**
	 * Acción que envía a una página listado de usuarios para permitir su
	 * borrado.
	 *
	 * @return cadena de texto con la vista del listado de usuarios.
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public String delete(ModelMap model) throws AccountNotFoundException {
		List<Account> usuarios = userService.findAll();
		usuarios.remove(userService.findByEmail("admin@admin.com"));
		logger.info("Total de usuarios recuperados: " + usuarios.size());
		model.addAttribute("usuarios", usuarios);
		return "admin/delete";
	}

	/**
	 * Acción que elimina al usuario seleccionado del sistema.
	 * 
	 *
	 * @param id
	 *            , identificador numérico del usuario a eliminar.
	 * @param model
	 *            , el modelo.
	 * @param request
	 *            , petición HTTP.
	 * @param response
	 *            , respuesta HTTP
	 * @param auth
	 *            , datos de autenticación
	 * @param ra
	 *            , atributos a redireccionar entre peticiones.
	 * 
	 * @return cadena de texto con la vista del listado de usuarios de nuevo.
	 * @throws AccountNotFoundException
	 *             , excepción en caso de no encontrar el usuario.
	 */
	@RequestMapping(value = "/account/delete/{id}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public String deleteUser(@PathVariable("id") Long id, Model model,
			HttpServletRequest request, HttpServletResponse response,
			Authentication auth, RedirectAttributes ra)
			throws AccountNotFoundException {
		logger.info("Delete user controller");

		Account user = userService.findOne(id);
		UserDetails userDetails = userService.loadUserByUsername(user
				.getEmail());
		if (user.getCurriculum() != null) {
			Curriculum curriculum = user.getCurriculum();
			curriculum.setUser(null);
			curriculumService.save(curriculum);
			curriculumService.delete(curriculum.getId());
			curriculumService.flush();
			logger.info("Curriculum deleted");
		}

		user.setCurriculum(null);
		for (Role r : user.getRoles()) {
			user.removeRole(r);
			roleService.delete(r);
			roleService.flush();
		}
		userService.merge(user);
		userService.flush();
		try {
			userService.delete(user.getId());
		} catch (ConcurrentModificationException e) {
			logger.error("Error de concurrencia... pasable");
		}
		userService.flush();
		model.addAttribute("usuarios", userService.findAll());
		MessageHelper.addSuccessAttribute(ra, "delete.success");
		return "admin/delete";
	}

}
