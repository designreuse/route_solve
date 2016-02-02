package cl.citymovil.route_pro.solver.util;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cl.citymovil.route_pro.solver.domain.Location;



@Service
public class LocationContainerForGoogleAsk {
	
	public static final Logger log = LoggerFactory.getLogger(Location.class);

	public static Logger getLog() {
		return log;
	}
	private ArrayList<Location> locationOrigin;
	private ArrayList<Location> locationDestiny;

	public LocationContainerForGoogleAsk(){	}
	
public LocationContainerForGoogleAsk(ArrayList<Location> locationOrigin, ArrayList<Location> locationDestiny){
	this.locationOrigin = locationOrigin;
	this.locationDestiny = locationDestiny;
}

	public ArrayList<Location> getLocationOrigin() {
		return locationOrigin;
	}
	public void setLocationOrigin(ArrayList<Location> locationOrigin) {
		this.locationOrigin = locationOrigin;
	}
	public ArrayList<Location> getLocationDestiny() {
		return locationDestiny;
	}
	public void setLocationDestiny(ArrayList<Location> locationDestiny) {
		this.locationDestiny = locationDestiny;
	}

}
