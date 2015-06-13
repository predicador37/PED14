package es.uned.ped14.home;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import es.uned.ped14.curriculum.CurriculumSearchForm;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Principal principal, Model model) {
		model.addAttribute("curriculumSearchForm", new CurriculumSearchForm());
		return principal != null ? "home/homeSignedIn" : "home/homeNotSignedIn";
	}
}
