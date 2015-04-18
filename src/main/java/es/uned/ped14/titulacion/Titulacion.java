package es.uned.ped14.titulacion;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "titulacion")

public class Titulacion implements java.io.Serializable {


	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String descripcion;
	

    protected Titulacion() {

	}
	
	public Titulacion(String descripcion) {
		super();
		this.descripcion = descripcion;
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

	
}
