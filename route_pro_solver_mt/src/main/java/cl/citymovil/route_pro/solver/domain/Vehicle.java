package cl.citymovil.route_pro.solver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.CustomShadowVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.citymovil.route_pro.solver.solution.util.VehicleReadyTimeUpdatingVariableListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "vehicle")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehicle implements Standstill, Comparable<Vehicle> {

	private static final Logger logger = LoggerFactory.getLogger(Vehicle.class);

	public Vehicle() {
	}

	public Vehicle(Vehicle vehicle) {
		this.vehicleId = vehicle.getVehicleId();
		this.externalId = vehicle.getExternalId();
		this.driver = vehicle.getDriver();
		this.capacity = vehicle.getCapacity();
		this.readyTime = vehicle.getReadyTime();
		this.dueTime = vehicle.getDueTime();
		this.depot = new Depot(vehicle.getDepot());
	}

	public void setNextVehicle(Vehicle nextVehicle) {
		this.nextVehicle = nextVehicle;
	}

	@Transient
	private Vehicle nextVehicle;
	
	@Transient
	private Integer endOfTrip;

	public Integer getEndOfTrip() {
		return endOfTrip;
	}

	public void setEndOfTrip(Integer endOfTrip) {
		this.endOfTrip = endOfTrip;
	}

	@Transient
	private int sequence;

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public Vehicle getNextVehicle() {
		return this.nextVehicle;
	}

	protected int capacity;

	@Transient
	private int previousReadyTime;

	@Transient
	private Boolean isTrip;

	public Boolean getIsTrip() {
		return isTrip;
	}

	public void setIsTrip(Boolean isTrip) {
		this.isTrip = isTrip;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "depot_id")
	protected Depot depot;

	private String driver;

	@JsonProperty("due_time")
	@Column(name = "due_time")
	protected int dueTime;

	@JsonProperty("external_id")
	@Column(name = "external_id")
	private String externalId;

	@JsonIgnore
	@Transient
	protected Customer nextCustomer;

	@JsonProperty("ready_time")
	@Column(name = "ready_time")
	protected int readyTime;

	// @JsonIgnore
	// @ManyToOne(fetch = FetchType.EAGER)
	// @JoinColumn(name = "vehicleTypeId")
	// private VehicleType vehicleType;

	// @JsonIgnore
	// @Transient
	// protected int returnToDepotTime;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("vehicle_id")
	@Column(name = "vehicle_id")
	private long vehicleId;

	public int compareTo(Vehicle other) {
		return new CompareToBuilder().append(getClass().getName(), other.getClass().getName()).append(this.vehicleId, other.getVehicleId()).toComparison();
	}

	public int getCapacity() {
		return this.capacity;
	}

	public Depot getDepot() {
		return this.depot;
	}

	public String getDriver() {
		return driver;
	}

	public int getDueTime() {
		return dueTime;
	}

	public String getExternalId() {
		return this.externalId;
	}

	@JsonIgnore
	public Location getLocation() {
		return this.depot.getLocation();
	}

	public Customer getNextCustomer() {
		return this.nextCustomer;
	}

	public int getReadyTime() {
		return readyTime;
	}

	@JsonIgnore
	@Transient
	public Vehicle getVehicle() {
		return this;
	}

	public long getVehicleId() {
		return this.vehicleId;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setDepot(Depot depot) {
		this.depot = depot;
	}

	public void setDriver(String salesman) {
		this.driver = salesman;
	}

	public void setDueTime(int endOfShift) {
		this.dueTime = endOfShift;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public void setNextCustomer(Customer nextCustomer) {
		this.nextCustomer = nextCustomer;
	}

	public void setReadyTime(int readyTime) {

		this.readyTime = readyTime;
	}

	public int getPreviousReadyTime() {
		return previousReadyTime;
	}

	public void setPreviousReadyTime(int previousReadyTime) {
		this.previousReadyTime = previousReadyTime;
	}

	public void setVehicleId(long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String toString() {
		return "[vehicle - " + this.vehicleId + "][" + externalId + "]";
	}

}
