package es.uned.ped14.curriculum;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import es.uned.ped14.curriculum.Curriculum;

@Transactional
public interface CurriculumRepositoryInterface extends JpaRepository<Curriculum, Long>{
	
	Curriculum findByUserEmail(String email);
	
	Curriculum findByNombre(String nombre);
}
