package cl.citymovil.route_pro.solver.domain;

public class DistanceTimeData {

	public long distance;
	public long time;
	
	public DistanceTimeData(long distance, long duration)
	{
		this.setDistance(distance);
		this.setTime(duration);
	}
	
	public long getDistance() {
		return distance;
	}
	public void setDistance(long distance) {
		this.distance = distance;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long duration) {
		this.time = duration;
	}
	
}
