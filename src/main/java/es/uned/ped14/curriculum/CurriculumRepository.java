package es.uned.ped14.curriculum;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.uned.ped14.conocimiento.Conocimiento;
import es.uned.ped14.conocimiento.Conocimiento_;
import es.uned.ped14.titulacion.Titulacion;
import es.uned.ped14.titulacion.Titulacion_;

/**
 * Clase CurriculumRepository, implementa el patrón repository para la
 * recuperación de instancias desde la base de datos.
 */
@Repository
@Transactional(readOnly = true)
public class CurriculumRepository {

	/** Logger para depuración de errores. */
	Logger logger = LoggerFactory.getLogger(CurriculumRepository.class);

	/** Entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Find by user email.
	 *
	 * Método que devuelve una instancia de currículum asociada al email
	 * (login)de un usuario determinado.
	 * 
	 * @param email
	 *            : cadena de texto con el email
	 * @return objeto curriculum de clase Curriculum
	 */
	public Curriculum findByUserEmail(String email) {
		try {
			return entityManager
					.createNamedQuery(Curriculum.FIND_BY_USER_EMAIL,
							Curriculum.class).setParameter("email", email)
					.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}

	/**
	 * Método que devuelve un listado de currículos filtrados por país, ciudad,
	 * experiencia, titulación y conocimiento utilizando una consulta realizada
	 * con JPA criteria. Los parámetros pueden ser nulos, en cuyo caso no se
	 * filtrará por ellos. La experiencia se filtra devolviendo todos aquellos
	 * currículos con experiencia mayor o igual a la introducida.
	 *
	 * @param pais
	 *            cadena de texto con el país a buscar
	 * @param ciudad
	 *            cadena de texto con la ciudad a buscar
	 * @param experiencia
	 *            cadena de texto con la experiencia mínima a partir de la cual
	 *            se devolverán resultados
	 * @param titulacion
	 *            cadena de texto con la titulación a buscar
	 * @param conocimiento
	 *            cadena de texto con el conocimiento a buscar
	 * @return lista de currículos
	 */

	public List<Curriculum> findByOptionalParameters(String pais,
			String ciudad, Integer experiencia, String titulacion,
			String conocimiento) {
		logger.info("Curriculum repository findByOptionalParameters");
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Curriculum> criteriaQuery = builder
				.createQuery(Curriculum.class);
		Root<Curriculum> curriculumRoot = criteriaQuery.from(Curriculum.class);
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (pais != null) {
			Predicate predicate1 = builder.like(
					builder.lower(curriculumRoot.<String> get("pais")), "%"
							+ pais.toLowerCase() + "%");
			predicates.add(predicate1);
		}

		if (ciudad != null) {
			Predicate predicate2 = builder.like(
					builder.lower(curriculumRoot.<String> get("ciudad")), "%"
							+ ciudad.toLowerCase() + "%");
			predicates.add(predicate2);
		}

		if (experiencia != null) {
			Predicate predicate3 = builder.ge(
					curriculumRoot.<Integer> get("experiencia"),
					experiencia * 12);
			predicates.add(predicate3);
		}

		if (titulacion != null) {
			Join<Curriculum, Titulacion> titulaciones = curriculumRoot
					.join(Curriculum_.titulaciones);
			Predicate predicate4 = builder.like(
					builder.lower(titulaciones.get(Titulacion_.descripcion)),
					"%" + titulacion.toLowerCase() + "%");
			predicates.add(predicate4);

		}

		if (conocimiento != null) {

			Join<Curriculum, Conocimiento> conocimientos = curriculumRoot
					.join(Curriculum_.conocimientos);
			Predicate predicate5 = builder
					.like(builder.lower(conocimientos
							.get(Conocimiento_.descripcion)), "%"
							+ conocimiento.toLowerCase() + "%");
			predicates.add(predicate5);

		}
		criteriaQuery.select(curriculumRoot)
				.where(predicates.toArray(new Predicate[] {})).distinct(true);
		criteriaQuery.orderBy(builder.desc(curriculumRoot.get("experiencia")));
		return entityManager.createQuery(criteriaQuery).getResultList();

	}

}
