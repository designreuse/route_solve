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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.AnchorShadowVariable;
import org.optaplanner.core.api.domain.variable.CustomShadowVariable;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.core.api.domain.variable.PlanningVariableGraphType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.citymovil.route_pro.solver.solution.util.ArrivalTimeUpdatingVariableListener;
import cl.citymovil.route_pro.solver.solution.util.DepotAngleCustomerDifficultyWeightFactory;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@Table(name="customer")
@PlanningEntity(difficultyWeightFactoryClass = DepotAngleCustomerDifficultyWeightFactory.class)
// @PlanningEntity(difficultyWeightFactoryClass =
// DepotDistanceCustomerDifficultyWeightFactory.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(scope = Customer.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "external_id")
public class Customer implements Standstill {

	@Transient
	Logger logger = LoggerFactory.getLogger(Customer.class);

	
	public Customer() {
	}
	
	@JsonProperty("arrival_time")
	@Column(name="arrival_time")
	private Integer arrivalTime;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("customer_id")
	@Column(name="customer_id")
	private int customerId;

	@JsonProperty("total_demand")
	protected float demand = 0;

	@JsonProperty("due_time")
	@Column(name="due_time")
	private Integer dueTime;

	@JsonProperty("external_id")
	@Column(name="external_id")
	private String externalId;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "location_id")
	protected Location location;

	protected String name;

	@Transient
	@JsonIgnore
	protected Customer nextCustomer;

	@OneToMany(mappedBy="customer", fetch = FetchType.LAZY)
	protected List<Order> orders = new ArrayList<Order>();

	@Transient
	@JsonIgnore
	protected Standstill previousStandstill;

	@JsonProperty("ready_time")
	@Column(name="ready_time")
	private Integer readyTime;

	private int sequence;

	@JsonProperty("service_duration")
	@Column(name="service_duration")
	private Integer serviceDuration;

	@Transient
	@JsonIgnore
	protected Vehicle vehicle;
	
	@Transient
	@JsonIgnore
	public int getAngleDifferenceToNextCustomer()
	{
		if(this.getNextCustomer()!=null)
		{
			Location depotLocation = this.getVehicle().getDepot().getLocation();
			double angle1 = this.location.getAngleInDegrees(depotLocation);
			double angle2 = this.getNextCustomer().getLocation().getAngleInDegrees(depotLocation);
			
			double angleDifference = Math.abs(angle2-angle1);
			return (int) angleDifference;
		}
		return 0;
	}
	

	
	
//	public boolean test()
//	{
//		System.out.println("hey!");
//		
//		if(this.getNextCustomer()!=null)
//		{
//			Location depotLocation = this.getVehicle().getDepot().getLocation();
//			double angle1 = this.location.getAngleInDegrees(depotLocation);
//			double angle2 = this.getNextCustomer().getLocation().getAngleInDegrees(depotLocation);
//			
//			double angleDifference = Math.abs(angle2-angle1);
//			
//			System.out.println("angleDifference: "+ (int)angleDifference);
//			
//			if(angleDifference>135)
//			{
//				return true;
//			}
//			return false;
//			
//		}
//		return false;
//		
//	}
	

	public void addDemand(float demand) {
		this.demand += demand;
	}
	
	public void addOrder(Order order) {
		this.orders.add(order);
	}

	@CustomShadowVariable(variableListenerClass = ArrivalTimeUpdatingVariableListener.class, sources = { @CustomShadowVariable.Source(variableName = "previousStandstill") })
	public Integer getArrivalTime() {
		return arrivalTime;
	}

	public int getCustomerId() {
		return customerId;
	}

	public float getDemand() {
		return demand;
	}

	@JsonProperty("departure_time")
	public Integer getDepartureTime() {
		if (arrivalTime == null) {
			return null;
		}
		return Integer.valueOf(Math.max(this.arrivalTime.intValue(), this.readyTime) + this.serviceDuration);
	}

	public Integer getDueTime() {
		return dueTime;
	}

	public String getExternalId() {
		return externalId;
	}

//	@JsonIgnore
	public Location getLocation() {
		return this.location;
	}

	@JsonProperty("meters_from_previous")
	public long getMetersFromPreviousStandstill() {
		if (this.previousStandstill == null) {
			return 0;
		}
		return this.location.getMetersFrom(this.previousStandstill.getLocation());
	}

	@JsonIgnore
	public long getMetersTo(Standstill standstill) {
		return this.location.getMetersTo(standstill.getLocation());
	}

	@JsonIgnore
	public long getMetersToNextCustomer() {
		if (this.getNextCustomer() == null) {
			return 0;
		}
		return this.location.getMetersTo(this.getNextCustomer().getLocation());
	}

	public String getName() {
		return name;
	}

	@JsonIgnore
	public Customer getNextCustomer() {
		return this.nextCustomer;
	}

	public List<Order> getOrders() {
		return orders;
	}

	@PlanningVariable(valueRangeProviderRefs = { "vehicleRange", "customerRange" }, graphType = PlanningVariableGraphType.CHAINED)
	public Standstill getPreviousStandstill() {
		return this.previousStandstill;
	}

	public Integer getReadyTime() {
		return readyTime;
	}

	@JsonProperty("seconds_from_previous")
	public long getSecondsFromPreviousStandstill() {
		if (this.previousStandstill == null) {
			return 0;
		}
		return this.location.getSecondsFrom(this.previousStandstill.getLocation());
	}

	// public List<Order> getOrders() {
	// return orders;
	// }

	public long getSecondsTo(Standstill standstill) {
		return this.location.getSecondsTo(standstill.getLocation());
	}

	@JsonIgnore
	public long getSecondsToNextCustomer() {
		if (this.getNextCustomer() == null) {
			return 0;
		}
		return this.location.getSecondsTo(this.getNextCustomer().getLocation());
	}

	public int getSequence() {
		return sequence;
	}

	public Integer getServiceDuration() {
		return serviceDuration;
	}

	@AnchorShadowVariable(sourceVariableName = "previousStandstill")
	public Vehicle getVehicle() {
		return this.vehicle;
	}
	
	public Vehicle getVehicleRecursive()
	{
		if(this.vehicle!=null)
		{
			return this.vehicle;
		}
		else if(this.previousStandstill instanceof Vehicle)
		{
			return (Vehicle)this.previousStandstill;
		}
		else if (this.previousStandstill instanceof Customer)
		{
			return ((Customer)this.previousStandstill).getVehicleRecursive();
		}
		else
		{
//			logger.debug("previous = null");
			return null;
		}
			
	}

	@JsonProperty("is_arrival_after_due_time")
	public boolean isArrivalAfterDueTime() {
		return (this.arrivalTime != null) && (this.dueTime < this.arrivalTime.intValue());
	}

	@JsonProperty("is_arrival_before_ready_time")
	public boolean isArrivalBeforeReadyTime() {
		return (this.arrivalTime != null) && (this.arrivalTime.intValue() < this.readyTime);
	}

	public void setArrivalTime(Integer arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public void setDemand(float demand) {
		this.demand = demand;
	}

	public void setDueTime(Integer dueTime) {
		this.dueTime = dueTime;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	@JsonProperty
	public void setLocation(Location location) {
		this.location = location;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNextCustomer(Customer nextCustomer) {
		this.nextCustomer = nextCustomer;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public void setPreviousStandstill(Standstill previousStandstill) {
		this.previousStandstill = previousStandstill;
	}

	public void setReadyTime(Integer readyTime) {
		this.readyTime = readyTime;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public void setServiceDuration(Integer serviceDuration) {
		this.serviceDuration = serviceDuration;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public boolean solutionEquals(Object o) {
		if (this == o)
			return true;
		if ((o instanceof Customer)) {
			Customer other = (Customer) o;
			return new EqualsBuilder().append(this.externalId, other.getExternalId())
			// .append(this.location, other.location)
			// .append(this.previousStandstill, other.previousStandstill)
					.isEquals();
		}
		return false;
	}

	public int solutionHashCode() {
		return new HashCodeBuilder().append(this.externalId).append(this.location).append(this.previousStandstill).toHashCode();
	}

	public String toString() {
		// return ToStringBuilder.reflectionToString(this,
		// ToStringStyle.MULTI_LINE_STYLE);
		
		String text =""; 
		if(this.previousStandstill instanceof Vehicle)
			text = this.previousStandstill.toString();
		else if(this.previousStandstill instanceof Customer)
		{
			text = this.previousStandstill.getLocation().toString();
		}
		else
		{
			text = "null";
		}
		
		return this.location + "(after " + text + ")";
	}

}
