package es.uned.ped14.account;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

import es.uned.ped14.curriculum.Curriculum;

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
	private String email;
	
	@Column
	private String login;
	
	@Column
	private String provincia;

	public Curriculum getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	private String password;

	private String role = "ROLE_USER";
	
	@OneToOne(mappedBy="user")
	private Curriculum curriculum;
	
    protected Account() {

	}
	
	public Account(String email, String password, String login, String provincia, String role) {
		this.email = email;
		this.password = password;
		this.login = login;
		this.provincia = provincia;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
