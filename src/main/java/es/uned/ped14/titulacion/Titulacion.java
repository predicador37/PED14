package es.uned.ped14.titulacion;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Proxy;
import org.hibernate.validator.constraints.NotBlank;

import es.uned.ped14.curriculum.Curriculum;

/**
 * Clase Titulacion, POJO que modela la entidad titulacion así como sus
 * propiedades y relaciones, mapeándola con la base de datos.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "titulacion")
@Proxy(lazy = false)
public class Titulacion implements java.io.Serializable {
	
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	
	@Id
	@GeneratedValue
	private Long id;

	@Column
	@NotBlank(message = Titulacion.NOT_BLANK_MESSAGE)
	private String descripcion;
	
	@Column
	@NotNull(message = Titulacion.NOT_BLANK_MESSAGE)
	@Digits(integer=4, fraction=0)
	private Integer anyoFinalizacion;
	
	@Column(columnDefinition = "int default 0")
	private Integer likes;

	/** Curriculum asociado */
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Curriculum.class, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "curriculum_id")
	private Curriculum curriculum;

	/**
	 * Instancia una nueva titulación.
	 */
	public Titulacion() {

	}

	/**
	 * Instancia una nueva titulación.
	 *
	 * 
	 * @param descripcion
	 *            , cadena de texto con la descripción de la experiencia
	 *            profesional.
	 * @param anyoFinalizacion
	 *            , campo de tipo entero con el año de finalización de la
	 *            titulación.
	 */
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

	/**
	 * Fijar el nuevo currículum asociado. Este método mantiene la consistencia
	 * entre relaciones: * la titulación se elimina del currículo anterior * la
	 * titulación se añade al nuevo currículo
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
			viejoCurriculum.removeTitulacion(this);
		// fijarme a mí mismo como nuevo currículum
		if (curriculum != null)
			curriculum.addTitulacion(this);
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

	public Curriculum getCurriculum() {
		return curriculum;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}
	
	/**
	 * Método para incrementar el contador "me gusta"
	 */
	public void like() {
		this.likes++;
	}
	/**
	 * Método para inicializar el contador "me gusta" a cero
	 */
	@PrePersist
	void preInsert() {
		this.likes = 0;
	}

}
