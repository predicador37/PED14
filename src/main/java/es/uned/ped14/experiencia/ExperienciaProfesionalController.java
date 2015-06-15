package es.uned.ped14.experiencia;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.uned.ped14.curriculum.Curriculum;
import es.uned.ped14.curriculum.CurriculumNotFoundException;
import es.uned.ped14.curriculum.CurriculumRepository;
import es.uned.ped14.curriculum.CurriculumService;

/**
 * Clase ExperienciaProfesionalController, controlador de Spring MVC para la
 * clase ExperienciaProfesional. Gestiona todas las peticiones a URLs.
 */
@Controller
@RequestMapping("/experiencia")
public class ExperienciaProfesionalController {

	/** Logger para la depuración de errores. */
	Logger logger = LoggerFactory.getLogger(CurriculumRepository.class);

	/** Constante CREATE_VIEW_NAME, con el nombre de la vista de creación. */
	private static final String CREATE_VIEW_NAME = "experiencia/create";

	private static final String LIST_VIEW_NAME = "experiencia/list";

	@Autowired
	private ExperienciaProfesionalService experienciaService;

	@Autowired
	private CurriculumService curriculumService;

	/**
	 * Acción que permite crear una nueva experiencia profesional asociada a un
	 * currículum.
	 *
	 * @param model
	 *            , modelo para la vista.
	 * @param id
	 *            , identificador del currículum asociado.
	 * 
	 * @return cadena de texto con el nombre de la vista.
	 */
	@RequestMapping(value = "/create/{id}")
	public String create(@PathVariable("id") Long id, Model model) {
		ExperienciaProfesionalForm experienciaForm = new ExperienciaProfesionalForm();
		experienciaForm.setCurriculumId(id);
		model.addAttribute("experienciaForm", experienciaForm);
		return CREATE_VIEW_NAME;
	}

	/**
	 * Acción que permite añadir una nueva experiencia profesional, complementaria de la
	 * anterior.
	 *
	 * @param experienciaProfesionalForm
	 *            , objeto formulario para la adición de una experiencia
	 *            profesional.
	 * @param errors
	 *            , posibles errores relacionados con la acción.
	 * @param ra
	 *            , atributos a redirigir entre peticiones.
	 * @return cadena de texto con el nombre de la vista.
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(
			@Valid @ModelAttribute("experienciaForm") ExperienciaProfesionalForm experienciaForm,
			Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return CREATE_VIEW_NAME;
		}

		Curriculum curriculum = new Curriculum();
		try {
			ExperienciaProfesional experiencia = experienciaForm
					.createExperienciaProfesional();
			curriculum = curriculumService.findOne(experienciaForm
					.getCurriculumId());
			curriculum.addExperiencia(experiencia);
			curriculumService.save(curriculum);
		} catch (CurriculumNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("Curriculum no encontrado");
			e.printStackTrace();
		}

		// see /WEB-INF/i18n/messages.properties and
		// /WEB-INF/views/homeSignedIn.html

		return "redirect:/curriculum/show/" + curriculum.getId();
	}

	/**
	 * Acción que permite editar una experiencia profesional ya existente.
	 *
	 * @param id
	 *            , identificador del currículum asociado.
	 * @return modelo y vista.
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView("experiencia/edit");

		try {
			ExperienciaProfesional experiencia = experienciaService.findOne(id);
			mav.addObject("experiencia", experiencia);

		} catch (ExperienciaProfesionalNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("No se encuentra experiencia");
		}

		return mav;
	}

	/**
	 * Acción que permite actualizar los datos de una experiencia profesional,
	 * complementaria de la anterior.
	 *
	 * @param experiencia
	 *            , objeto de clase ExperienciaProfesional.
	 * @param errors
	 *            , posibles errores asociados con la acción.
	 * @param ra
	 *            , atributos a redirigir entre peticiones.
	 * @return cadena de texto con el nombre de la vista.
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(
			@Valid @ModelAttribute("experiencia") ExperienciaProfesional experiencia,
			Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return "experiencia/edit";
		}
		experienciaService.save(experiencia);

		return "redirect:/curriculum/show/"
				+ experiencia.getCurriculum().getId();
	}

	/**
	 * Acción que permite borrar una experiencia profesional.
	 *
	 * @param id
	 *            , identificador de la experiencia profesional.
	 * @return cadena de texto con el nombre de la vista.
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id) {

		Long curriculumId = null;
		try {
			ExperienciaProfesional experiencia = experienciaService.findOne(id);
			Curriculum curriculum = experiencia.getCurriculum();
			curriculumId = curriculum.getId();
			curriculum.removeExperiencia(experiencia);
			curriculumService.save(curriculum);
			experienciaService.delete(experiencia);
			curriculumService.flush();
		} catch (ExperienciaProfesionalNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Titulación no encontrada");
		}

		// see /WEB-INF/i18n/messages.properties and
		// /WEB-INF/views/homeSignedIn.html

		return "redirect:/curriculum/show/" + curriculumId;
	}

	/**
	 * Acción que permite listar todas las experiencias profesionales.
	 *
	 * @param model
	 *            , el modelo.
	 * @return cadena de texto con el nombre de la vista.
	 * @throws ExperienciaProfesionalNotFoundException
	 *             , excepción en caso de no encontrar experiencias
	 *             profesionales.
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model)
			throws ExperienciaProfesionalNotFoundException {

		model.addAttribute("experiencias", experienciaService.findAll());
		// see /WEB-INF/i18n/messages.properties and
		// /WEB-INF/views/homeSignedIn.html

		return "experiencia/list";
	}

	/**
	 * Acción que permite listar todas las experiencias asociadas a un
	 * currículum.
	 *
	 * @param model
	 *            , el modelo.
	 * @param id
	 *            , el identificador del currículum asociado.
	 * @return cadena de texto con el nombre de la vista.
	 * @throws ExperienciaProfesionalNotFoundException
	 *             , excepción en caso de no encontrar experiencias
	 *             profesionales.
	 */
	@RequestMapping(value = "/list/user/{id}", method = RequestMethod.GET)
	public String listByUser(@PathVariable("id") Long id, ModelMap model)
			throws ExperienciaProfesionalNotFoundException {
		Curriculum curriculum;
		try {
			curriculum = curriculumService.findOne(id);
			model.addAttribute("experiencias",
					experienciaService.findByCurriculum(curriculum));
		} catch (CurriculumNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("Curriculum no encontrado");
			e.printStackTrace();
		}

		// see /WEB-INF/i18n/messages.properties and
		// /WEB-INF/views/homeSignedIn.html

		return "experiencia/list";
	}
	/*
	 * Binder para poder pasar nulos en los campos de fecha. Incompatible con Thymeleaf...
	 */
	@InitBinder
	public void allowEmptyDateBinding( WebDataBinder binder )
	{
		// tell spring to set empty values as null instead of empty string.
	    binder.registerCustomEditor( String.class, new StringTrimmerEditor( true ));
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    dateFormat.setLenient(false);
	    // Allow for null values in date fields.
	    binder.registerCustomEditor( Date.class, new CustomDateEditor( dateFormat, true ));
	  
	}

}
