package es.uned.ped14.conocimiento;

/**
 * Class ConocimientoNotFoundException, con la excepción en caso de no encontrar
 * un conocimiento cuando se espera.
 */
public class ConocimientoNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Instancia una nueva excepción.
	 *
	 * @param string
	 *            , cadena de texto con el mensaje de la excepción.
	 */
	public ConocimientoNotFoundException(String string) {
		// TODO Auto-generated constructor stub
		System.out.println(string);
	}

}
