package es.uned.ped14.titulacion;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import es.uned.ped14.curriculum.Curriculum;

@Transactional
public interface TitulacionRepositoryInterface extends JpaRepository<Titulacion, Long>{

	Titulacion findByCurriculum(Curriculum curriculum);
	
	Titulacion findByDescripcion(String descripcion);
		
	
}
