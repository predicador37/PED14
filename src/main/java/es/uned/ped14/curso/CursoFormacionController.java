package es.uned.ped14.curso;

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
@RequestMapping("/curso")
public class CursoFormacionController {

    private static final String CREATE_VIEW_NAME = "curso/create";
    Logger logger = LoggerFactory.getLogger(CurriculumRepository.class);
    
    private static final String LIST_VIEW_NAME = "curso/list";

	@Autowired
	private CursoFormacionService cursoService;
	
	@Autowired
	private CurriculumService curriculumService;
	
	@RequestMapping(value = "/create/{id}")
	public String create(@PathVariable("id")Long id, Model model) {
		CursoFormacionForm cursoForm = new CursoFormacionForm();
		cursoForm.setCurriculumId(id);
		model.addAttribute("cursoForm", cursoForm);
        return CREATE_VIEW_NAME;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute("cursoForm") CursoFormacionForm cursoForm,  Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return CREATE_VIEW_NAME;
		}
	    
	   Curriculum curriculum = new Curriculum();
		try {
			CursoFormacion curso = cursoForm.createCursoFormacion();
			 curriculum = curriculumService.findOne(cursoForm.getCurriculumId());
			 curriculum.addCurso(curso);
			 curriculumService.save(curriculum);
			 curriculumService.flush();
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
	 	  ModelAndView mav = new ModelAndView("curso/edit");
	 	
		try {
			  CursoFormacion curso = cursoService.findOne(id);
			  mav.addObject("curso", curso);

		} catch (CursoFormacionNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("No se encuentra curso");
		}
		 
	 	  return mav;
	 	 }
	 	  
	 	 @RequestMapping(value="/update", method=RequestMethod.POST)
	 	 public String update(@Valid @ModelAttribute("curso")CursoFormacion curso, Errors errors, RedirectAttributes ra)
	 	 {
	 	  if (errors.hasErrors()) {
	 	   return "curso/edit";
	 	  }
	 	  cursoService.save(curso);
	 	 
	 	  return "redirect:/curriculum/show/"+curso.getCurriculum().getId();
	 	 }
	 	 
	 
	 	
	
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id) {
	
		    Long curriculumId = null;
			try {
				CursoFormacion curso = cursoService.findOne(id);
				Curriculum curriculum = curso.getCurriculum();
				curriculumId = curriculum.getId();
				curriculum.removeCurso(curso);
				curriculumService.save(curriculum);
				cursoService.delete(curso);
				curriculumService.flush();
			} catch (CursoFormacionNotFoundException e) {
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
      
		return "redirect:/curso/list";
	}
	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) throws CursoFormacionNotFoundException  {
		
		 model.addAttribute("cursos", cursoService.findAll());
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "curso/list";
	}
	
	@RequestMapping(value = "/list/user/{id}", method = RequestMethod.GET)
	public String listByUser(@PathVariable("id") Long id, ModelMap model) throws CursoFormacionNotFoundException  {
		 Curriculum curriculum;
		try {
			curriculum = curriculumService.findOne(id);
			 model.addAttribute("cursos", cursoService.findByCurriculum(curriculum));
		} catch (CurriculumNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("Curriculum no encontrado");
			e.printStackTrace();
		}
		
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
      
		return "curso/list";
	}
	
}
