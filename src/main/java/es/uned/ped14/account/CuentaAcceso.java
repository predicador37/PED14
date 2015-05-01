package es.uned.ped14.account;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "cuenta_acceso")
public class CuentaAcceso implements java.io.Serializable {

	@Id
	@Column
	private String login;
	@Column
	private String password;
	@Column
	private String email;
	@Column
	private String provincia;


	protected CuentaAcceso() {

	}

	public CuentaAcceso(String login, String password, String email,
			String provincia) {
		super();
		this.login = login;
		this.password = password ;
		this.email = email ;
		this.provincia = provincia ;
	}


	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
}
