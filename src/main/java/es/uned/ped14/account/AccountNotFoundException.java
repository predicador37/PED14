package es.uned.ped14.account;

/**
 * Class AccountNotFoundException, con la excepción en caso de no encontrar
 * una cuenta de usuario en la base de datos cuando se espera.
 */
public class AccountNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Instancia una nueva excepción.
	 *
	 * @param string
	 *            , cadena de texto con el mensaje de la excepción.
	 */
	public AccountNotFoundException(String string) {
		System.out.println(string);
	}

}
