package es.uned.ped14.titulacion;

import org.hibernate.validator.constraints.*;

import es.uned.ped14.account.Account;

public class TitulacionForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

    @NotBlank(message = TitulacionForm.NOT_BLANK_MESSAGE)
	private String descripcion;

 
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Titulacion createTitulacion() {
        return new Titulacion(getDescripcion());
	}



	
}
