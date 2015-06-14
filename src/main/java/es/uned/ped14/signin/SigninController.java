package es.uned.ped14.signin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Clase SigninController, controlador de Spring MVC que gestiona el login en la
 * aplicación
 */
@Controller
public class SigninController {
	/**
	 * Acción que redirige a la vista del login.
	 * 
	 * @return cadena de texto con la vista de login.
	 */
	@RequestMapping(value = "signin")
	public String signin() {
		return "signin/signin";
	}
}
