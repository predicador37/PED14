package es.uned.ped14.titulacion;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import es.uned.ped14.support.web.*;

@Controller
@RequestMapping("/titulacion")
public class TitulacionController {

    private static final String CREATE_VIEW_NAME = "titulacion/create";
    Logger logger = LoggerFactory.getLogger(CurriculumRepository.class);
    
    private static final String LIST_VIEW_NAME = "titulacion/list";

	@Autowired
	private TitulacionService titulacionService;
	
	@Autowired
	private CurriculumService curriculumService;
	
	@RequestMapping(value = "/create/{id}")
	public String create(@PathVariable("id")Long id, Model model) {
		TitulacionForm titulacionForm = new TitulacionForm();
		titulacionForm.setCurriculumId(id);
		model.addAttribute("titulacionForm", titulacionForm);
        return CREATE_VIEW_NAME;
	}
	
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
	 	  
	 	 @RequestMapping(value="/update", method=RequestMethod.POST)
	 	 public String update(@Valid @ModelAttribute("titulacion")Titulacion titulacion, Errors errors, RedirectAttributes ra)
	 	 {
	 	  if (errors.hasErrors()) {
	 	   return "titulacion/edit";
	 	  }
	 	  titulacionService.save(titulacion);
	 	 
	 	  return "redirect:/curriculum/show/"+titulacion.getCurriculum().getId();
	 	 }
	 	 
	 
	 	
	
	
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
	
	@RequestMapping(value = "/curriculum/{curriculumId}/remove/{id}", method = RequestMethod.GET)
	public String removeFromUser(@PathVariable("curriculumId") Long curriculumId, @PathVariable("id") Long id) {
	
		
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "redirect:/titulacion/list";
	}
	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) throws TitulacionNotFoundException  {
		
		 model.addAttribute("titulaciones", titulacionService.findAll());
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "titulacion/list";
	}
	
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
	
}
