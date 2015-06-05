package es.uned.ped14.account;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@Table(name = "account")
@NamedQuery(name = Account.FIND_BY_EMAIL, query = "select a from Account a where a.email = :email")
public class Account implements java.io.Serializable {

	public static final String FIND_BY_EMAIL = "Account.findByEmail";

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true) 
	private String usuario;
	
	@Column(unique = true)
	private String email;
	
	@Column
	private String provincia;
	
	@JsonIgnore
	private String password;	

	private String role = "ROLE_USER";

    protected Account() {

	}
	
	public Account(String email, String password, String usuario, String role) {
		this.email = email;
		this.password = password;
		this.usuario = usuario;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
    public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
