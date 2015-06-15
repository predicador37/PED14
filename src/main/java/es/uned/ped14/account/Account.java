package es.uned.ped14.account;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import es.uned.ped14.curriculum.Curriculum;
import es.uned.ped14.experiencia.ExperienciaProfesional;

/**
 * Clase Account, POJO que modela la entidad cuenta de usuario así como sus
 * propiedades y relaciones, mapeándola con la base de datos.
 */
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

	/** Roles asociados */
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<Role> roles = new ArrayList<Role>();

	@OneToOne(mappedBy = "user",
	        cascade = CascadeType.ALL, orphanRemoval = true)
	private Curriculum curriculum;

	/**
	 * Instancia una nueva cuenta de usuario.
	 */
	protected Account() {

	}

	/**
	 * Instancia una nueva cuenta de usuario.
	 *
	 * @param email
	 *            , cadena de texto con el email del usuario, que será su login.
	 * @param password
	 *            , contraseña del usuario, que se almacenará encriptada en base
	 *            de datos.
	 */
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
	 * Añade un nuevo rol a la cuenta de uusario. Este método mantiene la
	 * consistencia entre las relaciones. Este usuario se asocia al rol en
	 * concreto
	 * 
	 * @param Role
	 *            role
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

	/**
	 * Devuelve una colección de roles. La colección devuelta es una
	 * copia defensiva (nadie puede cambiarla desde el exterior)
	 *
	 * @return una coleccion de roles asociados al currículum
	 */
	public Collection<Role> getRoles() {
		return new ArrayList<Role>(roles);
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	/**
	 * Elimina un rol de una cuenta de usuario. Este método mantiene la
	 * consistencia entre las relaciones. El rol no estára asociado al usuario a
	 * partir de ahora.
	 * 
	 * @param Role
	 *            role
	 */
	public void removeRole(Role role) {
		// prevent endless loop
		for (Role r : roles) {
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
