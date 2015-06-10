package es.uned.ped14.titulacion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import es.uned.ped14.curriculum.Curriculum;

@Entity
@Table(name="curriculo_titulacion")
@IdClass(AsociacionTitulacionId.class)
public class AsociacionTitulacion {

	@Id
	private Long curriculumId;
	@Id
	private Long titulacionId;
	@Column
	Integer likes;
	
	  @ManyToOne
	  @PrimaryKeyJoinColumn(name="curriculum_id", referencedColumnName="id")
	  private Curriculum curriculum;
	  @ManyToOne
	  @PrimaryKeyJoinColumn(name="titulacion_id", referencedColumnName="id")
	  private Titulacion titulacion;
	  
	protected AsociacionTitulacion(){
	
	}
	  
	public AsociacionTitulacion(Curriculum curriculum, Titulacion titulacion){
		this.curriculum = curriculum;
		this.curriculumId = curriculum.getId();
		this.titulacion = titulacion;
		this.titulacionId = titulacion.getId();
		
	}
	  
	public Long getCurriculumId() {
		return curriculumId;
	}
	public void setCurriculumId(Long curriculumId) {
		this.curriculumId = curriculumId;
	}
	public Long getTitulacionId() {
		return titulacionId;
	}
	public void setTitulacionId(Long titulacionId) {
		this.titulacionId = titulacionId;
	}
	public Integer getLikes() {
		return likes;
	}
	public void setLikes(Integer likes) {
		this.likes = likes;
	}
	public Curriculum getCurriculum() {
		return curriculum;
	}
	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}
	public Titulacion getTitulacion() {
		return titulacion;
	}
	public void setTitulacion(Titulacion titulacion) {
		this.titulacion = titulacion;
	}
	  
	  

}
