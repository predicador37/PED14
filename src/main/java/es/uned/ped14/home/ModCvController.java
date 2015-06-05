package es.uned.ped14.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ModCvController {
	
	@RequestMapping("/modcv")	 
    public String index() {
        return "modificar/modcv";
    }	
}
