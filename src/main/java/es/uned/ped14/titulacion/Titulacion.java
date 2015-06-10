package es.uned.ped14.titulacion;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import es.uned.ped14.curriculum.Curriculum;


@SuppressWarnings("serial")
@Entity
@Table(name = "titulacion")

public class Titulacion implements java.io.Serializable {


	@Id
	@GeneratedValue
	private Long id;

	 @Column(unique = true)
	private String descripcion;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "titulacion", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, 
		    org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
	private Collection<AsociacionTitulacion> curriculos = new ArrayList<AsociacionTitulacion>();

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
	
	@Override
	public String toString() {
		return this.descripcion;
	}
	

	public Collection<AsociacionTitulacion> getCurriculos() {
		return new ArrayList<AsociacionTitulacion>(curriculos);
	}
	
	/**
	 * Añade una nueva titulación al currículum. Este método mantiene la
	 * consistencia entre las relaciones. Este currículum se asocia a la
	 * titulación en concreto
	 * @param Titulacion titulacion
	 */
	public void addCurriculum(Curriculum curriculum, Integer likes) {
		
		 AsociacionTitulacion asociacion = new AsociacionTitulacion(curriculum, this);
		 asociacion.setLikes(likes);
		 this.curriculos.add(asociacion);
		    // Also add the association object to the curriculum 
		
	}

	/**
	 * Elimina una titulación de un currículum. Este método mantiene la
	 * consistencia entre las relaciones. La titulación no estára asociada al
	 * currículo a partir de ahora.
	 * @param Titulacion titulacion
	 */
	public void removeCurriculum(Curriculum curriculum) {
		// prevent endless loop
		AsociacionTitulacion asociacion = new AsociacionTitulacion(curriculum, this);
		if (!curriculos.contains(asociacion))
			return;
		// remove the account
		curriculos.remove(asociacion);
		
	}
	

	public void setCurriculos(Collection<AsociacionTitulacion> curriculos) {
		this.curriculos = curriculos;
	}
	
}
