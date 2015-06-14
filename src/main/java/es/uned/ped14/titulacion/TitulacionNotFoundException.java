package es.uned.ped14.titulacion;

/**
 * Class TitulacionNotFoundException, con la excepción en caso de no encontrar
 * una titulación cuando se espera.
 */
public class TitulacionNotFoundException extends Exception {

	/**
	 * Instancia una nueva excepción.
	 *
	 * @param string
	 *            , cadena de texto con el mensaje de la excepción.
	 */
	private static final long serialVersionUID = 1L;

	public TitulacionNotFoundException(String string) {
		// TODO Auto-generated constructor stub
		System.out.println(string);
	}

}
