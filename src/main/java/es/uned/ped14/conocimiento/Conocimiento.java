package es.uned.ped14.conocimiento;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "conocimiento")

public class Conocimiento implements java.io.Serializable {


	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String descripcion;

	@ManyToOne(fetch=FetchType.LAZY, targetEntity = NivelConocimiento.class, cascade=CascadeType.PERSIST)
	@JoinColumn(name="id_nivel")
	private NivelConocimiento nivelconocimiento;
	
    protected Conocimiento() {

	}

	public Conocimiento(String descripcion, Integer nivel) {
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

	public NivelConocimiento getNivelconocimiento() {
		return nivelconocimiento;
	}

	public void setNivelconocimiento(NivelConocimiento nivelconocimiento) {
		this.nivelconocimiento = nivelconocimiento;
	}
}
