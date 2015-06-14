package es.uned.ped14.curriculum;

/**
 * Clase CurriculumEmptyException, excepción en caso de que el currículum esté
 * vacío.
 * 
 */
public class CurriculumEmptyException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Instancia una nueva excepción.
	 *
	 * @param string
	 *            , cadena de texto con el mensaje de la excepción.
	 */
	public CurriculumEmptyException(String string) {
		System.out.println(string);
	}

}
