package es.uned.ped14.experiencia;

import java.util.Date;

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
	private String cargo;
	@Column
	private String empresa;
	@Column
	private String descripcion;
	@Column
	private Date fecha_inicio;
	@Column
	private Date fecha_fin;
	@ManyToOne(fetch=FetchType.LAZY, targetEntity = Curriculum.class, cascade=CascadeType.PERSIST)
	@JoinColumn(name="curriculum_id")
	private Curriculum curriculum;

    protected ExperienciaProfesional() {

	}

	public ExperienciaProfesional(String cargo, String empresa, 
			String descripcion, Date date, Date date2) {
		super();
		this.cargo = cargo;
		this.empresa = empresa;
		this.descripcion = descripcion;
		this.fecha_inicio = date;
		this.fecha_fin = date2;
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

	public Curriculum getCurriculum() {
		return curriculum;
	}

	/**
	* Fijar el nuevo currículum asociado. Este método mantiene
	* la consistencia entre relaciones:
	* * la experiencia se elimina del currículo anterior
	* * la experiencia se añade al nuevo currículo
	*
	* @param owner
	*/
	
	public void setCurriculum(Curriculum curriculum) {
		//prevenir bucle sin fin
		if (sameAsFormer(curriculum))
		return ;
		//fijar nuevo currículum
		Curriculum viejoCurriculum = this.curriculum;
		this.curriculum = curriculum;
		//eliminar del currículum viejo
		if (viejoCurriculum!=null)
		viejoCurriculum.removeExperiencia(this);
		//fijarme a mí mismo como nuevo currículum
		if (curriculum!=null)
		curriculum.addExperiencia(this);
	}
	
	 private boolean sameAsFormer(Curriculum nuevoCurriculum) {
		 return curriculum==null? nuevoCurriculum == null : curriculum.equals(nuevoCurriculum);
		 }
	
}
