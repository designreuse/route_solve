package cl.citymovil.route_pro.solver.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import cl.citymovil.route_pro.solver.domain.DistanceTime;
import cl.citymovil.route_pro.solver.domain.Location;

@Repository
public class DistanceTimeDAO {
	
	protected final Log logger = LogFactory.getLog(getClass());

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
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<DistanceTime> getDistanceTimeList() {
		
		Query query = this.em.createQuery("SELECT s FROM DistanceTime s");


		List<DistanceTime> distanceTime = (List<DistanceTime>)query.getResultList();
		
		return distanceTime;
	}
	//NUEVO METODO DE DISTANCETIME PARA OBTENER LOS DATOS DE UNA MATRIZ DE DISTANCIA EN BASE A UNA LISTA DE LOCATION
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List <DistanceTime> getDistanceTimeOriginsOf(ArrayList <Location> locationList) {
		System.out.println("**\nDEntro de getDistanceTimeOriginsOf\n**\n");
		List <DistanceTime> distTime = new ArrayList <DistanceTime>();
		
		for(int count=0; count < locationList.size(); count++){
			List <DistanceTime> distTimeTmp = new ArrayList <DistanceTime>();
			Query query = this.em.createQuery("SELECT s FROM DistanceTime s where s.origin = :origins")
					.setParameter("origins", locationList.get(count).getLocationId() );
			logger.info("/////******* getLocationId :"+locationList.get(count).getLocationId());
			
			
			distTimeTmp = (List<DistanceTime>) query.getResultList();
			
			if(distTimeTmp.isEmpty()){
				System.out.println("@@@No se encontro ninguna matriz de distancia para la locación con ID="+locationList.get(count).getLocationId());
			}else{
				System.out.println("@@@ Entro al Else");
				distTime.addAll(distTimeTmp);
				System.out.println("Encontre");
//				System.out.println("La matriz de distancia encontrada tiene los datos: \n Origen"+distTime.getOrigin()+" \n Destiny:"+distTime.getDestination()+"\n Distance: "+distTime.getDistance());
//				listDistanceTime.add(count, distTime);
			}
		}
		if(distTime.isEmpty()){
			return null;
		}else{
			
			for(int i=0; i < distTime.size() ; i++){
				System.out.println("\n Origen: "+distTime.get(i).getOrigin()+"\n Destino: "+distTime.get(i).getDestination()+"\n Distancia:"+distTime.get(i).getDistance()+"\n Duracion:"+distTime.get(i).getDuration());
				
			}
			return distTime;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List <DistanceTime> getDistanceTimeDestiniesOf(ArrayList <Location> locationList) {
		System.out.println("**\nDEntro de getDistanceTimeDestiniesOf\n**\n");
		List <DistanceTime> distTime = new ArrayList <DistanceTime>();
		
		for(int count=0; count < locationList.size(); count++){
			List <DistanceTime> distTimeTmp = new ArrayList <DistanceTime>();
			Query query = this.em.createQuery("SELECT s FROM DistanceTime s where s.destination = :destinies")
					.setParameter("destinies", locationList.get(count).getLocationId() );
			
			logger.info("/////******* getLocationId :"+locationList.get(count).getLocationId());
			
			
			distTimeTmp = (List<DistanceTime>) query.getResultList();
			
			if(distTimeTmp.isEmpty()){
				System.out.println("@@@No se encontro ninguna matriz de distancia para la locación con ID="+locationList.get(count).getLocationId());
			}else{
				System.out.println("@@@ Entro al Else");
				distTime.addAll(distTimeTmp);
			}
		}
		if(distTime.isEmpty()){
			return null;
		}else{
			for(int i=0; i < distTime.size() ; i++){
				System.out.println("\n Destino: "+distTime.get(i).getDestination()+"\n Origen: "+distTime.get(i).getOrigin()+"\n Distancia:"+distTime.get(i).getDistance()+"\n Duracion:"+distTime.get(i).getDuration());
				
			}
			return distTime;
		}
	}


	@Transactional
	public void mergeDistanceTime(DistanceTime distanceTime) {
		em.merge(distanceTime);
		
	}


	@Transactional
	public void persistDistanceTime(DistanceTime distanceTime) {
		System.out.println("Ingresando a la base de datos un DistanceTime, sus datos son:\n");
		System.out.println("Origen :"+distanceTime.getOrigin()+"\nDestination:"+distanceTime.getDestination()+"\nDistance:"+distanceTime.getDistance()+"\nDuration:"+distanceTime.getDuration());
		em.persist(distanceTime);
	
	}
}
