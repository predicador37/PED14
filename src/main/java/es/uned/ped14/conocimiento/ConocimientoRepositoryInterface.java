package es.uned.ped14.conocimiento;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import es.uned.ped14.curriculum.Curriculum;

@Transactional
public interface ConocimientoRepositoryInterface extends JpaRepository<Conocimiento, Long>{

	List<Conocimiento> findByCurriculum(Curriculum curriculum);
	
	List<Conocimiento> findByDescripcion(String descripcion);
		
	
}
