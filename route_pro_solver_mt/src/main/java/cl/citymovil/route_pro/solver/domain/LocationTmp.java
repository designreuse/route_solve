package cl.citymovil.route_pro.solver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="location_tmp")
public class LocationTmp {
	
	public static final Logger log = LoggerFactory.getLogger(Location.class);

	public static Logger getLog() {
		return log;
	}
	
	@JsonProperty("location_tmp_id")
	@Id
	@Column(name="location_tmp_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long locationId;
	
	@JsonProperty("latitude_tmp")
	@Column(name="latitude_tmp")
	private Double latitudeTmp;//Los valores double e int so primitivos y en java no admiten null,
	//para solucionar esto, se hace uso de Double y de Integer, que son clases en java que admiten este tipo de valores.
	
	@JsonProperty("longitude_tmp")
	@Column(name="longitude_tmp")
	private Double longitudeTmp;
	
	@JsonProperty("address_tmp")
	@Column(name="address_tmp")
	private String addressTmp;
	
	@JsonProperty("street_number_tmp")
	@Column(name="street_number_tmp")
	private String streetNumberTmp;
	
	@JsonProperty("route_tmp")
	@Column(name="route_tmp")
	private String routeTmp;
	
	@JsonProperty("administrative_area_level_1_tmp")
	@Column(name="administrative_area_level_1_tmp")
	private String administrativeAreaLevel1Tmp;
	
	@JsonProperty("country_tmp")
	@Column(name="country_tmp")
	private String countryTmp;
	
	@JsonProperty("meterPerHours_tmp")
	@Column(name="meterPerHours_tmp")
	private Integer meterPerHoursTmp;
	
	
	@JsonProperty("locality_tmp")
	@Column(name="locality_tmp")
	private String localityTmp;
	
	public  LocationTmp(){}
	
	public LocationTmp(long locationId) {

		this.locationId=locationId;
	}
	
	public LocationTmp MakeLocationTmpWithLocation(Location location){
		this.latitudeTmp=location.getLatitude();
		this.locationId=location.getLocationId();
		this.longitudeTmp=location.getLongitude();
		
		return null;
		
	}

	public LocationTmp(Double latitude, Double longitude) {

		this.latitudeTmp = latitude;
		this.longitudeTmp = longitude;
	}
	
	public long getLocationId() {
		return locationId;
	}

	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}

	public Double getLatitudeTmp() {
		return latitudeTmp;
	}

	public void setLatitudeTmp(Double latitudeTmp) {
		this.latitudeTmp = latitudeTmp;
	}

	public Double getLongitudeTmp() {
		return longitudeTmp;
	}

	public void setLongitudeTmp(Double longitudeTmp) {
		this.longitudeTmp = longitudeTmp;
	}

	public String getAddressTmp() {
		return addressTmp;
	}

	public void setAddressTmp(String addressTmp) {
		this.addressTmp = addressTmp;
	}

	public String getStreetNumberTmp() {
		return streetNumberTmp;
	}

	public void setStreetNumberTmp(String streetNumberTmp) {
		this.streetNumberTmp = streetNumberTmp;
	}

	public String getRouteTmp() {
		return routeTmp;
	}

	public void setRouteTmp(String routeTmp) {
		this.routeTmp = routeTmp;
	}

	public String getAdministrativeAreaLevel1Tmp() {
		return administrativeAreaLevel1Tmp;
	}

	public void setAdministrativeAreaLevel1Tmp(String administrativeAreaLevel1Tmp) {
		this.administrativeAreaLevel1Tmp = administrativeAreaLevel1Tmp;
	}

	public String getCountryTmp() {
		return countryTmp;
	}

	public void setCountryTmp(String countryTmp) {
		this.countryTmp = countryTmp;
	}

	public Integer getMeterPerHoursTmp() {
		return meterPerHoursTmp;
	}

	public void setMeterPerHoursTmp(Integer meterPerHoursTmp) {
		this.meterPerHoursTmp = meterPerHoursTmp;
	}

	public String getLocalityTmp() {
		return localityTmp;
	}

	public void setLocalityTmp(String localityTmp) {
		this.localityTmp = localityTmp;
	}

}
