package es.uned.ped14.admin;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Clase FuncionService, servicio que implementa un bean con ámbito de todo el
 * ciclo de vida de la aplicación que permite guardar el estado de activación (o
 * desactivación) de las funcionalidades de creación, edición y borrado de
 * elementos.
 */
@Component
@Service
public class FuncionService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -411221535542128123L;
	/**
	 * 
	 */

	private Boolean create;
	private Boolean edit;
	private Boolean delete;

	/**
	 * Init. Carga los datos iniciales, por defecto siempre activos.
	 * 
	 */
	@PostConstruct
	private void init() {
		this.create = true;
		this.edit = true;
		this.delete = true;

	}

	public Boolean getCreate() {
		return create;
	}

	public void setCreate(Boolean create) {
		this.create = create;
	}

	public Boolean getEdit() {
		return edit;
	}

	public void setEdit(Boolean edit) {
		this.edit = edit;
	}

	public Boolean getDelete() {
		return delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}

}
