package cl.citymovil.route_pro.solver.dao;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.citymovil.route_pro.solver.domain.Location;




@Repository
public class LocationDAOImpl implements LocationDAO{
	
	@PersistenceContext//(type=PersistenceContextType.EXTENDED)
    public EntityManager em;
	
	
	public void setEntityManager(EntityManager em) {
	        this.em = em;
	}
	
	@Override
	public void mergeLocation(Location loc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void persistLocation(Location loc) {
		// TODO Auto-generated method stub
		em.persist(loc);
		
	}

	
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public ArrayList<Location> getLocationList() {
//		System.out.println("/////// inicialndo getLocationList ///////");
		
		Query query = this.em.createQuery("SELECT s FROM Location s");//en la consulta, es necesario el 
		                                                              //nombre de la clase y no el nombre de la table de la base de datos


		ArrayList<Location> locations = (ArrayList<Location>)query.getResultList();
		
		// return em.createQuery("select p from Location p ").getResultList();
		// return em.createQuery("select loc from Location loc order by loc.id").getResultList();
		//Query query = em.createQuery("SELECT s FROM Location s");
		
		//return (List<Location>)query.getResultList();
		
		return locations;
	}

	@Override
	public void updateLocation(long LocationId) {
		
		/*
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Query query = this.em.createNativeQuery("update location "
				+ "SET real_arrival_time = '"+aaa+"' "
				+ "where "
						+ "scheduled_customer_id = '"+LocationId+"' "
						+ "and real_arrival_time is null");
		query.executeUpdate();
		*/
		System.out.println("MÉTODO AÚN NO IMPLEMENTADO");
		
	}



	@Override
	public void deleteLocation(long LocationId) {
		// TODO Auto-generated method stub
		
	}
}
