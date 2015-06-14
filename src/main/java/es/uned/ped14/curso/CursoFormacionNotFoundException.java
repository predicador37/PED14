package es.uned.ped14.curso;

public class CursoFormacionNotFoundException extends Exception {

	/**
	 * Class CursoFormacionNotFoundException, con la excepción en caso de no
	 * encontrar un curso cuando se espera.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instancia una nueva excepción.
	 *
	 * @param string
	 *            , cadena de texto con el mensaje de la excepción.
	 */
	public CursoFormacionNotFoundException(String string) {
		// TODO Auto-generated constructor stub
		System.out.println(string);
	}

}
