package es.uned.ped14.conocimiento;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.uned.ped14.curriculum.Curriculum;
import es.uned.ped14.curriculum.CurriculumNotFoundException;
import es.uned.ped14.curriculum.CurriculumRepository;
import es.uned.ped14.curriculum.CurriculumService;
import es.uned.ped14.curso.CursoFormacion;
import es.uned.ped14.curso.CursoFormacionNotFoundException;

/**
 * Clase ConocimientoController, controlador de Spring MVC para la clase
 * Conocimiento. Gestiona todas las peticiones a URLs.
 */
@Controller
@RequestMapping("/conocimiento")
public class ConocimientoController {
	/** Logger para la depuración de errores. */
	Logger logger = LoggerFactory.getLogger(CurriculumRepository.class);

	/** Constante CREATE_VIEW_NAME, con el nombre de la vista de creación. */
	private static final String CREATE_VIEW_NAME = "conocimiento/create";

	@Autowired
	private ConocimientoService conocimientoService;

	@Autowired
	private CurriculumService curriculumService;

	/**
	 * Acción que permite crear un nuevo conocimiento asociado a un currículum.
	 *
	 * @param model
	 *            , modelo para la vista.
	 * @param id
	 *            , currículum asociado.
	 * 
	 * @return cadena de texto con el nombre de la vista.
	 */
	@RequestMapping(value = "/create/{id}")
	public String create(@PathVariable("id") Long id, Model model) {
		ConocimientoForm conocimientoForm = new ConocimientoForm();
		conocimientoForm.setCurriculumId(id);
		model.addAttribute("conocimientoForm", conocimientoForm);
		return CREATE_VIEW_NAME;
	}

	/**
	 * Acción que permite añadir un nuevo conocimiento, complementaria de la
	 * anterior.
	 *
	 * @param conocimientoForm
	 *            , objeto formulario para la adición de un conocimiento.
	 * @param errors
	 *            , posibles errores relacionados con la acción.
	 * @param result
	 *            , resultado de la acción.
	 * @param status
	 *            , estado de la sesión.
	 * @param ra
	 *            , atributos a redirigir entre peticiones.
	 * @return cadena de texto con el nombre de la vista.
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(
			@Valid @ModelAttribute("conocimientoForm") ConocimientoForm conocimientoForm,
			Errors errors, BindingResult result, SessionStatus status,
			RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return CREATE_VIEW_NAME;
		}

		Curriculum curriculum = new Curriculum();
		try {
			Conocimiento conocimiento = conocimientoForm.createConocimiento();
			curriculum = curriculumService.findOne(conocimientoForm
					.getCurriculumId());
			logger.info("curriculo id: " + curriculum.getId());
			logger.info("conocimiento id: " + conocimiento.getId());
			conocimientoService.save(conocimiento);
			curriculum.addConocimiento(conocimiento);
			curriculumService.save(curriculum);
			curriculumService.flush();
			logger.info("despues de guardar: "
					+ curriculum.getConocimientos().iterator().next().getId());
			status.setComplete();
		} catch (CurriculumNotFoundException e) {

			logger.error("Curriculum no encontrado");
			e.printStackTrace();
		}

		// see /WEB-INF/i18n/messages.properties and
		// /WEB-INF/views/homeSignedIn.html

		return "redirect:/curriculum/show/" + curriculum.getId();
	}

	/**
	 * Acción que permite editar un conocimiento ya existente.
	 *
	 * @param id
	 *            , identificador del currículum asociado.
	 * @return modelo y vista.
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView("conocimiento/edit");

		try {
			Conocimiento conocimiento = conocimientoService.findOne(id);
			mav.addObject("conocimiento", conocimiento);

		} catch (ConocimientoNotFoundException e) {

			logger.error("No se encuentra conocimiento");
		}

		return mav;
	}

	/**
	 * Acción que permite actualizar los datos de un conocimiento,
	 * complementaria de la anterior.
	 *
	 * @param curriculum
	 *            , objeto de clase Conocimiento.
	 * @param errors
	 *            , posibles errores asociados con la acción.
	 * @param result
	 *            , resultado de la acción.
	 * @param status
	 *            , estado de la sesión.
	 * @return cadena de texto con el nombre de la vista.
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(
			@Valid @ModelAttribute("conocimiento") Conocimiento conocimiento,
			Errors errors, BindingResult result, SessionStatus status,
			RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return "conocimiento/edit";
		}
		conocimientoService.save(conocimiento);
		conocimientoService.flush();
		status.setComplete();

		return "redirect:/curriculum/show/"
				+ conocimiento.getCurriculum().getId();
	}

	/**
	 * Acción que permite borrar un conocimiento.
	 *
	 * @param id
	 *            , identificador del conocimiento asociado.
	 * @return cadena de texto con el nombre de la vista.
	 */

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id) {

		Long curriculumId = null;
		try {
			Conocimiento conocimiento = conocimientoService.findOne(id);
			Curriculum curriculum = conocimiento.getCurriculum();
			curriculumId = curriculum.getId();
			curriculum.removeConocimiento(conocimiento);
			curriculumService.save(curriculum);
			conocimientoService.delete(conocimiento);
			curriculumService.flush();
		} catch (ConocimientoNotFoundException e) {

			e.printStackTrace();
			logger.error("Titulación no encontrada");
		}

		// see /WEB-INF/i18n/messages.properties and
		// /WEB-INF/views/homeSignedIn.html

		return "redirect:/curriculum/show/" + curriculumId;
	}

	/**
	 * Acción que permite listar todos los conocimientos.
	 *
	 * @param model
	 *            , el modelo.
	 * @return cadena de texto con el nombre de la vista.
	 * @throws ConocimientoNotFoundException
	 *             , excepción en caso de no encontrar conocimientos.
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) throws ConocimientoNotFoundException {

		model.addAttribute("conocimientos", conocimientoService.findAll());
		// see /WEB-INF/i18n/messages.properties and
		// /WEB-INF/views/homeSignedIn.html

		return "conocimiento/list";
	}

	/**
	 * Acción que permite listar todos los conocimientos asociados a un
	 * currículum.
	 *
	 * @param model
	 *            , el modelo.
	 * @param id
	 *            , el identificador del currículum asociado.
	 * @return cadena de texto con el nombre de la vista.
	 * @throws ConocimientoNotFoundException
	 *             , excepción en caso de no encontrar conocimientos.
	 */
	@RequestMapping(value = "/list/user/{id}", method = RequestMethod.GET)
	public String listByUser(@PathVariable("id") Long id, ModelMap model)
			throws ConocimientoNotFoundException {
		Curriculum curriculum;
		try {
			curriculum = curriculumService.findOne(id);
			model.addAttribute("conocimientos",
					conocimientoService.findByCurriculum(curriculum));
		} catch (CurriculumNotFoundException e) {

			logger.error("Curriculum no encontrado");
			e.printStackTrace();
		}

		// see /WEB-INF/i18n/messages.properties and
		// /WEB-INF/views/homeSignedIn.html

		return "conocimiento/list";
	}
	
	/**
	 * Acción que permite incrementar el número de "me gusta" utilizando AJAX.
	 *
	 * @param id
	 *            , identificador del elemento cuyo contador de "me gusta" se incrementará.
	 * @param authentication
	 *            , datos del usuario.
	 * @param model
	 *            , el modelo.
	 * @param request
	 *            , la petición HTTP asociada.
	 * @throws ConocimientoNotFoundException, en caso de no encontrar ningún elemento conocimiento.
	 * @return String con el número de "me gusta"
	 */
	@RequestMapping(value = "/like", method = RequestMethod.GET)
	public @ResponseBody String like(@RequestParam("id") Long id, ModelMap model, Authentication authentication) throws ConocimientoNotFoundException {
		logger.info("Starting like controller...");
		Conocimiento conocimiento = conocimientoService.findOne(id);
		return conocimientoService.like(conocimiento).toString();
		
	}

}
