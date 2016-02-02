package cl.citymovil.route_pro.solver.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

@Entity
@Table(name = "location")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

	public static final Logger log = LoggerFactory.getLogger(Location.class);

	@Transient
	int metersPerHour = 40000;

	private String address;

	@Transient
	@JsonIgnore
	private Map<Long, Map<Long, DistanceTimeData>> distanceDurationMatrix = new HashMap<Long, Map<Long, DistanceTimeData>>();

	@Column(nullable = true)
	private double latitude;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("location_id")
	@Column(name = "location_id")
	private long locationId;

	@Column(nullable = true)
	private double longitude;

	public Location() {
	}

	public Location(Location location) {
		this.locationId = location.getLocationId();
		this.latitude = location.getLatitude();
		this.longitude = location.getLongitude();
	}

	public String getAddress() {
		return address;
	}

	/**
	 * The angle relative to the direction EAST.
	 * 
	 * @param location
	 *            never null
	 * @return in Cartesian coordinates
	 */
	public double getAngle(Location location) {
		// Euclidean distance (Pythagorean theorem) - not correct when the
		// surface is a sphere
		double latitudeDifference = location.latitude - latitude;
		double longitudeDifference = location.longitude - longitude;
		return Math.atan2(latitudeDifference, longitudeDifference);
	}

	public double getAngleInDegrees(Location location) {

		double atan2 = getAngle(location);
		double atan2InDegrees = atan2 * (180.0 / Math.PI);
		return atan2InDegrees;
	}

	public int getMetersPerHour() {
		return metersPerHour;
	}

	public void setMetersPerHour(int metersPerHour) {
		this.metersPerHour = metersPerHour;
	}

	public static Logger getLog() {
		return log;
	}

	public Map<Long, Map<Long, DistanceTimeData>> getDistanceDurationMatrix() {
		return distanceDurationMatrix;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public long getLocationId() {
		return this.locationId;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public long getMetersFrom(Location location) {
		
		try {

			return ((this.distanceDurationMatrix.get(location.getLocationId())).get(this.getLocationId())).getDistance();

		} catch (Exception e) {

			// System.out.println("MetersFrom "+ location.getLocationId() +
			// " to "+ this.getLocationId()+" value is missing!");

			LatLng pointA = new LatLng(this.latitude, this.longitude);
			LatLng pointB = new LatLng(location.latitude, location.longitude);

			int meters = (int) LatLngTool.distance(pointA, pointB, LengthUnit.METER);

			return meters;
		}
	}

	public long getMetersTo(Location location) {
		return location.getMetersFrom(this);
	}

	@JsonIgnore
	public String getSafeName() {
		return String.valueOf(this.locationId);
	}

	public long getSecondsFrom(Location location) {

		try {

			// log.debug("from "+location.getLocationId()+
			// " to "+this.getLocationId()+" : "+((this.distanceDurationMatrix.get(location.getLocationId()))
			// .get(this.getLocationId())).getDistance());

			return ((this.distanceDurationMatrix.get(location.getLocationId())).get(this.getLocationId())).getTime();

		} catch (Exception e) {

			long meters = getMetersFrom(location);
			long seconds = (meters * 3600) / metersPerHour;

			// long start = location.getLocationId();
			// long end = this.getLocationId();
			//
			// DistanceTimeData distanceDurationData = new
			// DistanceTimeData(meters, seconds);
			//
			// this.getDistanceDurationMatrix().put(start, new HashMap<Long,
			// DistanceTimeData>());
			// (this.getDistanceDurationMatrix().get(start)).put(end,
			// distanceDurationData);
			//
			// log.debug("SecondsFrom "+ location.getLocationId() + " to "+
			// this.getLocationId()+" value is missing!");
			// log.debug("meters: "+ meters);
			// log.debug("segundos: "+ seconds);

			return seconds;
		}

	}

	// public int getMilliDistance(Location location) {
	// LatLng pointA = new LatLng(this.latitude, this.longitude);
	// LatLng pointB = new LatLng(location.latitude, location.longitude);
	//
	// double distance = LatLngTool.distance(pointA, pointB,
	// LengthUnit.KILOMETER);
	//
	// int milliDistance = (int) (distance / this.averageSpeed * 60.0D);
	//
	// return milliDistance;
	// }

	public long getSecondsTo(Location location) {

		return location.getSecondsFrom(this);
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setDistanceDurationMatrix(Map<Long, Map<Long, DistanceTimeData>> distanceDurationMatrix) {
		this.distanceDurationMatrix = distanceDurationMatrix;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String toString() {
		return String.valueOf(this.locationId);
	}
}
