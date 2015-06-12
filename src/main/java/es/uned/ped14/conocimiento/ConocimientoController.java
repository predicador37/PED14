package es.uned.ped14.conocimiento;

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
@RequestMapping("/conocimiento")
public class ConocimientoController {

    private static final String CREATE_VIEW_NAME = "conocimiento/create";
    Logger logger = LoggerFactory.getLogger(CurriculumRepository.class);
    
    private static final String LIST_VIEW_NAME = "conocimiento/list";

	@Autowired
	private ConocimientoService conocimientoService;
	
	@Autowired
	private CurriculumService curriculumService;
	
	@RequestMapping(value = "/create/{id}")
	public String create(@PathVariable("id")Long id, Model model) {
		ConocimientoForm conocimientoForm = new ConocimientoForm();
		conocimientoForm.setCurriculumId(id);
		model.addAttribute("conocimientoForm", conocimientoForm);
        return CREATE_VIEW_NAME;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute("conocimientoForm") ConocimientoForm conocimientoForm,  Errors errors, BindingResult result, SessionStatus status, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return CREATE_VIEW_NAME;
		}
	    
	   Curriculum curriculum = new Curriculum();
		try {
			Conocimiento conocimiento = conocimientoForm.createConocimiento();
			 curriculum = curriculumService.findOne(conocimientoForm.getCurriculumId());
			 logger.info("curriculo id: " + curriculum.getId());
			 logger.info("conocimiento id: " + conocimiento.getId());
			 conocimientoService.save(conocimiento);
			 curriculum.addConocimiento(conocimiento);
			 curriculumService.save(curriculum);
			 curriculumService.flush();
			 logger.info("despues de guardar: " +curriculum.getConocimientos().iterator().next().getId());
			 status.setComplete();
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
	 	  ModelAndView mav = new ModelAndView("conocimiento/edit");
	 	
		try {
			  Conocimiento conocimiento = conocimientoService.findOne(id);
			  mav.addObject("conocimiento", conocimiento);

		} catch (ConocimientoNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("No se encuentra conocimiento");
		}
		 
	 	  return mav;
	 	 }
	 	  
	 	 @RequestMapping(value="/update", method=RequestMethod.POST)
	 	 public String update(@Valid @ModelAttribute("conocimiento")Conocimiento conocimiento, Errors errors,BindingResult result, SessionStatus status, RedirectAttributes ra)
	 	 {
	 	  if (errors.hasErrors()) {
	 	   return "conocimiento/edit";
	 	  }
	 	  conocimientoService.save(conocimiento);
	 	  conocimientoService.flush();
		  status.setComplete();
	 	 
	 	  return "redirect:/curriculum/show/"+conocimiento.getCurriculum().getId();
	 	 }
	 	 
	 
	 	
	
	
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
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("Titulación no encontrada");
			}
		 
		
		
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "redirect:/curriculum/show/"+curriculumId;
	}
	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) throws ConocimientoNotFoundException  {
		
		 model.addAttribute("conocimientos", conocimientoService.findAll());
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "conocimiento/list";
	}
	
	@RequestMapping(value = "/list/user/{id}", method = RequestMethod.GET)
	public String listByUser(@PathVariable("id") Long id, ModelMap model) throws ConocimientoNotFoundException  {
		 Curriculum curriculum;
		try {
			curriculum = curriculumService.findOne(id);
			 model.addAttribute("conocimientos", conocimientoService.findByCurriculum(curriculum));
		} catch (CurriculumNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("Curriculum no encontrado");
			e.printStackTrace();
		}
		
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "conocimiento/list";
	}
	
}
