package es.uned.ped14.titulacion;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "curriculum_titulacion")

public class CurriculumTitulacion implements java.io.Serializable {


	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private Integer id_curriculum;
	
	@Column
	private Integer id_titulacion;

    protected CurriculumTitulacion() {

	}
	
	public CurriculumTitulacion(int id_curriculum, int id_titulacion) {
		super();
		this.id_curriculum = id_curriculum;
		this.id_titulacion = id_titulacion;
	}

	public Long getId() {
		return id;
	}
	
	public Integer getIdCurriculum() {
		return id_curriculum;
	}
    
    public void setIdCurriculum(Integer id_curriculum) {
		this.id_curriculum = id_curriculum;
	}

    public Integer getIdTitulacion() {
		return id_titulacion;
	}
    
    public void setIdTitulacion(Integer id_titulacion) {
		this.id_titulacion = id_titulacion;
	}
	
}
