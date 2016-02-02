package cl.citymovil.route_pro.solver.dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.citymovil.route_pro.solver.domain.Route;

@Repository
public class RouteDAO{

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	@Transactional
	public Route save(Route route) {
		this.em.persist(route);
		return route;
	}
	
//	@Transactional(readOnly = true)
	public Route saveRoute(Route route) {
		
		try {
			Query query = this.em.createNativeQuery("insert into route "
					+ "(vehicle_id, schedule_id, total_customers_in_route, total_meters, total_kilograms) values ('"
					+ route.getVehicle().getVehicleId()+"','"
					+ route.getSchedule().getScheduleId()+"','"
					+ route.getTotalCustomersInRoute()+"','"
					+ route.getTotalMeters()+"','"
					+ route.getTotalKilograms()+"')");
			query.executeUpdate();
			
			BigInteger lastId = (BigInteger) this.em.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult();
			
			Long longLastId = lastId.longValue();
			route.setRouteId(longLastId);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("--------------------------------------------------------------------");
			e.printStackTrace();
		}
		
		return route;
		
	}
	
}
