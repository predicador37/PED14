package es.uned.ped14.signup;

import org.hibernate.validator.constraints.*;

import es.uned.ped14.account.Account;
import es.uned.ped14.account.Role;
/**
 * Clase TitulacionForm, que representa el objeto DTO para la creación de
 * usuarios en el sistema.
 */
public class SignupForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	private static final String EMAIL_MESSAGE = "{email.message}";
	private static final String USUARIO_MESSAGE = "{usuario.message}";
	private static final String PROVINCIA_MESSAGE = "{provincia.message}";

    @NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	@Email(message = SignupForm.EMAIL_MESSAGE)
	private String email;

    @NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String password;
    
    @NotBlank(message = SignupForm.PROVINCIA_MESSAGE)
	private String provincia;

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public Account createAccount() {
		Account user = new Account(getEmail(), getPassword());
		user.addRole(new Role("ROLE_CREATE"));
		user.addRole(new Role("ROLE_EDIT"));
		user.addRole(new Role("ROLE_DELETE"));
        return user;
	}

}
