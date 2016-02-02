package cl.citymovil.route_pro.solver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="depot")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Depot {

	public Depot() {
	};

	public Depot(Depot depot) {
		this.depotId = depot.getDepotId();
		this.location = depot.getLocation();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("depot_id")
	@Column(name="depot_id")
	private long depotId;

	@JsonProperty("due_time")
	@Column(name="due_time")
	private int dueTime;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "location_id")
	private Location location;
	
	@JsonProperty("ready_time")
	@Column(name="ready_time")
	private int readyTime;

	public long getDepotId() {
		return this.depotId;
	}

	public int getDueTime() {
		return dueTime;
	}

	public Location getLocation() {
		return this.location;
	}

	public int getReadyTime() {
		return readyTime;
	}

	public void setDepotId(long depotId) {
		this.depotId = depotId;
	}

	public void setDueTime(int milliDueTime) {
		this.dueTime = milliDueTime;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setReadyTime(int milliReadyTime) {
		this.readyTime = milliReadyTime;
	}
}
