package es.uned.ped14.titulacion;

import java.io.Serializable;

public class AsociacionTitulacionId implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long curriculumId;
	private Long titulacionId;
	
	 public int hashCode() {
		    return (int)(curriculumId + titulacionId);
		  }
		 
		  public boolean equals(Object object) {
		    if (object instanceof AsociacionTitulacionId) {
		      AsociacionTitulacionId otherId = (AsociacionTitulacionId) object;
		      return (otherId.curriculumId == this.curriculumId) && (otherId.titulacionId == this.titulacionId);
		    }
		    return false;
		  }

		public Long getCurriculumId() {
			return curriculumId;
		}

		public void setCurriculumId(Long curriculumId) {
			this.curriculumId = curriculumId;
		}

		public Long getTitulacionId() {
			return titulacionId;
		}

		public void setTitulacionId(Long titulacionId) {
			this.titulacionId = titulacionId;
		}
		  
	

}
