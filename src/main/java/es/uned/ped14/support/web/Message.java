package es.uned.ped14.support.web;

/**
 * Mensaje a mostrar en el contexto web; dependiendo del tipo, se aplican
 * distintos estilos.
 */
public class Message implements java.io.Serializable {
	/**
	 * Name of the flash attribute.
	 */
	public static final String MESSAGE_ATTRIBUTE = "message";

	/**
	 * El tipo de mensaje a mostrar. Se utiliza para mostrarlo en distintos
	 * estilos.
	 */
	public static enum Type {
		DANGER, WARNING, INFO, SUCCESS;
	}

	private final String message;
	private final Type type;
	private final Object[] args;

	public Message(String message, Type type) {
		this.message = message;
		this.type = type;
		this.args = null;
	}

	public Message(String message, Type type, Object... args) {
		this.message = message;
		this.type = type;
		this.args = args;
	}

	public String getMessage() {
		return message;
	}

	public Type getType() {
		return type;
	}

	public Object[] getArgs() {
		return args;
	}
}
