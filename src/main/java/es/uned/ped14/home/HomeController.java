package es.uned.ped14.home;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.ModelFactory;

import es.uned.ped14.curriculum.Curriculum;
import es.uned.ped14.curriculum.CurriculumSearchForm;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(@ModelAttribute("curriculos") ArrayList<Curriculum> curriculos, Principal principal, Model model) {
		
		
		model.addAttribute("curriculumSearchForm", new CurriculumSearchForm());
		model.addAttribute("curriculos", curriculos);
		return principal != null ? "home/homeSignedIn" : "home/homeNotSignedIn";
	}
}
