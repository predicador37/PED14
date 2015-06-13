package es.uned.ped14.curriculum;

import java.security.Principal;
import java.text.SimpleDateFormat;
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
import es.uned.ped14.conocimiento.Conocimiento;
import es.uned.ped14.conocimiento.ConocimientoNotFoundException;
import es.uned.ped14.conocimiento.ConocimientoService;
import es.uned.ped14.curso.CursoFormacion;
import es.uned.ped14.curso.CursoFormacionNotFoundException;
import es.uned.ped14.curso.CursoFormacionService;
import es.uned.ped14.experiencia.ExperienciaProfesional;
import es.uned.ped14.experiencia.ExperienciaProfesionalNotFoundException;
import es.uned.ped14.experiencia.ExperienciaProfesionalService;
import es.uned.ped14.support.web.*;
import es.uned.ped14.titulacion.Titulacion;
import es.uned.ped14.titulacion.TitulacionNotFoundException;
import es.uned.ped14.titulacion.TitulacionService;

@Controller
@RequestMapping("/curriculum")
public class CurriculumController {
	Logger logger = LoggerFactory.getLogger(CurriculumController.class);
    private static final String CREATE_VIEW_NAME = "curriculum/create";
    private static final String LIST_VIEW_NAME = "curriculum/list";

	@Autowired
	private CurriculumService curriculumService;
	
	@Autowired
	private CursoFormacionService cursoFormacionService;
	
	@Autowired
	private ConocimientoService conocimientoService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TitulacionService titulacionService;
	
	@Autowired
	private ExperienciaProfesionalService experienciaProfesionalService;
	
	@RequestMapping(value = "/create")
	public String create(Model model, Authentication authentication,RedirectAttributes ra) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Account account = userService.findByEmail(userDetails.getUsername());
		if (account.getCurriculum() != null){
			MessageHelper.addErrorAttribute(ra,"curriculum.exists");
			return "redirect:/";
		} 
		model.addAttribute("curriculumForm", new CurriculumForm());
        return CREATE_VIEW_NAME;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute("curriculumForm") CurriculumForm curriculumForm, Authentication authentication, Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return CREATE_VIEW_NAME;
		}
		logger.info("Creando currículo y asociando a usuario");
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Curriculum curriculum = curriculumForm.createCurriculum();
		Account account = userService.findByEmail(userDetails.getUsername());
		curriculum.setUser(account);
		curriculumService.save(curriculum);
		curriculumService.flush();
		
		
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "redirect:/curriculum/show/"+curriculum.getId();
	}
	
	 @RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	 	 public ModelAndView edit(@PathVariable("id")Long id)
	 	 {
	 	  ModelAndView mav = new ModelAndView("curriculum/edit");
	 	 
		try {
			 Curriculum curriculum = curriculumService.findOne(id);
			 mav.addObject("curriculum", curriculum);
		} catch (CurriculumNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	  
	 	  return mav;
	 	 }
	 	  
	 	 @RequestMapping(value="/update", method=RequestMethod.POST)
	 	 public String update(@Valid @ModelAttribute("curriculum")Curriculum curriculum, Errors errors, BindingResult result, SessionStatus status)
	 	 {
	 	
	 	  if (errors.hasErrors()) {
	 	   return "curriculum/edit";
	 	  }
	 	  curriculumService.save(curriculum);
	 	  status.setComplete();
	 	  return "redirect:/curriculum/list";
	 	 }
	 
	
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id) {
		
		curriculumService.delete(id);
		
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "redirect:/curriculum/list";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) throws CurriculumNotFoundException  {
		
		 model.addAttribute("curriculos", curriculumService.findAll());
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "curriculum/list";
	}
	
	@RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
	public ModelAndView show(@PathVariable("id")Long id) throws CurriculumNotFoundException {
		
		 ModelAndView mav = new ModelAndView("curriculum/show");
		 Curriculum curriculum = curriculumService.findOne(id);
		try{
		 List<CursoFormacion> cursos = cursoFormacionService.findByCurriculum(curriculum);
		 mav.addObject("cursos", cursos);
		}
		catch (CursoFormacionNotFoundException e) {
			logger.info("No hay cursos que mostrar");
		}
		try{
			 List<ExperienciaProfesional> experiencias =experienciaProfesionalService.findByCurriculum(curriculum);
			 mav.addObject("experiencias", experiencias);
			}
			catch (ExperienciaProfesionalNotFoundException e) {
				logger.info("No hay cursos que mostrar");
			}
		try{
			 List<Titulacion> titulaciones = titulacionService.findByCurriculum(curriculum);
			 mav.addObject("titulaciones", titulaciones);
			}
			catch (TitulacionNotFoundException e) {
				logger.info("No hay titulaciones que mostrar");
			}
		try{
			 List<Conocimiento> conocimientos = conocimientoService.findByCurriculum(curriculum);
			 mav.addObject("conocimientos", conocimientos);
			}
			catch (ConocimientoNotFoundException e) {
				logger.info("No hay conocimientos que mostrar");
			}
		// SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	    // mav.addObject("dates", dateFormat);
	 	 mav.addObject("curriculum", curriculum);
	 	
	 	 return mav;
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
	}
	
	@RequestMapping(value = "/search")
	public String search(Model model) {
		model.addAttribute("curriculumSearchForm", new CurriculumSearchForm());
        return "curriculum/search";
	}
	
	@RequestMapping(value = "/results", method = RequestMethod.POST)
	public String results(@Valid @ModelAttribute("curriculumForm") CurriculumSearchForm curriculumSearchForm, ModelMap model, Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return "curriculum/search";
		}
		
		try {
			List<Curriculum> curriculos = curriculumService.findByOptionalParameters(curriculumSearchForm.getPais(), 
											curriculumSearchForm.getCiudad(), curriculumSearchForm.getExperiencia(), 
											curriculumSearchForm.getTitulacion(), curriculumSearchForm.getConocimiento());
			model.addAttribute("curriculos", curriculos);
		} catch (CurriculumNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("No se encontraron currículos para la búsqueda");
		}
		
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
		
		return "curriculum/list";
	}
	
}
