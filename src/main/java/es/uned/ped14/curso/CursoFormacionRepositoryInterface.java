package es.uned.ped14.curso;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import es.uned.ped14.curriculum.Curriculum;

@Transactional
public interface CursoFormacionRepositoryInterface extends JpaRepository<CursoFormacion, Long>{

	CursoFormacion findByCurriculum(Curriculum curriculum);
	
	CursoFormacion findByDescripcion(String descripcion);
		
	
}
