package cl.citymovil.route_pro.solver.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.citymovil.route_pro.solver.controllers.SolverController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VRPProblem {

	public VRPProblem() {
	}

	Logger logger = LoggerFactory.getLogger(VRPProblem.class);

	private Date date;

	@JsonProperty("date_string")
	private String dateString;

	private Order[] orders;

	@JsonProperty("seconds_to_spend")
	private int secondsToSpend;

	private Vehicle[] vehicles;

	public VehicleRoutingSolution castToVehicleRoutingSolution(Map<Long, Map<Long, DistanceTimeData>> distanceTimeMatrix) {
		VehicleRoutingSolution vehicleRoutingSolucion = new VehicleRoutingSolution();

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		try {

			date = formatter.parse(this.dateString);
			System.out.println(date);
			System.out.println(formatter.format(date));
			vehicleRoutingSolucion.setDate(date);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		Map<String, Customer> customersMap = new HashMap<String, Customer>();

		for (Order order : orders) {

			Customer customer;

			if (customersMap.get(order.getCustomer().getExternalId()) == null) {
				customersMap.put(order.getCustomer().getExternalId(), order.getCustomer());
				vehicleRoutingSolucion.getCustomerList().add(customersMap.get(order.getCustomer().getExternalId()));
			}

			customer = customersMap.get(order.getCustomer().getExternalId());

			customer.addDemand(order.getDemand());
			customer.addOrder(order);

			Location location = customer.getLocation();
			location.setDistanceDurationMatrix(distanceTimeMatrix);
			vehicleRoutingSolucion.getLocationList().add(location);
		}

		vehicleRoutingSolucion.setVehicleList(Arrays.asList(this.getVehicles()));

		// pruebas multiples trips

		Vehicle v1 = null;
		Vehicle v2 = null;
		Vehicle v3 = null;
		Vehicle v4 = null;
		Vehicle v5 = null;
		Vehicle v6 = null;

		for (Vehicle vehicle : vehicleRoutingSolucion.getVehicleList()) {
			if (vehicle.getExternalId().equalsIgnoreCase("FFFL-60")) {
				logger.debug("found 1");
				v1 = vehicle;
			} else if (vehicle.getExternalId().equalsIgnoreCase("FFFL-61")) {
				logger.debug("found 2");
				v2 = vehicle;
			} else if (vehicle.getExternalId().equalsIgnoreCase("FFFL-62")) {
				logger.debug("found 3");
				v3 = vehicle;
			} else if (vehicle.getExternalId().equalsIgnoreCase("FFFL-63")) {
				logger.debug("found 4");
				v4 = vehicle;
			} else if (vehicle.getExternalId().equalsIgnoreCase("FFFL-64")) {
				logger.debug("found 5");
				v5 = vehicle;
			} else if (vehicle.getExternalId().equalsIgnoreCase("FDRC-25")) {
				logger.debug("found 6");
				v6 = vehicle;
			}

		}

		v1.setNextVehicle(v2);
		v2.setNextVehicle(v3);
		v3.setNextVehicle(v4);
		v4.setNextVehicle(v5);
		v5.setNextVehicle(v6);
		


		// vehicleRoutingSolucion.getVehicleList().remove(actualVehicle);

		for (Vehicle vehicle : vehicleRoutingSolucion.getVehicleList()) {
			vehicle.setPreviousReadyTime(vehicle.getReadyTime());
			vehicle.setIsTrip(false);
		}
		
		v1.setIsTrip(true);
		v2.setIsTrip(true);
		v3.setIsTrip(true);
		v4.setIsTrip(true);
		v5.setIsTrip(true);
		v6.setIsTrip(true);


		for (Vehicle vehicle : vehicleRoutingSolucion.getVehicleList()) {
			logger.debug(vehicle.getExternalId() + " => " + ((vehicle.getNextVehicle() != null) ? vehicle.getNextVehicle().getExternalId() : "null"));
		}

		/*
		 * for (Customer customer : vehicleRoutingSolucion.getCustomerList()) {
		 * System.out.println("orders.size(): "+customer.getOrders().size());
		 * 
		 * }
		 */

		/*
		 * Location location = new Location(); location.setLocationId(1);
		 * location.setLatitude(-33.41606800);
		 * location.setLongitude(-70.60088420);
		 * 
		 * Depot depot = new Depot(); depot.setDepotId(1);
		 * depot.setLocation(location); depot.setReadyTime(1);
		 * depot.setDueTime(100000);
		 * 
		 * for(Vehicle vehicle : vehicleRoutingSolucion.getVehicleList()) {
		 * vehicle.setDepot(depot); }
		 */

		Map<Long, Depot> depotsHashMap = new HashMap<Long, Depot>();
		for (Vehicle vehicle : vehicleRoutingSolucion.getVehicleList()) {
			if (!depotsHashMap.containsKey(vehicle.getDepot().getDepotId())) {
				depotsHashMap.put(vehicle.getDepot().getDepotId(), vehicle.getDepot());
			}
		}

		vehicleRoutingSolucion.getDepotList().addAll(depotsHashMap.values());

		return vehicleRoutingSolucion;
	}

	public Date getDate() {
		return date;
	}

	public String getDateString() {
		return dateString;
	}

	public Order[] getOrders() {
		return orders;
	}

	public int getSecondsToSpend() {
		return secondsToSpend;
	}

	public Vehicle[] getVehicles() {
		return vehicles;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public void setOrders(Order[] orders) {
		this.orders = orders;
	}

	public void setSecondsToSpend(int secondsToSpend) {
		this.secondsToSpend = secondsToSpend;
	}

	public void setVehicles(Vehicle[] vehicles) {
		this.vehicles = vehicles;
	}

}
