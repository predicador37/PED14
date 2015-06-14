package es.uned.ped14.curso;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
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
 * Clase CursoFormacionController, controlador de Spring MVC para la clase
 * CursoFormacion. Gestiona todas las peticiones a URLs.
 */
@Controller
@RequestMapping("/curso")
public class CursoFormacionController {

	/** Logger para la depuración de errores. */
	Logger logger = LoggerFactory.getLogger(CurriculumRepository.class);

	/** Constante CREATE_VIEW_NAME, con el nombre de la vista de creación. */
	private static final String CREATE_VIEW_NAME = "curso/create";

	@Autowired
	private CursoFormacionService cursoService;

	@Autowired
	private CurriculumService curriculumService;

	/**
	 * Acción que permite crear un nuevo courso asociado a un currículum.
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
		CursoFormacionForm cursoForm = new CursoFormacionForm();
		cursoForm.setCurriculumId(id);
		model.addAttribute("cursoForm", cursoForm);
		return CREATE_VIEW_NAME;
	}

	/**
	 * Acción que permite añadir un nuevo curso, complementaria de la anterior.
	 *
	 * @param cursoForm
	 *            , objeto formulario para la adición de un curso.
	 * @param errors
	 *            , posibles errores relacionados con la acción.
	 * @param ra
	 *            , atributos a redirigir entre peticiones.
	 * @return cadena de texto con el nombre de la vista.
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(
			@Valid @ModelAttribute("cursoForm") CursoFormacionForm cursoForm,
			Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return CREATE_VIEW_NAME;
		}

		Curriculum curriculum = new Curriculum();
		try {
			CursoFormacion curso = cursoForm.createCursoFormacion();
			curriculum = curriculumService.findOne(cursoForm.getCurriculumId());
			curriculum.addCurso(curso);
			curriculumService.save(curriculum);
			curriculumService.flush();
		} catch (CurriculumNotFoundException e) {
			logger.error("Curriculum no encontrado");
			e.printStackTrace();
		}

		// see /WEB-INF/i18n/messages.properties and
		// /WEB-INF/views/homeSignedIn.html

		return "redirect:/curriculum/show/" + curriculum.getId();
	}

	/**
	 * Acción que permite editar un curso ya existente.
	 *
	 * @param id
	 *            , identificador del currículum asociado.
	 * @return modelo y vista.
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView("curso/edit");

		try {
			CursoFormacion curso = cursoService.findOne(id);
			mav.addObject("curso", curso);

		} catch (CursoFormacionNotFoundException e) {
			logger.error("No se encuentra curso");
		}

		return mav;
	}

	/**
	 * Acción que permite actualizar los datos de un curso, complementaria de la
	 * anterior.
	 *
	 * @param curriculum
	 *            , objeto de clase Curso.
	 * @param errors
	 *            , posibles errores asociados con la acción.
	 * @return cadena de texto con el nombre de la vista.
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("curso") CursoFormacion curso,
			Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return "curso/edit";
		}
		cursoService.save(curso);

		return "redirect:/curriculum/show/" + curso.getCurriculum().getId();
	}

	/**
	 * Acción que permite borrar un curso.
	 *
	 * @param id
	 *            , identificador del curso asociado.
	 * @return cadena de texto con el nombre de la vista.
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id) {

		Long curriculumId = null;
		try {
			CursoFormacion curso = cursoService.findOne(id);
			Curriculum curriculum = curso.getCurriculum();
			curriculumId = curriculum.getId();
			curriculum.removeCurso(curso);
			curriculumService.save(curriculum);
			cursoService.delete(curso);
			curriculumService.flush();
		} catch (CursoFormacionNotFoundException e) {
			e.printStackTrace();
			logger.error("Titulación no encontrada");
		}

		// see /WEB-INF/i18n/messages.properties and
		// /WEB-INF/views/homeSignedIn.html

		return "redirect:/curriculum/show/" + curriculumId;
	}

	/**
	 * Acción que permite listar todos los cursos.
	 *
	 * @param model
	 *            , el modelo.
	 * @return cadena de texto con el nombre de la vista.
	 * @throws CursoFormacionNotFoundException
	 *             , excepción en caso de no encontrar cursos.
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) throws CursoFormacionNotFoundException {

		model.addAttribute("cursos", cursoService.findAll());
		// see /WEB-INF/i18n/messages.properties and
		// /WEB-INF/views/homeSignedIn.html

		return "curso/list";
	}

	/**
	 * Acción que permite listar todos los cursos asociados a un currículum.
	 *
	 * @param model
	 *            , el modelo.
	 * @param id
	 *            , el identificador del currículum asociado.
	 * @return cadena de texto con el nombre de la vista.
	 * @throws CursoFormacionNotFoundException
	 *             , excepción en caso de no encontrar conocimientos.
	 */
	@RequestMapping(value = "/list/user/{id}", method = RequestMethod.GET)
	public String listByUser(@PathVariable("id") Long id, ModelMap model)
			throws CursoFormacionNotFoundException {
		Curriculum curriculum;
		try {
			curriculum = curriculumService.findOne(id);
			model.addAttribute("cursos",
					cursoService.findByCurriculum(curriculum));
		} catch (CurriculumNotFoundException e) {
			logger.error("Curriculum no encontrado");
			e.printStackTrace();
		}

		// see /WEB-INF/i18n/messages.properties and
		// /WEB-INF/views/homeSignedIn.html

		return "curso/list";
	}

}
