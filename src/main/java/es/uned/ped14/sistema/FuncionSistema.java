package es.uned.ped14.sistema;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "funcion_sistema")

public class FuncionSistema implements java.io.Serializable {


	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String descripcion;
	@Column
	private Integer estado;	

    protected FuncionSistema() {

	}	

	public FuncionSistema(String descripcion, Integer estado) {
		super();
		this.descripcion = descripcion;
		this.estado = estado;		
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
	
	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}
}
