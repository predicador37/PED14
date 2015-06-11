package es.uned.ped14.curriculum;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import es.uned.ped14.curriculum.Curriculum;

@Transactional
public interface CurriculumRepositoryInterface extends JpaRepository<Curriculum, Long>{

	
}
