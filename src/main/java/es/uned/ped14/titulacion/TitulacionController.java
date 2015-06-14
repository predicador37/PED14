package es.uned.ped14.titulacion;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.uned.ped14.account.*;
import es.uned.ped14.curriculum.Curriculum;
import es.uned.ped14.curriculum.CurriculumNotFoundException;
import es.uned.ped14.curriculum.CurriculumRepository;
import es.uned.ped14.curriculum.CurriculumService;
import es.uned.ped14.experiencia.ExperienciaProfesionalNotFoundException;
import es.uned.ped14.support.web.*;
/**
 * Clase TitulacionController, controlador de Spring MVC para la
 * clase Titulacion. Gestiona todas las peticiones a URLs.
 */
@Controller
@RequestMapping("/titulacion")
public class TitulacionController {
	
	/** Logger para la depuración de errores. */
	Logger logger = LoggerFactory.getLogger(CurriculumRepository.class);
    
    /** Constante CREATE_VIEW_NAME, con el nombre de la vista de creación. */
    private static final String CREATE_VIEW_NAME = "titulacion/create";
    

	@Autowired
	private TitulacionService titulacionService;
	
	@Autowired
	private CurriculumService curriculumService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * Acción que permite crear una nueva titulación asociada a un
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
	public String create(@PathVariable("id")Long id, Model model) {
		TitulacionForm titulacionForm = new TitulacionForm();
		titulacionForm.setCurriculumId(id);
		model.addAttribute("titulacionForm", titulacionForm);
        return CREATE_VIEW_NAME;
	}
	/**
	 * Acción que permite añadir una nueva titulación, complementaria de la
	 * anterior.
	 *
	 * @param titulacionForm
	 *            , objeto formulario para la adición de una titulación.
	 * @param errors
	 *            , posibles errores relacionados con la acción.
	 * @param ra
	 *            , atributos a redirigir entre peticiones.
	 * @return cadena de texto con el nombre de la vista.
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute("titulacionForm") TitulacionForm titulacionForm,  Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return CREATE_VIEW_NAME;
		}
	    
	   Curriculum curriculum = new Curriculum();
		try {
			Titulacion titulacion = titulacionForm.createTitulacion();
			 curriculum = curriculumService.findOne(titulacionForm.getCurriculumId());
			 logger.info("curriculo id: " + curriculum.getId());
			 logger.info("titulacion id: " + titulacion.getId());
			 curriculum.addTitulacion(titulacion);
			 curriculumService.save(curriculum);
		} catch (CurriculumNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("Curriculum no encontrado");
			e.printStackTrace();
		}
	    
		
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "redirect:/curriculum/show/"+curriculum.getId();
	}
	/**
	 * Acción que permite editar una titulación ya existente.
	 *
	 * @param id
	 *            , identificador del currículum asociado.
	 * @return modelo y vista.
	 */
	 @RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	 	 public ModelAndView edit(@PathVariable("id")Long id)
	 	 {
	 	  ModelAndView mav = new ModelAndView("titulacion/edit");
	 	
		try {
			  Titulacion titulacion = titulacionService.findOne(id);
			  mav.addObject("titulacion", titulacion);

		} catch (TitulacionNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("No se encuentra titulacion");
		}
		 
	 	  return mav;
	 	 }
	 /**
		 * Acción que permite actualizar los datos de una titulación,
		 * complementaria de la anterior.
		 *
		 * @param titulacion
		 *            , objeto de clase Titulacion.
		 * @param errors
		 *            , posibles errores asociados con la acción.
		 * @param ra
		 *            , atributos a redirigir entre peticiones.
		 * @return cadena de texto con el nombre de la vista.
		 */ 
	 	 @RequestMapping(value="/update", method=RequestMethod.POST)
	 	 public String update(@Valid @ModelAttribute("titulacion")Titulacion titulacion, Errors errors, RedirectAttributes ra)
	 	 {
	 	  if (errors.hasErrors()) {
	 	   return "titulacion/edit";
	 	  }
	 	  titulacionService.save(titulacion);
	 	 
	 	  return "redirect:/curriculum/show/"+titulacion.getCurriculum().getId();
	 	 }
	 	 
	 
	 	
	
	 	/**
	 	 * Acción que permite borrar una titulación.
	 	 *
	 	 * @param id
	 	 *            , identificador de la titulación.
	 	 * @return cadena de texto con el nombre de la vista.
	 	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id) {
	
		    Long curriculumId = null;
			try {
				Titulacion titulacion = titulacionService.findOne(id);
				Curriculum curriculum = titulacion.getCurriculum();
				curriculumId = curriculum.getId();
				curriculum.removeTitulacion(titulacion);
				curriculumService.save(curriculum);
				titulacionService.delete(titulacion);
				curriculumService.flush();
			} catch (TitulacionNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("Titulación no encontrada");
			}
		 
		
		
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "redirect:/curriculum/show/"+curriculumId;
	}
	
	/**
	 * Acción que permite listar todas las titulaciones.
	 *
	 * @param model
	 *            , el modelo.
	 * @return cadena de texto con el nombre de la vista.
	 * @throws TitulacionNotFoundException
	 *             , excepción en caso de no encontrar titulaciones.
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) throws TitulacionNotFoundException  {
		
		 model.addAttribute("titulaciones", titulacionService.findAll());
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "titulacion/list";
	}
	/**
	 * Acción que permite listar todas las titulaciones asociadas a un
	 * currículum.
	 *
	 * @param model
	 *            , el modelo.
	 * @param id
	 *            , el identificador del currículum asociado.
	 * @return cadena de texto con el nombre de la vista.
	 * @throws TitulacionProfesionalNotFoundException
	 *             , excepción en caso de no encontrar titulaciones.
	 *      
	 */
	@RequestMapping(value = "/list/user/{id}", method = RequestMethod.GET)
	public String listByUser(@PathVariable("id") Long id, ModelMap model) throws TitulacionNotFoundException  {
		 Curriculum curriculum;
		try {
			curriculum = curriculumService.findOne(id);
			 model.addAttribute("titulaciones", titulacionService.findByCurriculum(curriculum));
		} catch (CurriculumNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("Curriculum no encontrado");
			e.printStackTrace();
		}
		
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "titulacion/list";
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
	 * @throws TitulacionNotFoundException, en caso de no encontrar ningún elemento titulación.
	 * @return String con el número de "me gusta"
	 */
	@RequestMapping(value = "/like", method = RequestMethod.GET)
	public @ResponseBody String like(@RequestParam("id") Long id, ModelMap model, Authentication authentication) throws TitulacionNotFoundException {
		logger.info("Starting like controller...");
		Titulacion titulacion = titulacionService.findOne(id);
		return titulacionService.like(titulacion).toString();
		
	}
	
}
