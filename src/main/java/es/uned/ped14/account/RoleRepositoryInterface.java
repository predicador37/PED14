package es.uned.ped14.account;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import es.uned.ped14.curriculum.Curriculum;

@Transactional
public interface RoleRepositoryInterface extends JpaRepository<Role, Long>{
	
	List<Role> findByDescripcion(String descripcion);

	Role findByDescripcionAndUser(String descripcion, Account user);
		
	
}
