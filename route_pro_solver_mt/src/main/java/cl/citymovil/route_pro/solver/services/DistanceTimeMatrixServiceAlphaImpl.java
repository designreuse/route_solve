package cl.citymovil.route_pro.solver.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.citymovil.route_pro.solver.dao.DistanceTimeDAO;
import cl.citymovil.route_pro.solver.domain.DistanceTime;
import cl.citymovil.route_pro.solver.domain.DistanceTimeData;
import cl.citymovil.route_pro.solver.domain.Location;
import cl.citymovil.route_pro.solver.util.LocationContainerForGoogleAsk;
import cl.citymovil.route_pro.solver.util.RelationLocation;

@Service
public class DistanceTimeMatrixServiceAlphaImpl implements DistanceTimeMatrixServiceAlpha{
	
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	DistanceTimeDAO distanceTimeDAO;
	
	@Autowired
	AskToGoogle askToGoogle;
	  

	/**Genera el HashMap de la lista de origenes a destino que actualmente se encuentran en la base de datos **/
	@Override
	public Map<Long, Map<Long, DistanceTimeData>> PreprocessAlpha(ArrayList<Location> listWithIdLocation) {
		logger.info("\n[(PreprocessAlpha)]start PreprocessAlpha\n");
		Map<Long, Map<Long, DistanceTimeData>> distanceTimeMatrixHashMapOrigin = new HashMap<Long, Map<Long, DistanceTimeData>>();
		Map<Long, DistanceTimeData> distanceTimeMatrixHashMapDestiny = new HashMap<Long, DistanceTimeData>();
		
		List <DistanceTime> distanceTimeMatrix=distanceTimeDAO.getDistanceTimeOriginsOf(listWithIdLocation);
		/**Generación del HashMap de la matriz de distancia para el Origen a Destino**/
		if(distanceTimeMatrix!=null){
			/**COMPLETO EL HASHMAP DE TODOS LOS DATOS DE LA MATRIZ DE DISTANCIA DESDE LOS ORGENES A LOS DESTINOS**/
			Long idOrigen= null;
			for(int count=0; count < distanceTimeMatrix.size(); count++){
				DistanceTimeData distanceTimeData = new DistanceTimeData((long)distanceTimeMatrix.get(count).getDistance(),(long)distanceTimeMatrix.get(count).getDuration());

				if(idOrigen==null){
					idOrigen=(Long)distanceTimeMatrix.get(count).getOrigin();
					logger.trace("[(PreprocessAlpha)]Primer ingreso");
				}
				if((Long)distanceTimeMatrix.get(count).getOrigin()!=idOrigen){
					logger.trace("[(PreprocessAlpha)](if) Cambiando y alamcenando hashMap origen... ");
					distanceTimeMatrixHashMapOrigin.put(idOrigen, distanceTimeMatrixHashMapDestiny);
					
					distanceTimeMatrixHashMapDestiny=new HashMap<Long, DistanceTimeData>();
					
					idOrigen=distanceTimeMatrix.get(count).getOrigin(); 
					distanceTimeMatrixHashMapDestiny.put((Long)distanceTimeMatrix.get(count).getDestination(), distanceTimeData);
					logger.trace("[(PreprocessAlpha)]generando nuevo valueOrigin\n");
				
				}else{
					distanceTimeMatrixHashMapDestiny.put((Long)distanceTimeMatrix.get(count).getDestination(), distanceTimeData);
					logger.trace("[(PreprocessAlpha)] (else) almacenando hashMap destiny\n");
				}
			}
			if(distanceTimeMatrixHashMapDestiny.isEmpty()!=true){
				logger.trace("[(PreprocessAlpha)] Almacenando el resto-cola");
				distanceTimeMatrixHashMapOrigin.put(idOrigen, distanceTimeMatrixHashMapDestiny);
			}
		}else{
			logger.trace("[(PreprocessAlpha)]****No hay ningun dato de la matriz de distancia de Origen a Destino ***************");
		}
		if(distanceTimeMatrixHashMapOrigin.isEmpty()){
			logger.trace("[(PreprocessAlpha)]**** El HASHMAP esta VACIO, HAY QUE CALCULARLO COMPLETO *******");
			distanceTimeMatrixHashMapOrigin.clear();
			logger.info("\n[(PreprocessAlpha)]end PreprocessAlpha\n");
			 return distanceTimeMatrixHashMapOrigin;
		}else{
			logger.trace("[(PreprocessAlpha)]**** ENVIANDO EL HASHMAP ORIGEN AL CONTROLADOR ******** \n");
			logger.info("\n[(PreprocessAlpha)]end PreprocessAlpha\n");
			return distanceTimeMatrixHashMapOrigin;
		}
	}
	
	/**En base al hashMap generado en ProprocessAlpha, genera las listas de string[] para realizar las consultas a google**/
	@Override
	public ArrayList<LocationContainerForGoogleAsk> PreprocessBeta(	
		Map<Long, Map<Long, DistanceTimeData>> distanceTimeHashMap, ArrayList <Location> arrayWithIdLocation) {
		logger.info("\n[(PreprocessBeta)]start PreprocessAlpha\n");
		ArrayList <LocationContainerForGoogleAsk> listOfLocationContainerForGoogleAsk = new ArrayList <LocationContainerForGoogleAsk>();
		LocationContainerForGoogleAsk locationContainerForGoogleAsk = new LocationContainerForGoogleAsk();
		ArrayList <Location> listLocationOrigen= new ArrayList <Location>(); 
		ArrayList <Location> listLocationDestiny= new ArrayList <Location>();  

		for(int countOrigen=0; countOrigen < arrayWithIdLocation.size() ; countOrigen++){
			Long idOrigen; 
			idOrigen = arrayWithIdLocation.get(countOrigen).getLocationId();
			/**SI NO ESTA EL ORIGEN, HAY QUE ALMACENAR EL ORIGEN CON TODOS LOS POSIBLES DESTINOS**/
			if(distanceTimeHashMap.get(idOrigen)==null){
				ArrayList<Location> listLocationDestinyTmp = new ArrayList <Location>(); 
				listLocationDestinyTmp.addAll(arrayWithIdLocation);
				listLocationDestinyTmp.remove(arrayWithIdLocation.get(countOrigen));
				logger.trace("[(PreprocessBeta)](desc HashMap) No hay definición del punto"+idOrigen+", va con todos los destinos");
				 listLocationOrigen.add(arrayWithIdLocation.get(countOrigen));
				 
				 listLocationDestiny.addAll(listLocationDestinyTmp);
				 locationContainerForGoogleAsk.setLocationOrigin(listLocationOrigen);
				 locationContainerForGoogleAsk.setLocationDestiny(listLocationDestiny);
				 listOfLocationContainerForGoogleAsk.add(locationContainerForGoogleAsk);
				 listLocationOrigen= new ArrayList <Location>(); 
				 listLocationDestiny= new ArrayList <Location>();
				 listLocationDestinyTmp= new ArrayList <Location>();
				 locationContainerForGoogleAsk=new LocationContainerForGoogleAsk(); 
			}else{
				/**SI ESTA EL ORIGEN, HAY QUE ALMACENAR SOLO LOS DESTINOS QUE NO SE ENCUENTRAN**/
					for(int countDestiny=0; countDestiny < arrayWithIdLocation.size() ; countDestiny++){
						Long idDestiny;
						idDestiny= arrayWithIdLocation.get(countDestiny).getLocationId();
						if(idOrigen!=idDestiny){
							DistanceTimeData dataLocation;
							dataLocation = distanceTimeHashMap.get(idOrigen).get(idDestiny);
						
							if(dataLocation==null){
								logger.trace("[(PreprocessBeta)]El dato no se encuentra!!!!!!! [idOrigen:"+idOrigen+"][idDestiny:"+idDestiny+"]"); 
								listLocationDestiny.add(arrayWithIdLocation.get(countDestiny));	 
							}else{
								 
								logger.trace("[(PreprocessBeta)]Estos son los valores encontrados \n distance:"+dataLocation.distance+" time:"+dataLocation.time);	
							}
						}
					}
					if(listLocationDestiny.isEmpty()!=true){
						listLocationOrigen.add(arrayWithIdLocation.get(countOrigen));
						 locationContainerForGoogleAsk.setLocationOrigin(listLocationOrigen);
						 locationContainerForGoogleAsk.setLocationDestiny(listLocationDestiny);
						 listOfLocationContainerForGoogleAsk.add(locationContainerForGoogleAsk);
						 listLocationOrigen= new ArrayList <Location>(); 
						 listLocationDestiny= new ArrayList <Location>();
						 locationContainerForGoogleAsk=new LocationContainerForGoogleAsk(); 
					}
			}
		}
		if(listOfLocationContainerForGoogleAsk.isEmpty()){
			logger.trace("[(PreprocessBeta)] listOfLocationContainerForGoogleAsk is EMPTY");
			logger.info("\n[(PreprocessBeta)]end PreprocessAlpha\n");
			return null;
			
		}else{
			logger.trace("[(PreprocessBeta)] listOfLocationContainerForGoogleAsk send");
			logger.info("\n[(PreprocessBeta)]end PreprocessAlpha\n");
			return listOfLocationContainerForGoogleAsk;
		}

	}

@Override
public ArrayList<RelationLocation> Process(LocationContainerForGoogleAsk locationContainerForGoogle) {
	logger.info("\n[(Process)]start Process \n");
	List<Location> listOriginLocation = locationContainerForGoogle.getLocationOrigin();
	List<Location> listDestLocation = locationContainerForGoogle.getLocationDestiny();
	if(listDestLocation==null || listDestLocation.size()==0){
		for(int count=0; count < listOriginLocation.size(); count++){
			logger.info("Imprimiendo Origen en Process:"+listOriginLocation.get(count).getLocationId());
		}
		logger.trace("[(Process)]No hay nuevas Locaciones para realizar preguntas a Google, Saliendo de Process");
		logger.info("\n[(Process)]**FIN Process**\n");
		return null;
	
	}else{
		ArrayList<RelationLocation>  resp = askToGoogle.getDistanceByGoogleAlpha(locationContainerForGoogle);
	
	//	for(RelationLocation re: resp){
	//		System.out.println("imprimiendo el resultado de askToGoogle. \n getGoingDistance"
	//	+re.getGoingDistance()
	//	+"\n getGoingDuration:"+re.getGoingDuration()
	//	+"id ORIGEN:"+re.getIdFirstLocation()
	//	+"ID DESTINO:"+re.getIdSecondLocation());
	//		
	//	}
		logger.trace("\n[(Process)]:::::::  TERMINANDO Proceso de Carga de GOOGLE  ::::::::::");
		
		logger.info("\n[(Process)]end Process \n");
		return resp;
	}
}

	@Override
	public void PostProcessAlpha(ArrayList<RelationLocation> relationLocationOfAllLocation) {
		logger.info("\n[(PostProcessAlpha)]start PostProcessAlpha \n");
		if(relationLocationOfAllLocation!=null){			
		 for(int count=0; count < relationLocationOfAllLocation.size() ; count++){
			 RelationLocation relacion = relationLocationOfAllLocation.get(count);
			 logger.trace("[(PostProcessAlpha)] Datos Extraidos GoingDistance: "+relacion.getGoingDistance());
			 logger.trace("[(PostProcessAlpha)] Id Primer Location: "+relacion.getIdFirstLocation());
			 logger.trace("[(PostProcessAlpha)] Id Segundo Location: "+relacion.getIdSecondLocation());
			 logger.trace("///////////// \n");
			 DistanceTime d = new DistanceTime(relacion.getIdFirstLocation(), relacion.getIdSecondLocation() ,relacion.getGoingDistance().longValue(),relacion.getGoingDuration().longValue());    
			 distanceTimeDAO.persistDistanceTime(d);
		 }
		}else{
			logger.trace("[(PostProcessAlpha)]**relationLocationOfAllLocation==null**\n");
		}
		logger.info("\n[(PostProcessAlpha)]end PostProcessAlpha \n");
		
	}



	
	



}
