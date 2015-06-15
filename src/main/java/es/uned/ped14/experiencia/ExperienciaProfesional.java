package es.uned.ped14.experiencia;

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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Proxy;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import es.uned.ped14.curriculum.Curriculum;

/**
 * Clase ExperienciaProfesional, POJO que modela la entidad experiencia así como
 * sus propiedades y relaciones, mapeándola con la base de datos.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "experiencia_profesional")
@Proxy(lazy = false)
public class ExperienciaProfesional implements java.io.Serializable {
	
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

	
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	@NotBlank(message = ExperienciaProfesional.NOT_BLANK_MESSAGE)
	private String cargo;

	@Column
	@NotBlank(message = ExperienciaProfesional.NOT_BLANK_MESSAGE)
	private String empresa;

	@Column
	private String descripcion;

	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull(message = ExperienciaProfesional.NOT_BLANK_MESSAGE)
	private Date fechaInicio;

	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
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

	/** Curriculum asociado */
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Curriculum.class, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "curriculum_id")
	private Curriculum curriculum;

	/**
	 * Instancia una nueva experiencia profesional.
	 */
	public ExperienciaProfesional() {

	}

	/**
	 * Instancia una nueva experiencia profesional.
	 *
	 * @param cargo
	 *            , cadena de texto con el cargo ocupado en la empresa
	 * @param empresa
	 *            , cadena de texto con la empresa en la que trabajó o trabaja
	 * @param descripcion
	 *            , cadena de texto con la descripción de la experiencia
	 *            profesional.
	 * @param fechaInicio
	 *            , campo de tipo Date con la fecha de inicio en dicha empresa.
	 * @param fechaFin
	 *            , campo de tipo Date con la fecha de finalización en dicha
	 *            empresa.
	 */
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
	 * Fijar el nuevo currículum asociado. Este método mantiene la consistencia
	 * entre relaciones: * la experiencia profesional se elimina del currículo
	 * anterior * la experiencia profesional se añade al nuevo currículo
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
			viejoCurriculum.removeExperiencia(this);
		// fijarme a mí mismo como nuevo currículum
		if (curriculum != null)
			curriculum.addExperiencia(this);
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

}
