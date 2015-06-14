package es.uned.ped14.titulacion;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Clase TitulacionForm, que representa el objeto DTO para la creación de
 * titulaciones.
 */
public class TitulacionForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

	@NotBlank(message = TitulacionForm.NOT_BLANK_MESSAGE)
	private String descripcion;
	@NotBlank(message = TitulacionForm.NOT_BLANK_MESSAGE)
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
