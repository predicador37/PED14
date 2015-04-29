package es.uned.ped14.account;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "descripcion")
public class Perfil implements java.io.Serializable {

	@Id
	@GeneratedValue
	private Long id;
	@Column
	private String descripcion;
	
	
	protected Perfil() {

	}

	public Perfil(String descripcion) {
		super();
		this.descripcion = descripcion;
	}


	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
