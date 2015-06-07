package es.uned.ped14.titulacion;

import javax.persistence.*;

import es.uned.ped14.curriculum.Curriculum;

@SuppressWarnings("serial")
@Entity
@Table(name = "titulacion")

public class Titulacion implements java.io.Serializable {


	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String descripcion;
	
	@Column(columnDefinition = "int default 0")
	private Integer numeroLikes;
	
	@ManyToOne(fetch=FetchType.LAZY, targetEntity = Curriculum.class, cascade=CascadeType.PERSIST)
	@JoinColumn(name="curriculum_id")
	private Curriculum curriculum;
	

    protected Titulacion() {

	}
	
	public Titulacion(String descripcion) {
		super();
		this.descripcion = descripcion;
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
	
	@Override
	public String toString() {
		return this.descripcion;
	}

	/**
	* Fijar el nuevo currículum asociado. Este método mantiene
	* la consistencia entre relaciones:
	* * la titulación se elimina del currículo anterior
	* * la titulación se añade al nuevo currículo
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
		viejoCurriculum.removeTitulacion(this);
		//fijarme a mí mismo como nuevo currículum
		if (curriculum!=null)
		curriculum.addTitulacion(this);
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

	private boolean sameAsFormer(Curriculum nuevoCurriculum) {
		 return curriculum==null? nuevoCurriculum == null : curriculum.equals(nuevoCurriculum);
		 }
	
}
