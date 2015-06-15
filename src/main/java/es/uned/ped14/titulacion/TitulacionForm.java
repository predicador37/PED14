package es.uned.ped14.titulacion;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Clase TitulacionForm, que representa el objeto DTO para la creación de
 * titulaciones.
 */
public class TitulacionForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

	@NotBlank(message = TitulacionForm.NOT_BLANK_MESSAGE)
	private String descripcion;
	@NotNull(message = TitulacionForm.NOT_BLANK_MESSAGE)
	@Digits(integer=4, fraction=0)
	private Integer anyoFinalizacion;
	private Long curriculumId;

	public TitulacionForm() {

	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getAnyoFinalizacion() {
		return anyoFinalizacion;
	}

	public void setAnyoFinalizacion(Integer anyoFinalizacion) {
		this.anyoFinalizacion = anyoFinalizacion;
	}

	public Titulacion createTitulacion() {
		return new Titulacion(getDescripcion(), getAnyoFinalizacion());
	}

	public Long getCurriculumId() {
		return curriculumId;
	}

	public void setCurriculumId(Long curriculumId) {
		this.curriculumId = curriculumId;
	}

}
