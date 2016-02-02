package cl.citymovil.route_pro.solver.services;

import java.util.ArrayList;
import java.util.Map;

import cl.citymovil.route_pro.solver.domain.DistanceTimeData;
import cl.citymovil.route_pro.solver.domain.Location;
import cl.citymovil.route_pro.solver.util.LocationContainerForGoogleAsk;
import cl.citymovil.route_pro.solver.util.RelationLocation;

public interface DistanceTimeMatrixServiceAlpha {
	
	Map<Long, Map<Long, DistanceTimeData>>  PreprocessAlpha(ArrayList <Location> arrayWithIdLocation);
	
	ArrayList<LocationContainerForGoogleAsk> PreprocessBeta(Map<Long, Map<Long, DistanceTimeData>> distanceTimeHashMap, ArrayList <Location> arrayWithIdLocation );
	
	ArrayList<RelationLocation>  Process(LocationContainerForGoogleAsk locationContainerForGoogle);
	
	void PostProcessAlpha(	ArrayList<RelationLocation>  relationLocation);
}
