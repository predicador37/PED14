package es.uned.ped14.titulacion;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.uned.ped14.conocimiento.Conocimiento;
import es.uned.ped14.curriculum.Curriculum;
import es.uned.ped14.curriculum.Curriculum_;
import es.uned.ped14.titulacion.AsociacionTitulacion;
import es.uned.ped14.titulacion.AsociacionTitulacion_;
import es.uned.ped14.titulacion.Titulacion;
import es.uned.ped14.titulacion.Titulacion_;

@Repository
@Transactional(readOnly = true)

public class TitulacionRepository{
	
	Logger logger = LoggerFactory.getLogger(TitulacionRepository.class);
	 
	@PersistenceContext
	private EntityManager entityManager;


	public List<Titulacion> findByCurriculum(Curriculum curriculum) {
		logger.info("Titulacion repository findByCurriculum");
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Titulacion> criteriaQuery = builder.createQuery(Titulacion.class );
		Root<Titulacion> titulacionRoot = criteriaQuery.from(Titulacion.class);
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		
		if (curriculum != null) {
			
			Join<Titulacion, AsociacionTitulacion> curriculos = titulacionRoot.join(Titulacion_.curriculos);
			Predicate predicate4 = builder.equal(
		              curriculos.get(AsociacionTitulacion_.curriculum).get(Curriculum_.id),
		              curriculum.getId());
			predicates.add(predicate4);
			
		}
		
	
		criteriaQuery.select(titulacionRoot).where(predicates.toArray(new Predicate[]{})).distinct(true);
		criteriaQuery.orderBy(builder.desc(titulacionRoot.get("id")));
		return entityManager.createQuery(criteriaQuery).getResultList();
		
	}


	
}
