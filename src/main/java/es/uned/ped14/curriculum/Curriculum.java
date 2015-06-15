package es.uned.ped14.curriculum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;

import es.uned.ped14.account.Account;
import es.uned.ped14.conocimiento.Conocimiento;
import es.uned.ped14.curso.CursoFormacion;
import es.uned.ped14.experiencia.ExperienciaProfesional;
import es.uned.ped14.titulacion.Titulacion;

/**
 * Clase Curriculum, POJO que modela la entidad currículum así como sus
 * propiedades y relaciones, mapeándola con la base de datos.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "curriculum")
@Proxy(lazy = false)
@NamedQueries({
		@NamedQuery(name = Curriculum.FIND_ONE, query = "select a from Curriculum a where a.id = :id"),
		@NamedQuery(name = Curriculum.FIND_BY_USER_EMAIL, query = "select a from Curriculum a where a.user.email = :email") })
public class Curriculum implements java.io.Serializable {

	/**
	 * Constante FIND_ONE, con el nombre de la consulta para encontrar un
	 * currículum por id.
	 */
	public static final String FIND_ONE = "Curriculum.findOne";

	/**
	 * Constante FIND_BY_USER_EMAIL, con el nombre de la consulta para encontrar
	 * un currículum por email de su usuario asociado.
	 */
	public static final String FIND_BY_USER_EMAIL = "Curriculum.findByUserEmail";

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String nombre;

	@Column
	private String apellidos;

	@Column
	private String pais;

	@Column
	private String ciudad;

	@Column
	private String urlImagen;

	@Column
	private String urlArchivo;

	@Column
	private Integer experiencia;

	@OneToOne
	private Account user;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL)
	private Collection<ExperienciaProfesional> experiencias = new ArrayList<ExperienciaProfesional>();

	public Account getUser() {
		return user;
	}

	public void setUser(Account user) {
		this.user = user;
	}

	/** Colección de titulaciones asociadas. */
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL)
	private Collection<Titulacion> titulaciones = new ArrayList<Titulacion>();

	/** Colección de conocimientos asociados. */
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL)
	private Collection<Conocimiento> conocimientos = new ArrayList<Conocimiento>();

	/** Colección de cursos asociados. */
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL)
	private Collection<CursoFormacion> cursos = new ArrayList<CursoFormacion>();

	/**
	 * Instancia un nuevo curriculum.
	 */
	public Curriculum() {

	}

	/**
	 * Instancia un nuevo curriculum.
	 *
	 * @param nombre
	 *            , cadena de texto con el nombre
	 * @param apellidosm
	 *            , cadena de texto con los apellidos
	 * @param pais
	 *            , cadena de texto con el país de residencia
	 * @param ciudad
	 *            , cadena de texto con la ciudad de residencia
	 * @param urlImagen
	 *            url imagen
	 * @param urlArchivo
	 *            url archivo
	 */
	public Curriculum(String nombre, String apellidos, String pais,
			String ciudad, String urlImagen, String urlArchivo) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.pais = pais;
		this.ciudad = ciudad;
		this.urlImagen = urlImagen;
		this.urlArchivo = urlArchivo;
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getUrlImagen() {
		return urlImagen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}

	public String getUrlArchivo() {
		return urlArchivo;
	}

	public void setUrlArchivo(String urlArchivo) {
		this.urlArchivo = urlArchivo;
	}

	/**
	 * Devuelve una colección de experiencias. La colección devuelta es una
	 * copia defensiva (nadie puede cambiarla desde el exterior)
	 *
	 * @return una coleccion de experiencias asociadas al currículum
	 */
	public Collection<ExperienciaProfesional> getExperiencias() {
		return new ArrayList<ExperienciaProfesional>(experiencias);
	}

	/**
	 * Añade una nueva experiencia al currículum. Este método mantiene la
	 * consistencia entre las relaciones. Este currículum se asocia a la
	 * experiencia en concreto
	 *
	 * @param experiencia
	 *            objeto de clase ExperienciaProfesional.
	 */
	public void addExperiencia(ExperienciaProfesional experiencia) {
		// prevenir bucle infinito
		if (experiencias.contains(experiencia))
			return;
		// añadir nueva experiencia
		experiencias.add(experiencia);
		// asociar este currículo con la experiencia
		experiencia.setCurriculum(this);
	}

	/**
	 * Elimina una experiencia de un currículum. Este método mantiene la
	 * consistencia entre las relaciones. La experiencia no estára asociada al
	 * currículo a partir de ahora.
	 *
	 * @param experiencia
	 *            , objeto de clase ExperienciaProfesional
	 */
	public void removeExperiencia(ExperienciaProfesional experiencia) {
		// prevent endless loop
		if (!experiencias.contains(experiencia))
			return;
		// remove the account
		experiencias.remove(experiencia);
		// remove myself from the twitter account
		experiencia.setCurriculum(null);
	}

	/**
	 * Devuelve una colección de titulaciones. La colección devuelta es una
	 * copia defensiva (nadie puede cambiarla desde el exterior)
	 *
	 * @param titulacion
	 *            , objeto de clase Titulacion.
	 * @return una coleccion de titulaciones asociadas al currículum.
	 */
	public Collection<Titulacion> getTitulaciones() {
		return new ArrayList<Titulacion>(titulaciones);
	}

	/**
	 * Añade una nueva titulación al currículum. Este método mantiene la
	 * consistencia entre las relaciones. Este currículum se asocia a la
	 * titulación en concreto
	 * 
	 * @param titulacion
	 *            , objeto de clase Titulación.
	 */
	public void addTitulacion(Titulacion titulacion) {

		// prevenir bucle infinito
		if (titulaciones.contains(titulacion))
			return;
		// añadir nueva experiencia
		titulaciones.add(titulacion);
		// asociar este currículo con la experiencia
		titulacion.setCurriculum(this);
	}

	/**
	 * Fija las titulaciones.
	 *
	 * @param titulaciones
	 *            , colección con titulaciones.
	 */
	public void setTitulaciones(Collection<Titulacion> titulaciones) {
		this.titulaciones = titulaciones;
	}

	/**
	 * Elimina una titulación de un currículum. Este método mantiene la
	 * consistencia entre las relaciones. La titulación no estára asociada al
	 * currículo a partir de ahora.
	 *
	 * @param titulacion
	 *            , objeto de clase Titulación.
	 */
	public void removeTitulacion(Titulacion titulacion) {
		// prevent endless loop

		// prevent endless loop
		if (!titulaciones.contains(titulacion))
			return;
		// remove the account
		titulaciones.remove(titulacion);
		// remove myself from the twitter account
		titulacion.setCurriculum(null);
	}

	/**
	 * Devuelve una colección de conocimientos. La colección devuelta es una
	 * copia defensiva (nadie puede cambiarla desde el exterior)
	 *
	 * @return una coleccion de conocimientos asociadas al currículum
	 */
	public Collection<Conocimiento> getConocimientos() {
		return new ArrayList<Conocimiento>(conocimientos);
	}

	/**
	 * Añade un nuevo conocimiento al currículum. Este método mantiene la
	 * consistencia entre las relaciones. Este currículum se asocia al
	 * conocimiento en concreto
	 *
	 * @param conocimiento
	 *            , objeto de clase Conocimiento.
	 */
	public void addConocimiento(Conocimiento conocimiento) {
		// prevenir bucle infinito
		if (conocimientos.contains(conocimiento))
			return;
		// añadir nueva experiencia
		conocimientos.add(conocimiento);
		// asociar este currículo con la experiencia
		conocimiento.setCurriculum(this);
	}

	/**
	 * Elimina un conocimiento de un currículum. Este método mantiene la
	 * consistencia entre las relaciones. El conocimiento no estára asociado al
	 * currículo a partir de ahora.
	 *
	 * @param conocimiento
	 *            , objeto de clase Conocimiento.
	 */
	public void removeConocimiento(Conocimiento conocimiento) {
		// prevent endless loop
		if (!conocimientos.contains(conocimiento))
			return;
		// remove the account
		conocimientos.remove(conocimiento);
		// remove myself from the twitter account
		conocimiento.setCurriculum(null);
	}

	/**
	 * Devuelve una colección de cursos. La colección devuelta es una copia
	 * defensiva (nadie puede cambiarla desde el exterior)
	 *
	 * @return una coleccion de cursos asociados al currículum
	 */
	public Collection<CursoFormacion> getCursos() {
		return new ArrayList<CursoFormacion>(cursos);
	}

	/**
	 * Añade un nuevo curso al currículum. Este método mantiene la consistencia
	 * entre las relaciones. Este currículum se asocia al curso en concreto
	 *
	 * @param curso
	 *            , objeto de clase CursoFormacion.
	 */
	public void addCurso(CursoFormacion curso) {
		// prevenir bucle infinito
		if (cursos.contains(curso))
			return;
		// añadir nueva experiencia
		cursos.add(curso);
		// asociar este currículo con la experiencia
		curso.setCurriculum(this);
	}

	/**
	 * Elimina un curso de un currículum. Este método mantiene la consistencia
	 * entre las relaciones. El curso no estára asociado al currículo a partir
	 * de ahora.
	 *
	 * @param curso
	 *            , objeto de clase CursoFormacion.
	 */
	public void removeCurso(CursoFormacion curso) {
		// prevent endless loop
		if (!cursos.contains(curso))
			return;
		// remove the account
		cursos.remove(curso);
		// remove myself from the twitter account
		curso.setCurriculum(null);
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Calcula la experiencia total de un currículum basándose en las
	 * experiencias individuales añadidas. Devuelve la experiencia en meses.
	 * 
	 * @returns experiencia, entero con el número de meses.
	 */
	@PrePersist
	@PreUpdate
	public void calculateExperiencia() {

		Integer mesesExperiencia = 0;
		for (ExperienciaProfesional e : experiencias) {
			if (e.getFechaFin() != null) {
				mesesExperiencia = mesesExperiencia
						+ ((e.getFechaFin().getYear() * 12 + e.getFechaFin()
								.getMonth())
								- (e.getFechaInicio().getYear() * 12 + e
										.getFechaInicio().getMonth()) + 1);
			} else { // esta trabajando actualmente en la empresa
				Date today = new Date();
				mesesExperiencia = mesesExperiencia
						+ ((today.getYear() * 12 + today.getMonth())
								- (e.getFechaInicio().getYear() * 12 + e
										.getFechaInicio().getMonth()) + 1);
			}
		}
		experiencia = mesesExperiencia;
	}

	/**
	 * Devuelve la experiencia en años.
	 *
	 * @return experiencia, con la experiencia total calculada en años.
	 */
	public Integer getExperiencia() {
		return experiencia / 12;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.nombre + this.apellidos;
	}

}
