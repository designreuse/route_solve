package cl.citymovil.route_pro.solver.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.citymovil.route_pro.solver.domain.DistanceTime;

@Repository
public class DistanceTimeDAO {

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = true)
	public List<DistanceTime> getAll() {
		Query query = this.em
				.createQuery("SELECT x FROM DistanceTime x");
		List<DistanceTime> result = new ArrayList<DistanceTime>();
		for (Iterator localIterator = query.getResultList().iterator(); localIterator
				.hasNext();) {
			Object o = localIterator.next();
			result.add((DistanceTime) o);
		}

		return result;
	}
}
