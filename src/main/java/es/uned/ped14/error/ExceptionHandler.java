package es.uned.ped14.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Throwables;

/**
 * Manejador de excepciones general para la aplicación.
 */
@ControllerAdvice
class ExceptionHandler {

	/**
	 * Método que gestiona las excepciones lanzadas por los manejadores.
	 *
	 * @param exception
	 *            , la excepción.
	 * @param request
	 *            , la petición HTTP.
	 * 
	 * @return modelo y vista para el error.
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
	public ModelAndView exception(Exception exception, WebRequest request) {
		ModelAndView modelAndView = new ModelAndView("error/general");
		modelAndView.addObject("errorMessage",
				Throwables.getRootCause(exception));
		return modelAndView;
	}
}