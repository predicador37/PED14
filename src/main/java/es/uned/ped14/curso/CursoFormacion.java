package es.uned.ped14.curso;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Proxy;
import org.springframework.format.annotation.DateTimeFormat;

import es.uned.ped14.curriculum.Curriculum;

/**
 * Clase CursoFormación, POJO que modela la entidad curso formación así como sus
 * propiedades y relaciones, mapeándola con la base de datos.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "curso_formacion")
@Proxy(lazy = false)
public class CursoFormacion implements java.io.Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String descripcion;
	@Column
	private Integer numeroHoras;
	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date fechaFinalizacion;
	@Column(columnDefinition = "int default 0")
	private Integer likes;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Curriculum.class, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "curriculum_id")
	private Curriculum curriculum;

	/**
	 * Instancia un nuevo curso de formación.
	 */
	public CursoFormacion() {

	}

	/**
	 * Instancia un nuevocurso de formación.
	 *
	 * @param descripcion
	 *            , cadena de texto con la descripción del curso.
	 * @param numeroHoras
	 *            , entero con el número de horas del curso.
	 * @param fechaFinalizacion
	 *            , tipo java.util.Date con la fecha de finalización del curso.
	 */
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
	 * Fijar el nuevo currículum asociado. Este método mantiene la consistencia
	 * entre relaciones: * el curso se elimina del currículo anterior * el curso
	 * se añade al nuevo currículo
	 *
	 * @param Curriculum
	 *            curriculum
	 */

	public void setCurriculum(Curriculum curriculum) {
		// prevenir bucle sin fin
		if (sameAsFormer(curriculum))
			return;
		// fijar nuevo currículum
		Curriculum viejoCurriculum = this.curriculum;
		this.curriculum = curriculum;
		// eliminar del currículum viejo
		if (viejoCurriculum != null)
			viejoCurriculum.removeCurso(this);
		// fijarme a mí mismo como nuevo currículum
		if (curriculum != null)
			curriculum.addCurso(this);
	}

	/**
	 * Same as former. Método que comprueba si el currículum a añadir es el
	 * mismo que ya estaba.
	 * 
	 * @param nuevoCurriculum
	 *            , currículum a comprobar.
	 * @return true, si tiene éxito.
	 */
	private boolean sameAsFormer(Curriculum nuevoCurriculum) {
		return curriculum == null ? nuevoCurriculum == null : curriculum
				.equals(nuevoCurriculum);
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

	public void setId(Long id) {
		this.id = id;
	}

}
