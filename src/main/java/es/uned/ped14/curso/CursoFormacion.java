package es.uned.ped14.curso;

import java.sql.Date;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "curso_formacion")

public class CursoFormacion implements java.io.Serializable {


	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String descripcion;
	@Column
	private Integer numero_horas;
	@Column
	private Date fecha_finalizacion;
	

    protected CursoFormacion() {

	}
	
	

	public CursoFormacion(String descripcion, Integer numero_horas,
			Date fecha_finalizacion) {
		super();
		this.descripcion = descripcion;
		this.numero_horas = numero_horas;
		this.fecha_finalizacion = fecha_finalizacion;
	}



	public Long getId() {
		return id;
	}

    public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Integer getNumero_horas() {
		return numero_horas;
	}

	public void setNumero_horas(Integer numero_horas) {
		this.numero_horas = numero_horas;
	}

	public Date getFecha_finalizacion() {
		return fecha_finalizacion;
	}

	public void setFecha_finalizacion(Date fecha_finalizacion) {
		this.fecha_finalizacion = fecha_finalizacion;
	}
	
}
