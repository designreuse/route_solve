package cl.citymovil.route_pro.solver.dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.citymovil.route_pro.solver.domain.Order;
import cl.citymovil.route_pro.solver.domain.Vehicle;

@Repository
public class OrderDAO {

	@PersistenceContext
	// (type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	@Transactional(readOnly = true)
	public List<Order> queryAll() {
		Query query = this.em.createQuery("SELECT x FROM Order x");

		List result = query.getResultList();
		return result;
	}

	@Transactional(readOnly = true)
	public List<Order> queryByDate(Date date) {
		Query query = this.em.createQuery("SELECT x FROM Order x where x.date = :date").setParameter("date", date, TemporalType.DATE);

		List result = query.getResultList();
		return result;
	}

	// @Transactional(readOnly = true)
	// public List<Order> queryByDateAndOrderType(Date date, OrderType
	// orderType)
	// {
	// Query query =
	// this.em.createQuery("SELECT x FROM Order x where x.date = :date and orderTypeId = :orderTypeId ")
	// .setParameter("date", date,TemporalType.DATE )
	// .setParameter("orderTypeId", orderType.getOrderTypeId());
	// // .setFirstResult(0)
	// // .setMaxResults(40);
	// List result = query.getResultList();
	// return result;
	// }

	@Transactional(readOnly = true)
	public List<Order> getOrdersByDateAndVehicle(Date date, Vehicle vehicle) {
		Query query = this.em.createQuery("SELECT x FROM Order x where x.date = :date and vehicleId = :vehicleId ").setParameter("date", date, TemporalType.DATE).setParameter("vehicleId", vehicle.getVehicleId());
		List result = query.getResultList();
		return result;
	}

//	@Transactional
	public Order save(Order order) {
		this.em.persist(order);
		return order;
	}

//	@Transactional
	public Order saveOrder(Order order) {
		 
		try {
			
			
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			
				Query query = this.em.createNativeQuery("insert into `order` "
						+ "("
						+ " date,"
						+ " demand,"
						+ " external_id,"
						+ " customer_id,"
						+ " scheduled_customer_id"
						+ ") values ('"
						+ formatter.format(order.getDate())+"','"
						+ order.getDemand()+"','"
						+ order.getExternalId()+"','"
						+ order.getCustomer().getCustomerId()+"','"
						+ order.getScheduledCustomer().getScheduledCustomerId()+"') ");
						
				query.executeUpdate();
				
				BigInteger lastId = (BigInteger) this.em.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult();
				
				Long longLastId = lastId.longValue();
				order.setOrderId(longLastId);
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("--------------------------------------------------------------------");
				e.printStackTrace();
			}
			
			return order;
	}

	@Transactional
	public void remove(Order order) {
		this.em.remove(order);
	}

	@Transactional
	public Order update(Order order) {
		Order temp = em.find(Order.class, order.getOrderId());
		if (temp == null) {
			throw new IllegalArgumentException("Unknown orderDetail id");
		}
		temp.setDemand(order.getDemand());
		return order;
	}

}
