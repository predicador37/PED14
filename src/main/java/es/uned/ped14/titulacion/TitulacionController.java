package es.uned.ped14.titulacion;

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
@RequestMapping("/titulacion")
public class TitulacionController {

    private static final String CREATE_VIEW_NAME = "titulacion/create";
    private static final String LIST_VIEW_NAME = "titulacion/list";

	@Autowired
	private TitulacionService titulacionService;
	
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
      
		return "redirect:/titulacion/list";
	}
	
	 @RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	 	 public ModelAndView edit(@PathVariable("id")Long id)
	 	 {
	 	  ModelAndView mav = new ModelAndView("titulacion/edit");
	 	  Titulacion titulacion = titulacionService.findOne(id);
	 	  mav.addObject("titulacion", titulacion);
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
	 
	
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id) {
		
		titulacionService.delete(id);
		
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "redirect:/titulacion/list";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) throws TitulacionNotFoundException  {
		
		 model.addAttribute("titulaciones", titulacionService.findAll());
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "titulacion/list";
	}
	
}
