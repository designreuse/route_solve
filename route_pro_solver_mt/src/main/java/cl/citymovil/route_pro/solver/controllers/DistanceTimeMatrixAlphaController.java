package cl.citymovil.route_pro.solver.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cl.citymovil.route_pro.solver.dao.LocationDAO;
import cl.citymovil.route_pro.solver.domain.DistanceTimeData;
import cl.citymovil.route_pro.solver.domain.Location;
import cl.citymovil.route_pro.solver.services.DistanceTimeMatrixServiceAlpha;
import cl.citymovil.route_pro.solver.util.LocationContainerForGoogleAsk;
import cl.citymovil.route_pro.solver.util.RelationLocation;

@Controller
@RequestMapping(value="/requestalpha.htm")
public class DistanceTimeMatrixAlphaController {
	
	/** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private DistanceTimeMatrixServiceAlpha distanceTimeMatrixServiceAlpha;
    /********** Inicio Adicional*/
    
    @Autowired
    private LocationDAO locationDAO;
    /********** Fin Adicional*/
    
    
    
    @RequestMapping(method = RequestMethod.GET)
     public void onSubmit()
     {
    	logger.info("\n[(onSubmit)]start onSubmit\n");
    	   /********** Adicional*/
    	ArrayList<Location> arrayWithIdLocation = locationDAO.getLocationList();
    	  /********** Fin Adicional*/

    	Map<Long, Map<Long, DistanceTimeData>> distanceTimeMatrixHashMap= new HashMap<Long, Map<Long, DistanceTimeData>>();
    	distanceTimeMatrixHashMap= distanceTimeMatrixServiceAlpha.PreprocessAlpha(arrayWithIdLocation);     	
    	
    	if(distanceTimeMatrixHashMap==null){
    		
     		logger.trace("[(onSubmit)](FAIL)Don't have Location.");
     		
     	}else{
     		
     		logger.trace("[(onSubmit)](OK)The PreprocessAlpha was return OK.");
//     		for (Map.Entry<Long, Map<Long, DistanceTimeData>> outer : distanceTimeMatrixHashMap.entrySet()) {
//    		    System.out.println("Key: " + outer.getKey() +  "\n");
//			for( Entry<Long, DistanceTimeData> algo : outer.getValue().entrySet() ){
//    			   System.out.println("Key = " + algo.getKey() + ", distance = " + algo.getValue().distance  );
//    		   }
//    		 } 
     		ArrayList<LocationContainerForGoogleAsk> listOfLocationContainerForGoogle =new  ArrayList <LocationContainerForGoogleAsk>();
     		listOfLocationContainerForGoogle=distanceTimeMatrixServiceAlpha.PreprocessBeta(distanceTimeMatrixHashMap,arrayWithIdLocation);
     		if(listOfLocationContainerForGoogle==null){
     			logger.trace("[(onSubmit)](x)listOfLocationContainerForGoogle==null");
     		}else{
     			logger.trace("[(onSubmit)](ok)listOfLocationContainerForGoogle!=null");
     		
     			logger.trace("\n[(onSubmit)]***********PUNTOS EN CONFLICTO****************\n");
	     		for(LocationContainerForGoogleAsk b: listOfLocationContainerForGoogle){
	     			logger.trace("[(onSubmit)]Enviando a Process");
	     			ArrayList<RelationLocation> distanceMatrixList = distanceTimeMatrixServiceAlpha.Process(b);
	     			logger.trace("[(onSubmit)]Enviando a PostProcessAlpha");
	     			distanceTimeMatrixServiceAlpha.PostProcessAlpha(distanceMatrixList);
	     		}
     		}
     	} 
    	logger.info("\n[(onSubmit)]end onSubmit\n");
     }
    protected void formBackingObject(HttpServletRequest request) throws ServletException {
    	
    }
}
