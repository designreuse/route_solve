package cl.citymovil.route_pro.solver.dao;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.citymovil.route_pro.solver.domain.Location;
import cl.citymovil.route_pro.solver.domain.ScheduledCustomer;

@Repository
public class ScheduledCustomerDAO {

	 @PersistenceContext
	   private EntityManager em;
	 
	   @Transactional
	   public ScheduledCustomer save(ScheduledCustomer scheduledCustomer) {
	     this.em.persist(scheduledCustomer);
	     return scheduledCustomer;
	   }
	   
	   
//	   @Transactional
	   public ScheduledCustomer saveScheduledCustomer(ScheduledCustomer scheduledCustomer)
	   {

		   try {
				Query query = this.em.createNativeQuery("insert into scheduled_customer "
						+ "("
						+ " name,"
						+ " location_id,"
						+ " total_demand, "
						+ " ready_time,"
						+ " due_time,"
						+ " service_duration,"
						+ " arrival_time,"
						+ " external_id,"
						+ " sequence,"
						+ " route_id "
						+ ") values ('"
						+ scheduledCustomer.getName()+"','"
						+ scheduledCustomer.getLocation().getLocationId()+"','"
						+ scheduledCustomer.getTotalDemand()+"','"
						+ scheduledCustomer.getReadyTime()+"','"
						+ scheduledCustomer.getDueTime()+"','"
						+ scheduledCustomer.getServiceDuration()+"','"
						+ scheduledCustomer.getArrivalTime()+"','"
						+ scheduledCustomer.getExternalId()+"','"
						+ scheduledCustomer.getSequence()+"','"
						+ scheduledCustomer.getRoute().getRouteId()+"') ");
						
				query.executeUpdate();
				
				BigInteger lastId = (BigInteger) this.em.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult();
				
				Long longLastId = lastId.longValue();
				scheduledCustomer.setScheduledCustomerId(longLastId);
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("--------------------------------------------------------------------");
				e.printStackTrace();
			}
			
			return scheduledCustomer;
	   }
}
