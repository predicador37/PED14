package es.uned.ped14.error;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Throwables;

/**
 * Clase CustomErrorController, controlador de Spring MVC que gestiona los
 * errores y excepciones de forma general.
 */
@Controller
class CustomErrorController {

	/**
	 * Acción que muestra una página de error, definida en el elemento
	 * custom-error del archivo web.xml
	 *
	 * @param request
	 *            , la petición HTTP.
	 * @param response
	 *            , la respuesta HTTP.
	 * @param model
	 *            , el modelo.
	 * 
	 * @return cadena de texto con el nombre de la vista de la página de error.
	 */
	@RequestMapping("generalError")
	public String generalError(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// retrieve some useful information from the request
		Integer statusCode = (Integer) request
				.getAttribute("javax.servlet.error.status_code");
		Throwable throwable = (Throwable) request
				.getAttribute("javax.servlet.error.exception");
		// String servletName = (String)
		// request.getAttribute("javax.servlet.error.servlet_name");
		String exceptionMessage = getExceptionMessage(throwable, statusCode);

		String requestUri = (String) request
				.getAttribute("javax.servlet.error.request_uri");
		if (requestUri == null) {
			requestUri = "Unknown";
		}

		String message = MessageFormat.format(
				"{0} returned for {1} with message {2}", statusCode,
				requestUri, exceptionMessage);

		model.addAttribute("errorMessage", message);
		return "error/general";
	}

	/**
	 * Método que recupera el mensaje de una excepción.
	 *
	 * @param throwable
	 *            , la excepción.
	 * @param statusCode
	 *            , el código de error.
	 * 
	 * @return cadena de texto con el mensaje de error.
	 */
	private String getExceptionMessage(Throwable throwable, Integer statusCode) {
		if (throwable != null) {
			return Throwables.getRootCause(throwable).getMessage();
		}
		HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
		return httpStatus.getReasonPhrase();
	}
}
