package es.uned.ped14.titulacion;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
	 
	@Column
	private Integer anyoFinalizacion;
	
	
	@ManyToOne(fetch=FetchType.LAZY, targetEntity = Curriculum.class, cascade=CascadeType.PERSIST)
	@JoinColumn(name="curriculum_id")
	private Curriculum curriculum;

    protected Titulacion() {

	}
	
	public Titulacion(String descripcion, Integer anyoFinalizacion) {
		super();
		this.descripcion = descripcion;
		this.anyoFinalizacion = anyoFinalizacion;
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
	
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return this.descripcion;
	}

	public Integer getAnyoFinalizacion() {
		return anyoFinalizacion;
	}

	public void setAnyoFinalizacion(Integer anyoFinalizacion) {
		this.anyoFinalizacion = anyoFinalizacion;
	}

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
	
	 private boolean sameAsFormer(Curriculum nuevoCurriculum) {
		 return curriculum==null? nuevoCurriculum == null : curriculum.equals(nuevoCurriculum);
		 }

	public Curriculum getCurriculum() {
		return curriculum;
	}
	
}
