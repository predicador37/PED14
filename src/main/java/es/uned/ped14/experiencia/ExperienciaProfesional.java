package es.uned.ped14.experiencia;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Proxy;
import org.springframework.format.annotation.DateTimeFormat;

import es.uned.ped14.curriculum.Curriculum;

@SuppressWarnings("serial")
@Entity
@Table(name = "experiencia_profesional")
@Proxy(lazy=false)
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
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date fechaInicio;
	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date fechaFin;
	@Column(columnDefinition = "int default 0")
	private Integer numeroLikes;
	
	public Integer getNumeroLikes() {
		return numeroLikes;
	}

	public void setNumeroLikes(Integer numeroLikes) {
		this.numeroLikes = numeroLikes;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch=FetchType.EAGER, targetEntity = Curriculum.class, cascade=CascadeType.PERSIST)
	@JoinColumn(name="curriculum_id")
	private Curriculum curriculum;

    public ExperienciaProfesional() {

	}

	public ExperienciaProfesional(String cargo, String empresa, 
			String descripcion, Date fechaInicio, Date fechaFin) {
		super();
		this.cargo = cargo;
		this.empresa = empresa;
		this.descripcion = descripcion;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
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
