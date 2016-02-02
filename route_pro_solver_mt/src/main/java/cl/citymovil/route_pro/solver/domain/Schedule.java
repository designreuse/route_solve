package cl.citymovil.route_pro.solver.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "`schedule`")
public class Schedule {

	private int active;

	private Date created;
	private Date date;

	private Date finished;

	@JsonProperty("hard_score")
	@Column(name = "hard_score")
	private int hardScore;

	@JsonProperty("is_feasible")
	@Column(name = "is_feasible")
	private boolean isFeasible;

	@JsonProperty("milliseconds_spent")
	@Column(name = "milliseconds_spent")
	private int millisecondsSpent;

	@JsonProperty("pdf_location")
	@Column(name = "pdf_location")
	private String pdfLocation;
	@JsonProperty("routes")
	@OneToMany(mappedBy = "schedule", fetch = FetchType.EAGER)
	@Cascade({CascadeType.PERSIST, CascadeType.MERGE})
	private List<Route> routes = new ArrayList<Route>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "schedule_id")
	@JsonProperty("schedule_id")
	private long scheduleId;

	@JsonProperty("seconds_to_spend")
	@Column(name = "seconds_to_spend")
	private long secondsToSpend;

	@JsonProperty("soft_score")
	@Column(name = "soft_score")
	private int softScore;

	@JsonProperty("total_vehicles")
	@Column(name = "total_vehicles")
	private int totalVehicles;

	@JsonProperty("used_vehicles")
	@Column(name = "used_vehicles")
	private int usedVehicles;

	public int getActive() {
		return active;
	}

	public Date getCreated() {
		return created;
	}

	public Date getDate() {
		return date;
	}

	@JsonProperty("date_string")
	public String getDateString() {

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		if (this.date != null) {
			String dateString = formatter.format(this.date);
			return (dateString);
		}
		return "";

	}

	public Date getFinished() {
		return finished;
	}

	public int getHardScore() {
		return hardScore;
	}

	public boolean getIsFeasible() {
		return isFeasible;
	}

	public int getMillisecondsSpent() {
		return millisecondsSpent;
	}

	public String getPdfLocation() {
		return pdfLocation;
	}

	public List<Route> getRoutes() {
		return routes;
	}

	public long getScheduleId() {
		return scheduleId;
	}

	public long getSecondsToSpend() {
		return secondsToSpend;
	}

	public int getSoftScore() {
		return softScore;
	}

	public int getTotalVehicles() {
		return totalVehicles;
	}

	public int getUsedVehicles() {
		return usedVehicles;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setFeasible(boolean isFeasible) {
		this.isFeasible = isFeasible;
	}

	public void setFinished(Date finished) {
		this.finished = finished;
	}

	public void setHardScore(int hardScore) {
		this.hardScore = hardScore;
	}

	public void setIsFeasible(boolean isFeasible) {
		this.isFeasible = isFeasible;
	}

	public void setMillisecondsSpent(int millisecondsSpent) {
		this.millisecondsSpent = millisecondsSpent;
	}

	public void setPdfLocation(String pdfLocation) {
		this.pdfLocation = pdfLocation;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}

	public void setScheduleId(long scheduleId) {
		this.scheduleId = scheduleId;
	}

	public void setSecondsToSpend(long secondsToSpend) {
		this.secondsToSpend = secondsToSpend;
	}

	public void setSoftScore(int softscore) {
		this.softScore = softscore;
	}

	public void setTotalVehicles(int totalVehicles) {
		this.totalVehicles = totalVehicles;
	}

	public void setUsedVehicles(int usedVehicles) {
		this.usedVehicles = usedVehicles;
	}
}
