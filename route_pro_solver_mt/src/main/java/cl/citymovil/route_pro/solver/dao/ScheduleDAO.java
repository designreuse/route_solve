package cl.citymovil.route_pro.solver.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.citymovil.route_pro.solver.domain.Schedule;

@Repository
public class ScheduleDAO {

	// @PersistenceContext(type = PersistenceContextType.EXTENDED)
	// @PersistenceContext(type = PersistenceContextType.TRANSACTION)
	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = true)
	public List<Schedule> queryall() {
		Query query = this.em.createQuery("SELECT x FROM Schedule x");
		List result = query.getResultList();
		return result;
	}

	@Transactional(readOnly = true)
	public List<Schedule> queryByDate(Date date) {
		Query query = this.em.createQuery("SELECT x FROM Schedule x where x.date = :date").setParameter("date", date, TemporalType.DATE);

		List result = query.getResultList();
		return result;
	}

	@Transactional
	public Schedule save(Schedule schedule) {

		this.em.persist(schedule);
		return schedule;
	}

	@Transactional
	public Schedule update(Schedule schedule) {

		try {

			this.em.merge(schedule);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("--------------------------------------------------------------------");
			e.printStackTrace();
		}

		return schedule;
	}

	public Schedule getById(long scheduleId) {
		Schedule temp = em.find(Schedule.class, scheduleId);
		return temp;
	}
}
