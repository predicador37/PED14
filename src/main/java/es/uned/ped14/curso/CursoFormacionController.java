package es.uned.ped14.curso;

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
@RequestMapping("/curso")
public class CursoFormacionController {

    private static final String CREATE_VIEW_NAME = "curso/create";
    private static final String LIST_VIEW_NAME = "curso/list";

	@Autowired
	private CursoFormacionService cursoFormacionService;
	
	@RequestMapping(value = "create")
	public String create(Model model) {
		model.addAttribute("cursoForm", new CursoFormacionForm());
        return CREATE_VIEW_NAME;
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("cursoForm") CursoFormacionForm cursoForm, Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return CREATE_VIEW_NAME;
		}
		cursoFormacionService.save(cursoForm.createCursoFormacion());
		
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "redirect:/curso/list";
	}
	
	 @RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	 	 public ModelAndView edit(@PathVariable("id")Long id)
	 	 {
	 	  ModelAndView mav = new ModelAndView("curso/edit");
	 	  CursoFormacion curso = cursoFormacionService.findOne(id);
	 	  mav.addObject("curso", curso);
	 	  return mav;
	 	 }
	 	  
	 	 @RequestMapping(value="/update", method=RequestMethod.POST)
	 	 public String update(@Valid @ModelAttribute("curso")CursoFormacion curso, Errors errors, BindingResult result, SessionStatus status)
	 	 {
	 	
	 	  if (errors.hasErrors()) {
	 	   return "curso/edit";
	 	  }
	 	  cursoFormacionService.save(curso);
	 	  status.setComplete();
	 	  return "redirect:/curso/list";
	 	 }
	 
	
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id) {
		
		cursoFormacionService.delete(id);
		
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "redirect:/curso/list";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) throws CursoFormacionNotFoundException  {
		
		 model.addAttribute("cursos", cursoFormacionService.findAll());
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "curso/list";
	}
	
}
