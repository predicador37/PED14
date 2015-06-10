package es.uned.ped14.titulacion;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.uned.ped14.account.*;
import es.uned.ped14.curriculum.CurriculumRepository;
import es.uned.ped14.support.web.*;

@Controller
public class TitulacionController {

    private static final String CREATE_VIEW_NAME = "titulacion/create";
    Logger logger = LoggerFactory.getLogger(CurriculumRepository.class);
    
	@Autowired
	private TitulacionService titulacionService;
	
	@RequestMapping(value = "titulacion/create")
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
	@RequestMapping(value = "titulacion/list", method = RequestMethod.GET)
	public String list(ModelMap model) throws TitulacionNotFoundException  {
		
		 model.addAttribute("titulaciones", titulacionService.findAll());
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "titulacion/list";
	}
	
}
