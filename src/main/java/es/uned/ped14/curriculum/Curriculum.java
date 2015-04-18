package es.uned.ped14.curriculum;

import java.sql.Date;
import java.util.List;

import es.uned.ped14.account.Account;
import es.uned.ped14.experiencia.*;

import javax.persistence.*;

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
	@OneToMany(mappedBy="curriculum")
	private List<ExperienciaProfesional> experiencia;

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


	public List<ExperienciaProfesional> getExperiencia() {
		return experiencia;
	}


	public void setExperiencia(List<ExperienciaProfesional> experiencia) {
		this.experiencia = experiencia;
	}


	public void setId(Long id) {
		this.id = id;
	}
	
}
