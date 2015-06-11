package es.uned.ped14.curriculum;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import es.uned.ped14.account.Account;
import es.uned.ped14.conocimiento.Conocimiento;
import es.uned.ped14.curso.CursoFormacion;
import es.uned.ped14.experiencia.ExperienciaProfesional;
import es.uned.ped14.titulacion.Titulacion;

@SuppressWarnings("serial")
@Entity
@Table(name = "curriculum")
@NamedQueries({
@NamedQuery(name = Curriculum.FIND_ONE, query = "select a from Curriculum a where a.id = :id"),
@NamedQuery(name = Curriculum.FIND_BY_USER_EMAIL, query = "select a from Curriculum a where a.user.email = :email")
}) 
public class Curriculum implements java.io.Serializable {
	public static final String FIND_ONE = "Curriculum.findOne";
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
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL)
	private Collection<Titulacion> titulaciones = new ArrayList<Titulacion>();
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL)
	private Collection<Conocimiento> conocimientos = new ArrayList<Conocimiento>();
	@OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL)
	private Collection<CursoFormacion> cursos = new ArrayList<CursoFormacion>();

	protected Curriculum() {

	}

	public Curriculum(String nombre, String apellidos, String pais,
			String ciudad, String url_imagen, String url_archivo) {
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

	public void setUrl_imagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}

	public String getUrl_archivo() {
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
	 * @param ExperienciaProfresional experiencia
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
	 * @param ExperienciaProfesional experiencia
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
	 * @return una coleccion de titulaciones asociadas al currículum
	 */
	public Collection<Titulacion> getTitulaciones() {
		return new ArrayList<Titulacion>(titulaciones);
	}

	/**
	 * Añade una nueva titulación al currículum. Este método mantiene la
	 * consistencia entre las relaciones. Este currículum se asocia a la
	 * titulación en concreto
	 * @param Titulacion titulacion
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
	 * Elimina una titulación de un currículum. Este método mantiene la
	 * consistencia entre las relaciones. La titulación no estára asociada al
	 * currículo a partir de ahora.
	 * @param Titulacion titulacion
	 */
	public void removeTitulacion(Titulacion titulacion) {
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
	 * @param Conocimiento conocimiento
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
	 * @param Conocimiento conocimiento
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
	 * Devuelve una colección de cursos. La colección devuelta es una
	 * copia defensiva (nadie puede cambiarla desde el exterior)
	 *
	 * @return una coleccion de cursos asociados al currículum
	 */
	public Collection<CursoFormacion> getCursos() {
		return new ArrayList<CursoFormacion>(cursos);
	}

	/**
	 * Añade un nuevo curso al currículum. Este método mantiene la
	 * consistencia entre las relaciones. Este currículum se asocia al
	 * curso en concreto
	 * @param CursoFormacion curso
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
	 * Elimina un curso de un currículum. Este método mantiene la
	 * consistencia entre las relaciones. El curso no estára asociado al
	 * currículo a partir de ahora.
	 * @param CursoFormacion curso
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
	 */
	@PrePersist
	public void calculateExperiencia() {

		Integer mesesExperiencia = 0;
		for (ExperienciaProfesional e : experiencias) {
			mesesExperiencia = mesesExperiencia
					+ ((e.getFecha_fin().getYear() * 12 + e.getFecha_fin()
							.getMonth())
							- (e.getFecha_inicio().getYear() * 12 + e
									.getFecha_inicio().getMonth()) + 1);
		}
		experiencia = mesesExperiencia;
	}

	public Integer getExperiencia() {
		return experiencia;
	}

}
