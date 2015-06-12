package es.uned.ped14.experiencia;

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
@RequestMapping("/experiencia")
public class ExperienciaProfesionalController {

    private static final String CREATE_VIEW_NAME = "experiencia/create";
    Logger logger = LoggerFactory.getLogger(CurriculumRepository.class);
    
    private static final String LIST_VIEW_NAME = "experiencia/list";

	@Autowired
	private ExperienciaProfesionalService experienciaService;
	
	@Autowired
	private CurriculumService curriculumService;
	
	@RequestMapping(value = "/create/{id}")
	public String create(@PathVariable("id")Long id, Model model) {
		ExperienciaProfesionalForm experienciaForm = new ExperienciaProfesionalForm();
		experienciaForm.setCurriculumId(id);
		model.addAttribute("experienciaForm", experienciaForm);
        return CREATE_VIEW_NAME;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute("experienciaForm") ExperienciaProfesionalForm experienciaForm,  Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return CREATE_VIEW_NAME;
		}
	    
	   Curriculum curriculum = new Curriculum();
		try {
			ExperienciaProfesional experiencia = experienciaForm.createExperienciaProfesional();
			 curriculum = curriculumService.findOne(experienciaForm.getCurriculumId());
			 curriculum.addExperiencia(experiencia);
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
	 	  
	 	 @RequestMapping(value="/update", method=RequestMethod.POST)
	 	 public String update(@Valid @ModelAttribute("experiencia")ExperienciaProfesional experiencia, Errors errors, RedirectAttributes ra)
	 	 {
	 	  if (errors.hasErrors()) {
	 	   return "experiencia/edit";
	 	  }
	 	  experienciaService.save(experiencia);
	 	 
	 	  return "redirect:/curriculum/show/"+experiencia.getCurriculum().getId();
	 	 }
	 	 
	 
	 	
	
	
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
			} catch (ExperienciaProfesionalNotFoundException e) {
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
      
		return "redirect:/experiencia/list";
	}
	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) throws ExperienciaProfesionalNotFoundException  {
		
		 model.addAttribute("experiencias", experienciaService.findAll());
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "experiencia/list";
	}
	
	@RequestMapping(value = "/list/user/{id}", method = RequestMethod.GET)
	public String listByUser(@PathVariable("id") Long id, ModelMap model) throws ExperienciaProfesionalNotFoundException  {
		 Curriculum curriculum;
		try {
			curriculum = curriculumService.findOne(id);
			 model.addAttribute("experiencias", experienciaService.findByCurriculum(curriculum));
		} catch (CurriculumNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("Curriculum no encontrado");
			e.printStackTrace();
		}
		
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "experiencia/list";
	}
	
}
