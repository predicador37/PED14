package es.uned.ped14.conocimiento;

// TODO: Auto-generated Javadoc
/**
 * Enum NivelConocimiento, enumeración con los niveles de conocimiento
 * disponibles.
 */
public enum NivelConocimiento {

	BAJO("nivel.bajo"),

	MEDIO("nivel.medio"),

	ALTO("nivel.alto"),

	OTRO("nivel.otro");

	private String valor;

	/**
	 * Instancia un nuevo nivel de conocimiento.
	 *
	 * @param valor
	 *            , valor del nivel.
	 */
	private NivelConocimiento(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

}