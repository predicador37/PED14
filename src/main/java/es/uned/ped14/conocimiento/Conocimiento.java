package es.uned.ped14.conocimiento;

import javax.persistence.*;

import org.hibernate.annotations.Proxy;

import es.uned.ped14.curriculum.Curriculum;


@SuppressWarnings("serial")
@Entity
@Table(name = "conocimiento")
@Proxy(lazy=false)
public class Conocimiento implements java.io.Serializable {


	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String descripcion;

	@Enumerated(EnumType.STRING)
	@Column(name ="nivel_conocimiento")
	private NivelConocimiento nivelConocimiento;
	
	@Column(columnDefinition = "int default 0")
	private Integer numeroLikes;
	
	public Conocimiento() {
		
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

	@ManyToOne(fetch=FetchType.EAGER, targetEntity = Curriculum.class, cascade=CascadeType.PERSIST)
	@JoinColumn(name="curriculum_id")
	private Curriculum curriculum;

	public Conocimiento(String descripcion,NivelConocimiento nivelConocimiento) {
		super();
		this.descripcion = descripcion;	
		this.nivelConocimiento = nivelConocimiento;	
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

	public NivelConocimiento getNivelConocimiento() {
		return nivelConocimiento;
	}

	public void setNivelConocimiento(NivelConocimiento nivelConocimiento) {
		this.nivelConocimiento = nivelConocimiento;
	}
	
	/**
	* Fijar el nuevo currículum asociado. Este método mantiene
	* la consistencia entre relaciones:
	* * el conocimiento se elimina del currículo anterior
	* * el conocimiento se añade al nuevo currículo
	*
	* @param curriculum
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
		viejoCurriculum.removeConocimiento(this);
		//fijarme a mí mismo como nuevo currículum
		if (curriculum!=null)
		curriculum.addConocimiento(this);
	}
	
	 private boolean sameAsFormer(Curriculum nuevoCurriculum) {
		 return curriculum==null? nuevoCurriculum == null : curriculum.equals(nuevoCurriculum);
		 }
}
