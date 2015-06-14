package es.uned.ped14.experiencia;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Clase ExperienciaProfesionalForm, que representa el objeto DTO para la
 * creación de experiencias profesionales.
 */
public class ExperienciaProfesionalForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

	@NotBlank(message = ExperienciaProfesionalForm.NOT_BLANK_MESSAGE)
	private String descripcion;
	@NotBlank(message = ExperienciaProfesionalForm.NOT_BLANK_MESSAGE)
	private String cargo;
	@NotBlank(message = ExperienciaProfesionalForm.NOT_BLANK_MESSAGE)
	private String empresa;
	@NotNull(message = ExperienciaProfesionalForm.NOT_BLANK_MESSAGE)
	private Date fechaInicio;
	@NotNull(message = ExperienciaProfesionalForm.NOT_BLANK_MESSAGE)
	private Date fechaFin;
	private Long curriculumId;

	public ExperienciaProfesionalForm() {

	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public ExperienciaProfesional createExperienciaProfesional() {
		return new ExperienciaProfesional(getCargo(), getEmpresa(),
				getDescripcion(), getFechaInicio(), getFechaFin());
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Long getCurriculumId() {
		return curriculumId;
	}

	public void setCurriculumId(Long curriculumId) {
		this.curriculumId = curriculumId;
	}

}
