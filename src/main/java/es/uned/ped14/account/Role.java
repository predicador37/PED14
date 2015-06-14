package es.uned.ped14.account;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import es.uned.ped14.curriculum.Curriculum;

@SuppressWarnings("serial")
@Entity
@Table(name = "role")
@Proxy(lazy=false)
public class Role implements java.io.Serializable {

@Id
@GeneratedValue
private Long id;

@Column
private String descripcion;
 
@ManyToOne(fetch=FetchType.EAGER, targetEntity = Account.class, cascade=CascadeType.PERSIST)
@JoinColumn(name="user_id")
private Account user;

public Role() {

}

public Role(String descripcion) {
	this.descripcion = descripcion;
}

@Override 
public String toString() {
	return this.descripcion;
			
}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getDescripcion() {
	return descripcion;
}

public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}


public void setUser(Account user) {
	//prevenir bucle sin fin
	if (sameAsFormer(user))
	return ;
	//fijar nuevo currículum
	Account viejoUser = this.user;
	this.user = user;
	//eliminar del currículum viejo
	if (viejoUser!=null)
	viejoUser.removeRole(this);
	//fijarme a mí mismo como nuevo currículum
	if (user!=null)
	user.addRole(this);
}

 private boolean sameAsFormer(Account nuevoUser) {
	 return user==null? nuevoUser == null : user.equals(nuevoUser);
	 }

public Account getUser() {
	return user;
}
@PreRemove
public void preRemove() {
setUser(null);
}
 
  public boolean equals(Object object) {
    if (object instanceof Role) {
      Role otherId = (Role) object;
      return (otherId.id == this.id && otherId.descripcion == this.descripcion);
    }
    return false;
  }


}
