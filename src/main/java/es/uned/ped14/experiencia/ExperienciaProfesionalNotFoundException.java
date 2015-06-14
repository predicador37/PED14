package es.uned.ped14.experiencia;

/**
 * Class ExperienciaProfesionalNotFoundException, con la excepción en caso de no
 * encontrar una experiencia cuando se espera.
 */
public class ExperienciaProfesionalNotFoundException extends Exception {

	/**
	 * Instancia una nueva excepción.
	 *
	 * @param string
	 *            , cadena de texto con el mensaje de la excepción.
	 */
	private static final long serialVersionUID = 1L;

	public ExperienciaProfesionalNotFoundException(String string) {

		System.out.println(string);
	}

}
