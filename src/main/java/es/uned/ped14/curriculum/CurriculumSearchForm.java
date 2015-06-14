package es.uned.ped14.curriculum;

import org.springframework.stereotype.Controller;

// TODO: Auto-generated Javadoc
/**
 * Class CurriculumSearchForm, objeto DTO que recoge los campos de currículum
 * para las búsquedas.
 */
@Controller
public class CurriculumSearchForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

	private String pais;

	private String ciudad;

	private String titulacion;

	private String conocimiento;

	private Integer experiencia;

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

	public String getTitulacion() {
		return titulacion;
	}

	public void setTitulacion(String titulacion) {
		this.titulacion = titulacion;
	}

	public String getConocimiento() {
		return conocimiento;
	}

	public void setConocimiento(String conocimiento) {
		this.conocimiento = conocimiento;
	}

	public Integer getExperiencia() {
		return experiencia;
	}

	public void setExperiencia(Integer experiencia) {
		this.experiencia = experiencia;
	}

}
