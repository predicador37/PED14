package es.uned.ped14.conocimiento;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import es.uned.ped14.curriculum.Curriculum;

/**
 * Clase Conocimiento, POJO que modela la entidad conocimiento así como sus
 * propiedades y relaciones, mapeándola con la base de datos.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "conocimiento")
@Proxy(lazy = false)
public class Conocimiento implements java.io.Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String descripcion;

	@Enumerated(EnumType.STRING)
	@Column(name = "nivel_conocimiento")
	private NivelConocimiento nivelConocimiento;

	@Column(columnDefinition = "int default 0")
	private Integer likes;

	/**
	 * Instancia un nuevo conocimiento.
	 */
	public Conocimiento() {

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

	/** Curriculum asociado */
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Curriculum.class, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "curriculum_id")
	private Curriculum curriculum;

	/**
	 * Instancia un nuevo conocimiento.
	 *
	 * @param descripcion
	 *            , cadena de texto con la descripción del conocimiento.
	 * @param nivelConocimiento
	 *            , valor de una enumeración con el nivel de conocimiento
	 *            asociado.
	 */
	public Conocimiento(String descripcion, NivelConocimiento nivelConocimiento) {
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
	 * Fijar el nuevo currículum asociado. Este método mantiene la consistencia
	 * entre relaciones: * el conocimiento se elimina del currículo anterior *
	 * el conocimiento se añade al nuevo currículo
	 *
	 * @param curriculum
	 *            , nuevo currículum a asociar
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
			viejoCurriculum.removeConocimiento(this);
		// fijarme a mí mismo como nuevo currículum
		if (curriculum != null)
			curriculum.addConocimiento(this);
	}

	/**
	 * Same as former. Método que comprueba si el currículum a añadir es el
	 * mismo queya estaba.
	 * 
	 * @param nuevoCurriculum
	 *            , currículum a comprobar.
	 * @return true, si tiene éxito.
	 */
	private boolean sameAsFormer(Curriculum nuevoCurriculum) {
		return curriculum == null ? nuevoCurriculum == null : curriculum
				.equals(nuevoCurriculum);
	}
}
