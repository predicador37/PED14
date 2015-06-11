package es.uned.ped14.curso;

import java.util.Date;

import org.hibernate.validator.constraints.*;

import es.uned.ped14.account.Account;

public class CursoFormacionForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

    @NotBlank(message = CursoFormacionForm.NOT_BLANK_MESSAGE)
	private String descripcion;

    private Integer numeroHoras;
    private Date fechaFinalizacion;


	public CursoFormacion createCursoFormacion() {
        return new CursoFormacion(getDescripcion(), getNumeroHoras(), getFechaFinalizacion());
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



	
}
