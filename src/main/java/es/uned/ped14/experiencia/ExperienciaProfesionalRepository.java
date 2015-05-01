package es.uned.ped14.experiencia;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class ExperienciaProfesionalRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public ExperienciaProfesional save(ExperienciaProfesional experienciaProfesional) {
		entityManager.persist(experienciaProfesional);
		return experienciaProfesional;
	}
	
}
