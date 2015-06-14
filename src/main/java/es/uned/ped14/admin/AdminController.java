package es.uned.ped14.admin;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.ModelFactory;

import es.uned.ped14.account.Account;
import es.uned.ped14.account.Role;
import es.uned.ped14.account.RoleRepositoryInterface;
import es.uned.ped14.account.RoleService;
import es.uned.ped14.account.UserService;
import es.uned.ped14.curriculum.Curriculum;
import es.uned.ped14.curriculum.CurriculumController;
import es.uned.ped14.curriculum.CurriculumSearchForm;

@Controller
@RequestMapping("/admin")
public class AdminController {
	Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	FuncionService funcionService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String index(Model model,  Authentication authentication, @ModelAttribute("create") String create, @ModelAttribute("edit") String edit, @ModelAttribute("delete") String delete) {
		logger.info("Starting admin controller");
		//UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		//Account user = userService.findByEmail(userDetails.getUsername());
		
		if (create != null && !(create.trim()).isEmpty()){
			funcionService.setCreate(Boolean.valueOf(create));
			userService.changeState("ROLE_CREATE", Boolean.valueOf(create));
		}
		
		if (edit != null && !(edit.trim()).isEmpty()){
			funcionService.setEdit(Boolean.valueOf(edit));
			userService.changeState("ROLE_EDIT", Boolean.valueOf(create));
			
		}
		if (delete != null && !(delete.trim()).isEmpty()) {
			funcionService.setDelete(Boolean.valueOf(delete));
			userService.changeState("ROLE_DELETE", Boolean.valueOf(create));
			
		}
		model.addAttribute("create", funcionService.getCreate());
		model.addAttribute("edit", funcionService.getEdit());
		model.addAttribute("delete", funcionService.getDelete());
		return "admin/view";
	}
	
}
