package cl.citymovil.route_pro.solver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name="distance_time_matrix")
public class DistanceTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("distance_time_matrix_id")
	@Column(name="distance_time_matrix_id")
	private String distanceTimeMatrixId;

	private long origin;
	private long destination;
	private long distance;
	private long duration;
	
	public DistanceTime(){}

	public DistanceTime(long origin, long destination, long distance, long duration)
	{
		this.origin = origin;
		this.destination = destination;
		this.distance = distance;
		this.duration = duration;
	}

	public String getDistanceTimeMatrixId() {
		return distanceTimeMatrixId;
	}

	public void setDistanceTimeMatrixId(String startend) {
		this.distanceTimeMatrixId = startend;
	}

	public long getOrigin() {
		return origin;
	}

	public void setOrigin(long origin) {
		this.origin = origin;
	}

	public long getDestination() {
		return destination;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}

	public long getDistance() {
		return distance;
	}

	public void setDistance(long distance) {
		this.distance = distance;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}
}
