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
import es.uned.ped14.curriculum.CurriculumRepository;
import es.uned.ped14.curriculum.CurriculumService;
import es.uned.ped14.support.web.*;

@Controller
@RequestMapping("/experiencia")
public class ExperienciaProfesionalController {
	Logger logger = LoggerFactory.getLogger(CurriculumRepository.class);
    
	private static final String CREATE_VIEW_NAME = "experiencia/create";
    private static final String LIST_VIEW_NAME = "experiencia/list";

	@Autowired
	private ExperienciaProfesionalService experienciaService;
	
	@Autowired
	private CurriculumService curriculumService;
	
	@RequestMapping(value = "/create")
	public String experiencia(Model model) {
		model.addAttribute(new ExperienciaProfesionalForm());
        return CREATE_VIEW_NAME;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute ExperienciaProfesionalForm experienciaForm, Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return CREATE_VIEW_NAME;
		}
		experienciaService.save(experienciaForm.createExperienciaProfesional());
		
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "redirect:/experiencia/list";
	}
	
	 @RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	 	 public ModelAndView edit(@PathVariable("id")Long id)
	 	 {
	 	  ModelAndView mav = new ModelAndView("experiencia/edit");
	 	 
		try {
			 ExperienciaProfesional experiencia = experienciaService.findOne(id);
			mav.addObject("experiencia", experiencia);
		} catch (ExperienciaProfesionalNotFoundException e) {
			logger.error("Experiencia profesional no encontrada");
		}
	 	  
	 	  return mav;
	 	 }
	 	  
	 	 @RequestMapping(value="/update", method=RequestMethod.POST)
	 	 public String update(@Valid @ModelAttribute("experiencia")ExperienciaProfesional experiencia, Errors errors, BindingResult result, SessionStatus status)
	 	 {
	 	
	 	  if (errors.hasErrors()) {
	 	   return "experiencia/edit";
	 	  }
	 	  experienciaService.save(experiencia);
	 	  status.setComplete();
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
			//(titulacionService.delete(titulacion);
		} catch (ExperienciaProfesionalNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
	
	
    // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
  
	return "redirect:/curriculum/show/"+curriculumId;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) throws ExperienciaProfesionalNotFoundException  {
		
		 model.addAttribute("experienciaes", experienciaService.findAll());
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "experiencia/list";
	}
	
}
