package es.uned.ped14.home;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.ModelFactory;

import es.uned.ped14.account.Account;
import es.uned.ped14.account.UserService;
import es.uned.ped14.curriculum.Curriculum;
import es.uned.ped14.curriculum.CurriculumNotFoundException;
import es.uned.ped14.curriculum.CurriculumSearchForm;
import es.uned.ped14.curriculum.CurriculumService;

@Controller
public class HomeController {
	
	@Autowired
	CurriculumService curriculumService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(@ModelAttribute("curriculos") ArrayList<Curriculum> curriculos, Principal principal, Model model) {
		
		if (curriculos.isEmpty()){
			try {
				curriculos = (ArrayList<Curriculum>) curriculumService.findAll();
				model.addAttribute("curriculos", curriculos);
			} catch (CurriculumNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else {
			model.addAttribute("curriculos", curriculos);
		}
		
		Account user = userService.findByEmail(principal.getName());
		if (user.getCurriculum() != null ) {
			model.addAttribute("hasCurriculum", true);
		}
		else {
			model.addAttribute("hasCurriculum", false);
			return principal != null ? "home/homeSignedIn" : "home/homeNotSignedIn";
		}
		
		model.addAttribute("curriculumSearchForm", new CurriculumSearchForm());
		
		return principal != null ? "redirect:/curriculum/show/" + user.getId() : "home/homeNotSignedIn";
	}
}
