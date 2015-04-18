package es.uned.ped14.experiencia;

import java.sql.Date;

import javax.persistence.*;

import es.uned.ped14.curriculum.Curriculum;

@SuppressWarnings("serial")
@Entity
@Table(name = "experiencia_profesional")

public class ExperienciaProfesional implements java.io.Serializable {


	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String descripcion;
	@Column
	private String cargo;
	@Column
	private String empresa;
	@Column
	private Date fecha_inicio;
	@Column
	private Date fecha_fin;
	@ManyToOne(fetch=FetchType.LAZY, targetEntity = Curriculum.class)
	@JoinColumn(name="curriculum_id")
	private Curriculum curriculum;

    protected ExperienciaProfesional() {

	}

	public ExperienciaProfesional(String descripcion, String cargo,
			String empresa, Date fecha_inicio, Date fecha_fin) {
		super();
		this.descripcion = descripcion;
		this.cargo = cargo;
		this.empresa = empresa;
		this.fecha_inicio = fecha_inicio;
		this.fecha_fin = fecha_fin;
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

	public Date getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public Date getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(Date fecha_fin) {
		this.fecha_fin = fecha_fin;
	}
	
}
