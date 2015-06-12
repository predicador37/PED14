package es.uned.ped14.titulacion;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import es.uned.ped14.curriculum.Curriculum;

@Transactional
public interface TitulacionRepositoryInterface extends JpaRepository<Titulacion, Long>{
	
	Titulacion findByDescripcion(String descripcion);
	
	List<Titulacion> findByCurriculum(Curriculum curriculum);
		
	
}
