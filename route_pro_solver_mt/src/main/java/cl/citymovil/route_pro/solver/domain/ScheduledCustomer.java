package cl.citymovil.route_pro.solver.domain;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "scheduled_customer")
public class ScheduledCustomer implements Comparable<ScheduledCustomer> {

	public ScheduledCustomer() {
	}

	public ScheduledCustomer(Customer customer) {
		this.name = customer.getName();
		this.location = new Location(customer.getLocation());
		this.totalDemand = customer.demand;
		this.readyTime = customer.getReadyTime();
		this.dueTime = customer.getDueTime();
		this.serviceDuration = customer.getServiceDuration();
		this.arrivalTime = customer.getArrivalTime();
		this.externalId = customer.getExternalId();
	}

	@JsonProperty("arrival_time")
	@Column(name = "arrival_time")
	private Integer arrivalTime;

	@JsonProperty("due_time")
	@Column(name = "due_time")
	private Integer dueTime;

	@JsonProperty("external_id")
	@Column(name = "external_id")
	private String externalId;

	@JsonProperty("img_location")
	@Column(name = "img_location")
	private String imgLocation;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "location_id")
	protected Location location;

	@Column(nullable = false)
	protected String name;

	@OneToMany(mappedBy = "scheduledCustomer", fetch = FetchType.LAZY)
	@Cascade({CascadeType.PERSIST, CascadeType.MERGE})
	protected List<Order> orders = new ArrayList<Order>();

	@JsonProperty("ready_time")
	@Column(name = "ready_time")
	private Integer readyTime;

	@JsonProperty("real_arrival_time")
	@Column(name = "real_arrival_time")
	private Date realArrivalTime;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "route_id")
	private Route route;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "scheduled_customer_id")
	protected long scheduledCustomerId;

	private int sequence;

	@JsonProperty("service_duration")
	@Column(name = "service_duration")
	private Integer serviceDuration;

	@JsonProperty("total_demand")
	@Column(name = "total_demand")
	protected float totalDemand;

	public int compareTo(ScheduledCustomer other) {

		return this.sequence - other.getSequence();
	}

	public Integer getArrivalTime() {
		return arrivalTime;
	}

	public Integer getDueTime() {
		return dueTime;
	}

	public String getExternalId() {
		return externalId;
	}

	public String getImgLocation() {
		return imgLocation;
	}

	public Location getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public Integer getReadyTime() {
		return readyTime;
	}

	public Date getRealArrivalTime() {
		return realArrivalTime;
	}

	public Route getRoute() {
		return route;
	}

	public long getScheduledCustomerId() {
		return scheduledCustomerId;
	}

	public int getSequence() {
		return sequence;
	}

	public Integer getServiceDuration() {
		return serviceDuration;
	}

	public float getTotalDemand() {
		return totalDemand;
	}

	public void setArrivalTime(Integer arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public void setDueTime(Integer dueTime) {
		this.dueTime = dueTime;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public void setImgLocation(String imgLocation) {
		this.imgLocation = imgLocation;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public void setReadyTime(Integer readyTime) {
		this.readyTime = readyTime;
	}

	public void setRealArrivalTime(Date realArrivalTime) {
		this.realArrivalTime = realArrivalTime;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public void setScheduledCustomerId(long scheduledCustomerId) {
		this.scheduledCustomerId = scheduledCustomerId;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public void setServiceDuration(Integer serviceDuration) {
		this.serviceDuration = serviceDuration;
	}

	public void setTotalDemand(float totalDemand) {
		this.totalDemand = totalDemand;
	}

}
