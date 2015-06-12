package es.uned.ped14.conocimiento;

public class ConocimientoForm {
	
	private String descripcion;
	private NivelConocimiento nivelConocimiento;
	private Long curriculumId;
	 
	public ConocimientoForm() {
		
	}
	
	public Conocimiento createConocimiento() {
        return new Conocimiento(getDescripcion(), getNivelConocimiento());
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public NivelConocimiento getNivelConocimiento() {
		return nivelConocimiento;
	}

	public void setNivelConocimiento(NivelConocimiento nivelConocimiento) {
		this.nivelConocimiento = nivelConocimiento;
	}

	public Long getCurriculumId() {
		return curriculumId;
	}

	public void setCurriculumId(Long curriculumId) {
		this.curriculumId = curriculumId;
	}
	
	
}
