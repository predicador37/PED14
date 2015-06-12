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
	
	@RequestMapping(value = "/create")
	public String titulacion(Model model) {
		model.addAttribute(new TitulacionForm());
        return CREATE_VIEW_NAME;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute TitulacionForm titulacionForm, Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return CREATE_VIEW_NAME;
		}
	
		titulacionService.save(titulacionForm.createTitulacion());
		
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "redirect:titulacion/list";
	}
	
	 @RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	 	 public ModelAndView edit(@PathVariable("id")Long id)
	 	 {
	 	  ModelAndView mav = new ModelAndView("titulacion/edit");
	 	  Titulacion titulacion;
		try {
			titulacion = titulacionService.findOne(id);
			  mav.addObject("titulacion", titulacion);
		} catch (TitulacionNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("No se encuentra titulacion");
		}
	 	
	 	  return mav;
	 	 }
	 
	 @RequestMapping(value="/curriculum/{curriculumId}/edit/{id}", method=RequestMethod.GET)
 	 public ModelAndView editByUser(@PathVariable("id")Long id, @PathVariable("curriculumId")Long curriculumId)
 	 {
 	  ModelAndView mav = new ModelAndView("titulacion/editByCurriculum");
 	
	try {
		Titulacion titulacion = titulacionService.findOne(id);
		  mav.addObject("titulacion", titulacion);
	} catch (TitulacionNotFoundException e) {
		// TODO Auto-generated catch block
		logger.error("No se encuentra titulacion");
	}
 	
 	  mav.addObject("curriculumId", curriculumId);
 	  return mav;
 	 }
	 
	 	  
	 	 @RequestMapping(value="/update", method=RequestMethod.POST)
	 	 public String update(@Valid @ModelAttribute("titulacion")Titulacion titulacion, Errors errors, BindingResult result, SessionStatus status)
	 	 {
	 	
	 	  if (errors.hasErrors()) {
	 	   return "titulacion/edit";
	 	  }
	 	  titulacionService.save(titulacion);
	 	  status.setComplete();
	 	  return "redirect:/titulacion/list";
	 	 }
	 	 
	 	 @RequestMapping(value="/update/curriculum", method=RequestMethod.POST)
	 	 public String updateCurriculum(@Valid @ModelAttribute("titulacion")Titulacion titulacion, @ModelAttribute("curriculumId")String curriculumId, Errors errors, BindingResult result, SessionStatus status, RedirectAttributes ra)
	 	 {
	 	
	 	  if (errors.hasErrors()) {
	 	   return "titulacion/editByCurriculum";
	 	  }
	 	  titulacionService.save(titulacion);
	 	  status.setComplete();
	 	  return "redirect:/curriculum/show/" + curriculumId;
	 	 }
	 
	
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id) {
	
		
			titulacionService.delete(id);
		
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "redirect:/titulacion/list";
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
		 Curriculum curriculum = curriculumService.findOne(id);
		 model.addAttribute("titulaciones", titulacionService.findByCurriculum(curriculum));
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "titulacion/list";
	}
	
}
