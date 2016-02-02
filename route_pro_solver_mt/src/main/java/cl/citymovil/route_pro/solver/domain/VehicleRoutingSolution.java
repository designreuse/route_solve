package cl.citymovil.route_pro.solver.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

@PlanningSolution
public class VehicleRoutingSolution implements
		Solution<HardSoftScore> {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;

	protected String name;
	protected List<Location> locationList = new ArrayList<Location>();
	protected List<Depot> depotList = new ArrayList<Depot>();
	protected List<Vehicle> vehicleList = new ArrayList<Vehicle>();
	protected List<Customer> customerList = new ArrayList<Customer>();
	protected Date date;

//	@XStreamConverter(value = XStreamScoreConverter.class, types = {HardSoftScoreDefinition.class})
	protected HardSoftScore score;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Location> getLocationList() {
		return this.locationList;
	}

	public void setLocationList(List<Location> locationList) {
		this.locationList = locationList;
	}

	public List<Depot> getDepotList() {
		return this.depotList;
	}

	public void setDepotList(List<Depot> depotList) {
		this.depotList = depotList;
	}

	@PlanningEntityCollectionProperty
	@ValueRangeProvider(id = "vehicleRange")
	public List<Vehicle> getVehicleList() {
		return this.vehicleList;
	}

	public void setVehicleList(List<Vehicle> vehicleList) {
		this.vehicleList = vehicleList;
	}

	@PlanningEntityCollectionProperty
	@ValueRangeProvider(id = "customerRange")
	public List<Customer> getCustomerList() {
		return this.customerList;
	}

	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public HardSoftScore getScore() {
		return this.score;
	}

	public void setScore(HardSoftScore score) {
		this.score = score;
	}

	public Collection<? extends Object> getProblemFacts() {
		List facts = new ArrayList();
		facts.addAll(this.locationList);
		facts.addAll(this.depotList);

		return facts;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if ((this.id == null) || (!(o instanceof VehicleRoutingSolution))) {
			return false;
		}
		VehicleRoutingSolution other = (VehicleRoutingSolution) o;
		if (this.customerList.size() != other.customerList.size()) {
			return false;
		}
		Iterator it = this.customerList.iterator();
		for (Iterator otherIt = other.customerList.iterator(); it.hasNext();) {
			Customer customer = (Customer) it.next();
			Customer otherCustomer = (Customer) otherIt.next();

			if (!customer.solutionEquals(otherCustomer)) {
				return false;
			}
		}
		return true;
	}

	public int hashCode() {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
		for (Customer customer : this.customerList) {
			hashCodeBuilder.append(customer.solutionHashCode());
		}
		return hashCodeBuilder.toHashCode();
	}
}
