package cl.citymovil.route_pro.solver.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "`order`")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(scope = Order.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "external_id")
public class Order {

	public Order() {
	}

	public Order(Order order) {
		this.date = order.getDate();
		this.demand = order.getDemand();
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "customer_id")
	// @JsonBackReference
	protected Customer customer;

	private Date date;

	private float demand;

	// @ManyToOne(fetch = FetchType.EAGER)
	// @JoinColumn(name = "vehicleId")
	// protected Vehicle vehicle;

	@JsonProperty("external_id")
	@Column(name = "external_id")
	private String externalId;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	protected long orderId;

	@ManyToOne(fetch = FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "scheduled_customer_id")
	protected ScheduledCustomer scheduledCustomer;

	public Customer getCustomer() {
		return customer;
	}

	public Date getDate() {
		return date;
	}

	public float getDemand() {
		return demand;
	}

	public String getExternalId() {
		return externalId;
	}

	public long getOrderId() {
		return orderId;
	}

	public ScheduledCustomer getScheduledCustomer() {
		return scheduledCustomer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	// public Vehicle getVehicle() {
	// return vehicle;
	// }
	//
	// public void setVehicle(Vehicle vehicle) {
	// this.vehicle = vehicle;
	// }

	public void setDemand(float demand) {
		this.demand = demand;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;

	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public void setScheduledCustomer(ScheduledCustomer scheduledCustomer) {
		this.scheduledCustomer = scheduledCustomer;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
