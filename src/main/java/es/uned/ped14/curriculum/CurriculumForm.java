package es.uned.ped14.curriculum;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Controller;

/**
 * Clase CurriculumForm, que representa el objeto DTO para la creación de
 * currículos.
 */
@Controller
public class CurriculumForm {

	/**
	 * Constante NOT_BLANK_MESSAGE, con el mensaje a mostrar en caso de campos
	 * nulos cuando no debieran.
	 */
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

	@NotBlank(message = CurriculumForm.NOT_BLANK_MESSAGE)
	private String nombre;
	@NotBlank(message = CurriculumForm.NOT_BLANK_MESSAGE)
	private String apellidos;
	@NotBlank(message = CurriculumForm.NOT_BLANK_MESSAGE)
	private String pais;
	@NotBlank(message = CurriculumForm.NOT_BLANK_MESSAGE)
	private String ciudad;
	
	private String urlImagen;
	private String urlArchivo;

	/**
	 * Crea el curriculum.
	 *
	 * @return devuelve un currículum.
	 */
	public Curriculum createCurriculum() {
		return new Curriculum(getNombre(), getApellidos(), getPais(),
				getCiudad(), getUrlImagen(), getUrlArchivo());
	}
	
	public CurriculumForm() {

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getUrlImagen() {
		return urlImagen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}

	public String getUrlArchivo() {
		return urlArchivo;
	}

	public void setUrlArchivo(String urlArchivo) {
		this.urlArchivo = urlArchivo;
	}

}
