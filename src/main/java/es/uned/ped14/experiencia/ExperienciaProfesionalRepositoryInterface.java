package es.uned.ped14.experiencia;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import es.uned.ped14.curriculum.Curriculum;

@Transactional
public interface ExperienciaProfesionalRepositoryInterface extends JpaRepository<ExperienciaProfesional, Long>{

	List<ExperienciaProfesional> findByCurriculum(Curriculum curriculum);
	
	ExperienciaProfesional findByEmpresa(String descripcion);
	
	ExperienciaProfesional findByCargo(String descripcion);
		
	
}
