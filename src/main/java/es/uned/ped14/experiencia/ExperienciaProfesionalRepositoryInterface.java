package es.uned.ped14.experiencia;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import es.uned.ped14.curriculum.Curriculum;

@Transactional
public interface ExperienciaProfesionalRepositoryInterface extends JpaRepository<ExperienciaProfesional, Long>{

	ExperienciaProfesional findByCurriculum(Curriculum curriculum);
	
	ExperienciaProfesional findByEmpresa(String descripcion);
	
	ExperienciaProfesional findByCargo(String descripcion);
		
	
}
