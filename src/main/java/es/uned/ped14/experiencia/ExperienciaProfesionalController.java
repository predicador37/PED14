package es.uned.ped14.experiencia;

import java.util.List;

import javax.validation.Valid;

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
import es.uned.ped14.support.web.*;

@Controller
@RequestMapping("/experiencia")
public class ExperienciaProfesionalController {

    private static final String CREATE_VIEW_NAME = "experiencia/create";
    private static final String LIST_VIEW_NAME = "experiencia/list";

	@Autowired
	private ExperienciaProfesionalService experienciaService;
	
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
	 	  ExperienciaProfesional experiencia = experienciaService.findOne(id);
	 	  mav.addObject("experiencia", experiencia);
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
	 	  return "redirect:/experiencia/list";
	 	 }
	 
	
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id) {
		
		experienciaService.delete(id);
		
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "redirect:/experiencia/list";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) throws ExperienciaProfesionalNotFoundException  {
		
		 model.addAttribute("experienciaes", experienciaService.findAll());
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "experiencia/list";
	}
	
}
