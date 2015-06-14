package es.uned.ped14.curriculum;

/**
 * Class CurriculumNotFoundException, con la excepción en caso de no encontrar
 * un currículo cuando se espera.
 */
public class CurriculumNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Instancia una nueva excepción.
	 *
	 * @param string
	 *            , cadena de texto con el mensaje de la excepción.
	 */
	public CurriculumNotFoundException(String string) {
		System.out.println(string);
	}

}
