package es.uned.ped14.curso;

import java.sql.Date;

import javax.persistence.*;

import es.uned.ped14.curriculum.Curriculum;

@SuppressWarnings("serial")
@Entity
@Table(name = "curso_formacion")

public class CursoFormacion implements java.io.Serializable {


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

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String descripcion;
	@Column
	private Integer numero_horas;
	@Column
	private Date fecha_finalizacion;
	@Column(columnDefinition = "int default 0")
	private Integer numeroLikes;

	@ManyToOne(fetch=FetchType.LAZY, targetEntity = Curriculum.class, cascade=CascadeType.PERSIST)
	@JoinColumn(name="curriculum_id")
	private Curriculum curriculum;
	
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
	
}
