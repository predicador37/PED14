package es.uned.ped14.curso;

import java.util.Date;

import javax.persistence.*;

import es.uned.ped14.curriculum.Curriculum;

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
	private Integer numeroHoras;
	@Column
	private Date fechaFinalizacion;
	@Column(columnDefinition = "int default 0")
	private Integer numeroLikes;

	@ManyToOne(fetch=FetchType.LAZY, targetEntity = Curriculum.class, cascade=CascadeType.PERSIST)
	@JoinColumn(name="curriculum_id")
	private Curriculum curriculum;
	
    protected CursoFormacion() {

	}
	
	

	public CursoFormacion(String descripcion, Integer numeroHoras,
			Date fechaFinalizacion) {
		super();
		this.descripcion = descripcion;
		this.numeroHoras = numeroHoras;
		this.fechaFinalizacion = fechaFinalizacion;
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
	
	/**
	* Fijar el nuevo currículum asociado. Este método mantiene
	* la consistencia entre relaciones:
	* * el curso se elimina del currículo anterior
	* * el curso se añade al nuevo currículo
	*
	* @param Curriculum curriculum
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
		viejoCurriculum.removeCurso(this);
		//fijarme a mí mismo como nuevo currículum
		if (curriculum!=null)
		curriculum.addCurso(this);
	}
	
	 private boolean sameAsFormer(Curriculum nuevoCurriculum) {
		 return curriculum==null? nuevoCurriculum == null : curriculum.equals(nuevoCurriculum);
		 }
	 
	 public Integer getNumeroLikes() {
			return numeroLikes;
		}



		public void setNumeroLikes(Integer numeroLikes) {
			this.numeroLikes = numeroLikes;
		}



		public Curriculum getCurriculum() {
			return curriculum;
		}



		public void setId(Long id) {
			this.id = id;
		}
	
}
