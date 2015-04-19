package es.uned.ped14.curriculum;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.uned.ped14.account.Account;
import es.uned.ped14.experiencia.*;

import javax.persistence.*;

import org.hibernate.annotations.Formula;

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
	
	//@Formula("(SELECT DATE_FORMAT(max(c.experiencias.fecha_fin), '%Y') - DATE_FORMAT(max(c.experiencias.fecha_inicio), '%Y')-(DATE_FORMAT(max(c.experiencias.fecha_fin), '00-%m-%d') lt; DATE_FORMAT(max(c.experiencias.fecha_inicio), '00-%m-%d')) from curriculum c where c.id = id)")
	//@Formula("(SELECT COUNT(c.experiencias) FROM curriculum c, experiencia_profesional e where c.id = e.curriculum_id)")
	@Transient
	private Integer experiencia;
	@OneToMany(mappedBy="curriculum", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ExperienciaProfesional> experiencias;

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


	public List<ExperienciaProfesional> getExperiencias() {
		return experiencias;
	}


	public void setExperiencias(List<ExperienciaProfesional> experiencias) {
		this.experiencias = experiencias;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public Integer getExperiencia() {
		
		Integer mesesExperiencia = 0;
		for (ExperienciaProfesional e: experiencias){	
			mesesExperiencia = mesesExperiencia + ((e.getFecha_fin().getYear()*12 + e.getFecha_fin().getMonth()) - (e.getFecha_inicio().getYear()*12 + e.getFecha_inicio().getMonth()) + 1);
		}
		return mesesExperiencia;
	}
	
	
	
}
