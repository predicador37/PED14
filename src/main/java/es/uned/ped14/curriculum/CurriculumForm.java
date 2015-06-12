package es.uned.ped14.curriculum;


import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Controller;


@Controller
public class CurriculumForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

    @NotBlank(message = CurriculumForm.NOT_BLANK_MESSAGE)
	
    private String nombre;
    private String apellidos;
    private String pais;
    private String ciudad;
    private String url_imagen;
    private String url_archivo;
    
	public Curriculum createCurriculum() {		
       return new Curriculum(getNombre(), getApellidos(), getPais(), getCiudad(),
    		   getUrl_imagen(), getUrl_archivo());		
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
	
}

