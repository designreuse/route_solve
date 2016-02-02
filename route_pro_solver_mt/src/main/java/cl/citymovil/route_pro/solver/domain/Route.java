package cl.citymovil.route_pro.solver.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="route")
public class Route {

	public Route() {
	}

	@OneToMany(mappedBy = "route")
	@Cascade({CascadeType.PERSIST, CascadeType.MERGE})
	@JsonProperty("customers_in_route")
	protected List<ScheduledCustomer> scheduledCustomers = new ArrayList<ScheduledCustomer>();

	@JsonProperty("pdf_location")
	@Column(name="pdf_location")
	private String pdfLocation;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="route_id")
	@JsonProperty("route_id")
	private long routeId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "schedule_id")
	@JsonIgnore
	protected Schedule schedule;

	@JsonProperty("total_customers_in_route")
	@Column(name="total_customers_in_route")
	private int totalCustomersInRoute;

	@JsonProperty("total_kilograms")
	@Column(name="total_kilograms")
	private float totalKilograms;

	@JsonProperty("total_meters")
	@Column(name="total_meters")
	private int totalMeters;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "vehicle_id")
	protected Vehicle vehicle;

	public List<ScheduledCustomer> getScheduledCustomers() {
		return scheduledCustomers;
	}

	public String getPdfLocation() {
		return pdfLocation;
	}

	public long getRouteId() {
		return routeId;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public int getTotalCustomersInRoute() {
		return totalCustomersInRoute;
	}

	public float getTotalKilograms() {
		return totalKilograms;
	}

	public int getTotalMeters() {
		return totalMeters;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setScheduledCustomers(List<ScheduledCustomer> scheduledCustomers) {
		this.scheduledCustomers = scheduledCustomers;
	}

	public void setPdfLocation(String pdfLocation) {
		this.pdfLocation = pdfLocation;
	}

	public void setRouteId(long routeId) {
		this.routeId = routeId;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public void setTotalCustomersInRoute(int totalCustomersInRoute) {
		this.totalCustomersInRoute = totalCustomersInRoute;
	}

	public void setTotalKilograms(float totalKilograms) {
		this.totalKilograms = totalKilograms;
	}

	public void setTotalMeters(int totalMeters) {
		this.totalMeters = totalMeters;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
}
