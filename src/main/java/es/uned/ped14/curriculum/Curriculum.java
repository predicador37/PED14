package es.uned.ped14.curriculum;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import es.uned.ped14.experiencia.ExperienciaProfesional;

@SuppressWarnings("serial")
@Entity
@Table(name = "curriculum")
@NamedQuery(name = Curriculum.FIND_ONE, query = "select a from Curriculum a where a.id = :id")
public class Curriculum implements java.io.Serializable {
	public static final String FIND_ONE = "Curriculum.findOne";

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
	private String url_imagen;
	@Column
	private String url_archivo;
	@Column
	private Integer experiencia;

	@OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Collection<ExperienciaProfesional> experiencias = new ArrayList<ExperienciaProfesional>();

	protected Curriculum() {

	}

	public Curriculum(String nombre, String apellidos, String pais,
			String ciudad, String url_imagen, String url_archivo) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.pais = pais;
		this.ciudad = ciudad;
		this.url_imagen = url_imagen;
		this.url_archivo = url_archivo;
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

	public String getUrl_imagen() {
		return url_imagen;
	}

	public void setUrl_imagen(String url_imagen) {
		this.url_imagen = url_imagen;
	}

	public String getUrl_archivo() {
		return url_archivo;
	}

	public void setUrl_archivo(String url_archivo) {
		this.url_archivo = url_archivo;
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
