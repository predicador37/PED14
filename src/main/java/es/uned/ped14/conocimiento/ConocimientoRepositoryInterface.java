package es.uned.ped14.conocimiento;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import es.uned.ped14.curriculum.Curriculum;

@Transactional
public interface ConocimientoRepositoryInterface extends JpaRepository<Conocimiento, Long>{

	Conocimiento findByCurriculum(Curriculum curriculum);
	
	Conocimiento findByDescripcion(String descripcion);
		
	
}
