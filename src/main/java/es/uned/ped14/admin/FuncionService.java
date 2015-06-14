package es.uned.ped14.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.ScopedProxyMode;

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
