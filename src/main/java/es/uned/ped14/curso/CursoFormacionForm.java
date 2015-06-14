package es.uned.ped14.curso;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Clase CursoFormacionForm, que representa el objeto DTO para la creación de
 * cursos.
 */
public class CursoFormacionForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

	@NotBlank(message = CursoFormacionForm.NOT_BLANK_MESSAGE)
	private String descripcion;
	@NotNull(message = CursoFormacionForm.NOT_BLANK_MESSAGE)
	private Integer numeroHoras;
	@NotNull(message = CursoFormacionForm.NOT_BLANK_MESSAGE)
	private Date fechaFinalizacion;
	private Long curriculumId;

	public CursoFormacionForm() {

	}

	public CursoFormacion createCursoFormacion() {
		return new CursoFormacion(getDescripcion(), getNumeroHoras(),
				getFechaFinalizacion());
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getNumeroHoras() {
		return numeroHoras;
	}

	public void setNumeroHoras(Integer numeroHoras) {
		this.numeroHoras = numeroHoras;
	}

	public Date getFechaFinalizacion() {
		return fechaFinalizacion;
	}

	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}

	public Long getCurriculumId() {
		return curriculumId;
	}

	public void setCurriculumId(Long curriculumId) {
		this.curriculumId = curriculumId;
	}

}
