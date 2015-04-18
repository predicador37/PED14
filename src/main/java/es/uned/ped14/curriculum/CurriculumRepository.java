package es.uned.ped14.curriculum;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.*;
import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import es.uned.ped14.account.Account;
import es.uned.ped14.experiencia.ExperienciaProfesional;
import es.uned.ped14.titulacion.Titulacion;

@Repository
@Transactional(readOnly = true)

public class CurriculumRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
    private SessionFactory sessionFactory;
	
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
	
	public List<Curriculum> findByPaisAndCiudadAndExperiencia(String pais, String ciudad, ExperienciaProfesional experiencia) {
		Criteria criteria = getSession().createCriteria(Curriculum.class);
		if (pais != null) {
		    criteria.add(Expression.eq("pais", pais));
		}
		
		if (ciudad != null) {
		    criteria.add(Expression.eq("ciudad", ciudad));
		}
		
		List results = criteria.list();
		
		return results;
	}
	
	private Session getSession(){
        return sessionFactory.getCurrentSession();
    }

	
}