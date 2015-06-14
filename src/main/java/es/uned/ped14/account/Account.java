package es.uned.ped14.account;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.uned.ped14.admin.AdminController;
import es.uned.ped14.curriculum.Curriculum;
import es.uned.ped14.experiencia.ExperienciaProfesional;
import es.uned.ped14.titulacion.Titulacion;

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

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL,  orphanRemoval=true)
	private Collection<Role> roles = new ArrayList<Role>();
	
	@OneToOne(mappedBy="user")
	private Curriculum curriculum;
	
    protected Account() {

	}
	
	public Account(String email, String password) {
		this.email = email;
		this.password = password;
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

	/**
	 * Añade una nueva titulación al currículum. Este método mantiene la
	 * consistencia entre las relaciones. Este currículum se asocia a la
	 * titulación en concreto
	 * @param Role role
	 */
	public void addRole(Role role) {
		
		// prevenir bucle infinito
				if (roles.contains(role))
					return;
				// añadir nueva experiencia
				roles.add(role);
				// asociar este currículo con la experiencia
				role.setUser(this);
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	/**
	 * Elimina una titulación de un currículum. Este método mantiene la
	 * consistencia entre las relaciones. La titulación no estára asociada al
	 * currículo a partir de ahora.
	 * @param Role role
	 */
	public void removeRole(Role role) {
		// prevent endless loop
		for (Role r : roles){
		}
		// prevent endless loop
				if (!roles.contains(role))
					return;
				// remove the account
				roles.remove(role);
				// remove myself from the twitter account
				role.setUser(null);
	}
}
