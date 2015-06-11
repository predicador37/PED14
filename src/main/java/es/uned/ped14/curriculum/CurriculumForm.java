package es.uned.ped14.curriculum;

import java.sql.Date;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Controller;
import es.uned.ped14.conocimiento.Conocimiento;
import es.uned.ped14.conocimiento.NivelConocimiento;
import es.uned.ped14.curso.CursoFormacion;
import es.uned.ped14.experiencia.ExperienciaProfesional;
import es.uned.ped14.titulacion.Titulacion;

@Controller
public class CurriculumForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

    @NotBlank(message = CurriculumForm.NOT_BLANK_MESSAGE)
	// Almacenan en la tabla Curriculum
    private String nombre;
    private String apellidos;
    private String pais;
    private String ciudad;
    private String url_imagen;
    private String url_archivo;
    // Almacena en la tabla Titulacion
    private String titulacion;
    // Almacena en la tabla Conocimientos
    private String conocimientos;
    private NivelConocimiento nivelConocimiento; // Elegir una de las tres opciones
    // Almacena en la tabla curso_formacion
    private String desc_formacion;
    private int horas_formacion;
    private Date fechaFin_formacion;
    // Almacena en la tabla experiencia_profesional
    private Date fechaInicio;
    private Date fechaFin;
    private String cargo;
    private String empresa;
    private String descripcion;
    
    
	public Curriculum createCurriculum() {		
       return new Curriculum(getNombre(), getApellidos(), getPais(), getCiudad(),
    		   getUrl_imagen(), getUrl_archivo());		
	}
	
	public Titulacion createTitulacion(){
		return new Titulacion(getTitulacion());
	}
	
	public Conocimiento createConocimiento(){
		return new Conocimiento(getConocimientos(), getNivelConocimiento());
	}
	
	public CursoFormacion createCursoFormacion(){
		return new CursoFormacion(getDesc_formacion(), getHoras_formacion(), getFechaFin_formacion());
	}

	public ExperienciaProfesional createExperiencia(){
		return new ExperienciaProfesional(getCargo(), getEmpresa(), getDescripcion(),
				getFechaInicio(), getFechaFin());
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

	public String getTitulacion() {
		return titulacion;
	}

	public void setTitulacion(String titulacion) {
		this.titulacion = titulacion;
	}

	public String getConocimientos() {
		return conocimientos;
	}

	public void setConocimientos(String conocimientos) {
		this.conocimientos = conocimientos;
	}

	public NivelConocimiento getNivelConocimiento() {
		return nivelConocimiento;
	}

	public void setNivelConocimiento(NivelConocimiento nivelConocimiento) {
		this.nivelConocimiento = nivelConocimiento;
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

	public String getDesc_formacion() {
		return desc_formacion;
	}

	public void setDesc_formacion(String desc_formacion) {
		this.desc_formacion = desc_formacion;
	}

	public int getHoras_formacion() {
		return horas_formacion;
	}

	public void setHoras_formacion(int horas_formacion) {
		this.horas_formacion = horas_formacion;
	}

	public Date getFechaFin_formacion() {
		return fechaFin_formacion;
	}

	public void setFechaFin_formacion(Date fechaFin_formacion) {
		this.fechaFin_formacion = fechaFin_formacion;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}

