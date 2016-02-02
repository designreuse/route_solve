package cl.citymovil.route_pro.solver.services;


import java.util.ArrayList;

import cl.citymovil.route_pro.solver.util.LocationContainer;
import cl.citymovil.route_pro.solver.util.LocationContainerForGoogleAsk;
import cl.citymovil.route_pro.solver.util.RelationLocation;

public interface AskToGoogle {
	
	public ArrayList<RelationLocation>  getDistanceByGoogle(LocationContainer locationConteiner);
	public ArrayList<RelationLocation>  getDistanceByGoogleAlpha(LocationContainerForGoogleAsk locationContainerForGoogle);
	public void getTimeByGoogle();

}
