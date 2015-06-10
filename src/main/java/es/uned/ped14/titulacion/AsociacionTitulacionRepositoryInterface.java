package es.uned.ped14.titulacion;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import es.uned.ped14.curriculum.Curriculum;

@Transactional
public interface AsociacionTitulacionRepositoryInterface extends JpaRepository<AsociacionTitulacion, Long>{

	AsociacionTitulacion findByCurriculum(Curriculum curriculum);
	
	AsociacionTitulacion findByTitulacion(Titulacion titulacion);
		
	
}
