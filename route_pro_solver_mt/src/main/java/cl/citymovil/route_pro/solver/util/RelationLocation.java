package cl.citymovil.route_pro.solver.util;

import org.springframework.stereotype.Service;


@Service
public class RelationLocation {
	
	private Double[] firstLocation=new Double[2];
	private Double[] secondLocation=new Double[2];
	private Long idFirstLocation;
	private Long idSecondLocation;
	private Double goingDistance;
	private Double comingDistance;
	private Double goingDuration;
	private Double comingDuration;

	public RelationLocation(){}


	public Double[] getFirstLocation() {
		return firstLocation;
	}


	public void setFirstLocation(Double[] firstLocation) {
		this.firstLocation = firstLocation;
	}


	public Double[] getSecondLocation() {
		return secondLocation;
	}


	public void setSecondLocation(Double[] secondLocation) {
		this.secondLocation = secondLocation;
	}




	public Long getIdFirstLocation() {
		return idFirstLocation;
	}


	public void setIdFirstLocation(Long idFirstLocation) {
		this.idFirstLocation = idFirstLocation;
	}


	public Long getIdSecondLocation() {
		return idSecondLocation;
	}


	public void setIdSecondLocation(Long idSecondLocation) {
		this.idSecondLocation = idSecondLocation;
	}


	public Double getGoingDistance() {
		return goingDistance;
	}
	public void setGoingDistance(Double goingDistance) {
		this.goingDistance = goingDistance;
	}
	public Double getComingDistance() {
		return comingDistance;
	}
	public void setComingDistance(Double comingDistance) {
		this.comingDistance = comingDistance;
	}
	public Double getGoingDuration() {
		return goingDuration;
	}
	public void setGoingDuration(Double goingDuration) {
		this.goingDuration = goingDuration;
	}
	public Double getComingDuration() {
		return comingDuration;
	}
	public void setComingDuration(Double comingDuration) {
		this.comingDuration = comingDuration;
	}
	

}
