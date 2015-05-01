package es.uned.ped14.curriculum;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)

public class CurriculumRepository {
	
	Logger logger = LoggerFactory.getLogger(CurriculumServiceTest.class);
	 
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public Curriculum save(Curriculum curriculum) {
		entityManager.persist(curriculum);
		return curriculum;
	}
	
	public Curriculum findOne(Long id) {
		try {
			return entityManager.createNamedQuery(Curriculum.FIND_ONE, Curriculum.class)
					.setParameter("id", id)
					.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	/**
	 * Método que devuelve un listado de currículos filtrados por país, ciudad y experiencia utilizando una
	 * consulta realizada con JPA criteria. Los parámetros pueden ser nulos, en cuyo caso no se filtrará por ellos.
	 * La experiencia se filtra devolviendo todos aquellos currículos con experiencia mayor o igual a la introducida.
	 * @param pais: String con el país de origen
	 * @param ciudad: String con la ciudad de origen
	 * @param experiencia: Integer con la experiencia en años
	 * @return List<Curriculum>
	 */
	public List<Curriculum> findByPaisAndCiudadAndGreaterThanExperiencia(String pais, String ciudad, Integer experiencia) {
		logger.info("Curriculum repository findByPaisAndCiudadAndGreaterThanExperiencia");
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Curriculum> criteriaQuery = builder.createQuery( Curriculum.class );
		Root<Curriculum> curriculumRoot = criteriaQuery.from(Curriculum.class );
		List<Predicate> predicates = new ArrayList<Predicate>();
		
	
		if (pais != null) {
			Predicate predicate1 = builder.equal(
						              curriculumRoot.get("pais"),
						              pais);
		   predicates.add(predicate1);
		}
		
		if (ciudad != null) {
			Predicate predicate2 = builder.equal(
		              curriculumRoot.get("ciudad"),
		              ciudad);
			predicates.add(predicate2);
		}
		
		if (experiencia != null) {
			Predicate predicate3 = builder.ge(
		              curriculumRoot.<Integer>get("experiencia"),
		              experiencia*12);
			predicates.add(predicate3);
		}
		criteriaQuery.select(curriculumRoot).where(predicates.toArray(new Predicate[]{}));
		criteriaQuery.orderBy(builder.desc(curriculumRoot.get("experiencia")));
		return entityManager.createQuery(criteriaQuery).getResultList();
		
	}


	/**
	 * Método que devuelve una lista de todos los currículos existentes utilizando una
	 * consulta JPA criteria.
	 * @return List<Curriculum>
	 */
	public List<Curriculum> findAll() {
		logger.info("Curriculum repository findAll");
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Curriculum> criteriaQuery = builder.createQuery(Curriculum.class );
		Root<Curriculum> curriculumRoot = criteriaQuery.from(Curriculum.class );
		CriteriaQuery<Curriculum> all = criteriaQuery.select(curriculumRoot);
		TypedQuery<Curriculum> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
	}

	
}
