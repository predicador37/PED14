package es.uned.ped14.conocimiento;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "nivel_conocimiento")

public class NivelConocimiento implements java.io.Serializable {


	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String descripcion;

	
    protected NivelConocimiento() {

	}

	public NivelConocimiento(String descripcion) {
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
