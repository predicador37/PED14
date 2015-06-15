package es.uned.ped14.curriculum;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.uned.ped14.account.Account;
import es.uned.ped14.account.UserService;
import es.uned.ped14.conocimiento.Conocimiento;
import es.uned.ped14.conocimiento.ConocimientoNotFoundException;
import es.uned.ped14.conocimiento.ConocimientoService;
import es.uned.ped14.curso.CursoFormacion;
import es.uned.ped14.curso.CursoFormacionNotFoundException;
import es.uned.ped14.curso.CursoFormacionService;
import es.uned.ped14.experiencia.ExperienciaProfesional;
import es.uned.ped14.experiencia.ExperienciaProfesionalNotFoundException;
import es.uned.ped14.experiencia.ExperienciaProfesionalService;
import es.uned.ped14.support.web.MessageHelper;
import es.uned.ped14.titulacion.Titulacion;
import es.uned.ped14.titulacion.TitulacionNotFoundException;
import es.uned.ped14.titulacion.TitulacionService;

/**
 * Clase CurriculumController, controlador de Spring MVC para la clase
 * Currículum. Gestiona todas las peticiones a URLs.
 */
@Controller
@RequestMapping("/curriculum")
public class CurriculumController {

	/** Logger para la depuración de errores. */
	Logger logger = LoggerFactory.getLogger(CurriculumController.class);

	/** Constante CREATE_VIEW_NAME, con el nombre de la vista de creación. */
	private static final String CREATE_VIEW_NAME = "curriculum/create";

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

	/**
	 * Acción que permite crear un nuevo currículum.
	 *
	 * @param model
	 *            , modelo para la vista.
	 * @param authentication
	 *            , datos del usuario.
	 * @param ra
	 *            , atributos a redirigir entre peticiones.
	 * @return cadena de texto con el nombre de la vista.
	 */
	@RequestMapping(value = "/create")
	public String create(Model model, Authentication authentication,
			RedirectAttributes ra) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Account account = userService.findByEmail(userDetails.getUsername());
		if (account.getCurriculum() != null) {
			MessageHelper.addErrorAttribute(ra, "curriculum.exists");
			return "redirect:/";
		}
		model.addAttribute("curriculumForm", new CurriculumForm());
		return CREATE_VIEW_NAME;
	}

	/**
	 * Acción que permite añadir un nuevo currículum, complementaria de la
	 * anterior.
	 *
	 * @param curriculumForm
	 *            , objeto formulario para la adición de currículum.
	 * @param authentication
	 *            , datos del usuario.
	 * @param errors
	 *            , posibles errores relacionados con la acción.
	 * @param ra
	 *            , atributos a redirigir entre peticiones.
	 * @return cadena de texto con el nombre de la vista.
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(
			@Valid @ModelAttribute("curriculumForm") CurriculumForm curriculumForm,
			 Errors errors, Authentication authentication, RedirectAttributes ra, BindingResult result) {
		logger.info("Iniciando acción añadir...");
		if (errors.hasErrors() || result.hasErrors()) {
			logger.error("form data has errors");
			return CREATE_VIEW_NAME;
		}
		logger.info("Creando currículo y asociando a usuario");
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Curriculum curriculum = curriculumForm.createCurriculum();
		Account account = userService.findByEmail(userDetails.getUsername());
		curriculum.setUser(account);
		curriculumService.save(curriculum);
		curriculumService.flush();

		// see /WEB-INF/i18n/messages.properties and
		// /WEB-INF/views/homeSignedIn.html

		return "redirect:/curriculum/show/" + curriculum.getId();
	}

	/**
	 * Acción que permite editar un currículum ya existente.
	 *
	 * @param id
	 *            , identificador del currículum.
	 * @return modelo y vista.
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView("curriculum/edit");

		try {
			Curriculum curriculum = curriculumService.findOne(id);
			mav.addObject("curriculum", curriculum);
		} catch (CurriculumNotFoundException e) {

			e.printStackTrace();
		}

		return mav;
	}

	/**
	 * Acción que permite actualizar los datos de un currículum, complementaria
	 * de la anterior.
	 *
	 * @param curriculum
	 *            , objeto de clase Currículum.
	 * @param errors
	 *            , posibles errores asociados con la acción.
	 * @param result
	 *            , resultado de la acción.
	 * @param status
	 *            , estado de la sesión.
	 * @return cadena de texto con el nombre de la vista.
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(
			@Valid @ModelAttribute("curriculum") Curriculum curriculum,
			Errors errors, BindingResult result, SessionStatus status) {

		if (errors.hasErrors()) {
			return "curriculum/edit";
		}
		curriculumService.save(curriculum);
		status.setComplete();
		return "redirect:/curriculum/list";
	}

	/**
	 * Acción que permite borrar un currículo.
	 *
	 * @param id
	 *            , identificador asociado al currículum.
	 * @return cadena de texto con el nombre de la vista.
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id) {

		curriculumService.delete(id);

		// see /WEB-INF/i18n/messages.properties and
		// /WEB-INF/views/homeSignedIn.html

		return "redirect:/curriculum/list";
	}

	/**
	 * Acción que permite listar un conjunto de currículos.
	 *
	 * @param model
	 *            , el modelo.
	 * @return cadena de texto con el nombre de la vista.
	 * @throws CurriculumNotFoundException
	 *             , excepción en caso de no encontrar currículos.
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) throws CurriculumNotFoundException {

		model.addAttribute("curriculos", curriculumService.findAll());

		return "curriculum/list";
	}

	/**
	 * Acción que permite mostrar los datos asociados a un currículum.
	 *
	 * @param id
	 *            , identificador asociado al currículum.
	 * @return modelo y vista.
	 * @throws CurriculumNotFoundException
	 *             , excepción en caso de no encontrar el currículum.
	 */
	@RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
	public ModelAndView show(@PathVariable("id") Long id)
			throws CurriculumNotFoundException {

		ModelAndView mav = new ModelAndView("curriculum/show");
		Curriculum curriculum = curriculumService.findOne(id);
		try {
			List<CursoFormacion> cursos = cursoFormacionService
					.findByCurriculum(curriculum);
			mav.addObject("cursos", cursos);
		} catch (CursoFormacionNotFoundException e) {
			logger.info("No hay cursos que mostrar");
		}
		try {
			List<ExperienciaProfesional> experiencias = experienciaProfesionalService
					.findByCurriculum(curriculum);
			mav.addObject("experiencias", experiencias);
		} catch (ExperienciaProfesionalNotFoundException e) {
			logger.info("No hay cursos que mostrar");
		}
		try {
			List<Titulacion> titulaciones = titulacionService
					.findByCurriculum(curriculum);
			mav.addObject("titulaciones", titulaciones);
		} catch (TitulacionNotFoundException e) {
			logger.info("No hay titulaciones que mostrar");
		}
		try {
			List<Conocimiento> conocimientos = conocimientoService
					.findByCurriculum(curriculum);
			mav.addObject("conocimientos", conocimientos);
		} catch (ConocimientoNotFoundException e) {
			logger.info("No hay conocimientos que mostrar");
		}

		mav.addObject("curriculum", curriculum);

		return mav;

	}

	/**
	 * Acción que permite buscar currículos por una serie de parámetros.
	 *
	 * @param model
	 *            , el modelo.
	 * @return cadena de texto con el nombre de la vista asociada.
	 */
	@RequestMapping(value = "/search")
	public String search(Model model) {
		model.addAttribute("curriculumSearchForm", new CurriculumSearchForm());
		return "curriculum/search";
	}

	/**
	 * Acción que permite visualizar los resultados de búsqueda de currículos,
	 * complementaria de la anterior.
	 *
	 * @param curriculumSearchForm
	 *            , objeto de tipo formulario con los campos de búsqueda.
	 * @param model
	 *            , el modelo.
	 * @param errors
	 *            , posibles errores asociados a la acción.
	 * @param ra
	 *            , atributos a redirigir entre peticiones.
	 * @return cadena de texto con el nombre de la vista asociada.
	 */
	@RequestMapping(value = "/results", method = RequestMethod.POST)
	public String results(
			@Valid @ModelAttribute("curriculumForm") CurriculumSearchForm curriculumSearchForm,
			 Errors errors, ModelMap model, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return "curriculum/search";
		}
		List<Curriculum> curriculos = new ArrayList<Curriculum>();
	
			curriculos = curriculumService
					.findByOptionalParameters(curriculumSearchForm.getPais(),
							curriculumSearchForm.getCiudad(),
							curriculumSearchForm.getExperiencia(),
							curriculumSearchForm.getTitulacion(),
							curriculumSearchForm.getConocimiento());
			if (curriculos.isEmpty()) {
				logger.info("Curriculum not found");
				model.addAttribute("notFound", true);
				ra.addFlashAttribute("notFound", true);
			}
			else {
			model.addAttribute("notFound", false);
			ra.addFlashAttribute("notFound", false);
			}
			model.addAttribute("curriculos", curriculos);
			ra.addFlashAttribute("curriculos", curriculos);

		// see /WEB-INF/i18n/messages.properties and
		// /WEB-INF/views/homeSignedIn.html

		return "redirect:/";
	}

	/**
	 * Acción que permite subir un archivo utilizando AJAX.
	 *
	 * @param file
	 *            , el archivo en cuestión.
	 * @param authentication
	 *            , datos del usuario.
	 * @param request
	 *            , la petición HTTP asociada.
	 * @return booleano que indica el resultado de la acción.
	 */
	@RequestMapping(value = "/ajaxuploadFile", method = RequestMethod.POST)
	public @ResponseBody String ajaxUploadFile(
			@RequestParam("file") MultipartFile file,
			Authentication authentication, HttpServletRequest request) {

		logger.info("reached ajaxuploadFile");
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Account user = userService.findByEmail(userDetails.getUsername());
		// if !(validateImage(file));

		if (saveFile(file, user, request)) {
			logger.info("archivo guardado");
			String[] parts = (file.getOriginalFilename()).split("\\.");
			return "../../../resources/files/" + user.getId() + "_file." + parts[1];
		} else
			return "";
	}

	/**
	 * Acción que permite subir una imagen utilizando AJAX.
	 *
	 * @param imagen
	 *            , la imagen en cuestión.
	 * @param authentication
	 *            , datos del usuario.
	 * @param request
	 *            , la petición HTTP asociada.
	 * @return booleano que indica el resultado de la acción.
	 */
	@RequestMapping(value = "/ajaxuploadImage", method = RequestMethod.POST)
	public @ResponseBody String ajaxUploadImage(
			@RequestParam("imagen") MultipartFile imagen,
			Authentication authentication, HttpServletRequest request) {

		logger.info("reached ajaxuploadImage");
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Account user = userService.findByEmail(userDetails.getUsername());
		// if !(validateImage(file));

		if (saveFile(imagen, user, request)) {
			logger.info("archivo guardado");
			String[] parts = (imagen.getOriginalFilename()).split("\\.");
			return "../../../resources/files/" + user.getId() + "_img." + parts[1];
		} else
			return "";
	}

	/**
	 * Validar imagen, permite validar si el tipo de imagen está soportado..
	 *
	 * @param image
	 *            , la imagen en cuestión.
	 * @return booleano indicando el resultado de la operación.
	 */
	private Boolean validateImage(MultipartFile image) {
		if (!image.getContentType().equals("image/jpeg")) {
			return false;
		}
		return true;
	}

	/**
	 * Método que permite guardar un archivo en el sistema de archivos, en un
	 * directorio de la aplicación desplegada.
	 *
	 * @param file
	 *            , el archivo en cuestión.
	 * @param user
	 *            , usuario asociado al archivo.
	 * @param request
	 *            , petición HTTP asociada.
	 * @return booleano indicando el resultado de la operación.
	 */
	private Boolean saveFile(MultipartFile file, Account user,
			HttpServletRequest request) {
		if (!file.isEmpty()) {
			logger.info("not empty");
			try {
				byte[] bytes = file.getBytes();
				String[] parts = (file.getOriginalFilename()).split("\\.");

				// Crear el directorio para almacenar el archivo
				String rootPath = request.getServletContext().getRealPath("/");
				logger.info("catalina home: " + rootPath);
				File dir = new File(rootPath + File.separator + "resources"
						+ File.separator + "files");
				if (!dir.exists())
					dir.mkdirs();

				// Crear el archivo en el servidor
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + user.getId() + "." + parts[1]);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				logger.info("Archivo subido a " + serverFile.getAbsolutePath());
			} catch (Exception e) {
				logger.info("No se pudo subir el archivo " + user.getId()
						+ " => " + e.getMessage());
				return false;
			}
		} else {
			logger.info("No se pudo subir el archivo " + user.getId()
					+ " porque estaba vacío.");
			return false;
		}
		return true;
	}

}
