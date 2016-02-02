package cl.citymovil.route_pro.solver.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cl.citymovil.route_pro.solver.domain.DistanceTime;
import cl.citymovil.route_pro.solver.domain.DistanceTimeData;

@Component
@Scope("singleton")
public class VehicleRoutingFactsImporter {

	private static final Logger log = LoggerFactory.getLogger(VehicleRoutingFactsImporter.class);

	@Autowired
	DataBaseService databaseService;

	// private int maxCapacity=5000;

	Map<Long, Map<Long, DistanceTimeData>> distanceTimeDataMatrix = new HashMap<Long, Map<Long, DistanceTimeData>>(1000);

	@PostConstruct
	public void loadDistanceTimeData() {
		List<DistanceTime> distanceTimeDateList;// = new ArrayList<DistanceTime>();

		distanceTimeDateList = databaseService.getDistancesAndDurations();
		for (DistanceTime distanceDuration : distanceTimeDateList) {

			// log.debug(ToStringBuilder.reflectionToString(distanceDuration,
			// ToStringStyle.MULTI_LINE_STYLE));

			DistanceTimeData distanceDurationData = new DistanceTimeData(distanceDuration.getDistance(), distanceDuration.getDuration());
			long origin = distanceDuration.getOrigin();

			long destination = distanceDuration.getDestination();

			// log.debug("start: "+start);
			// log.debug("end: "+end);
			// log.debug(ToStringBuilder.reflectionToString(distanceDurationData,
			// ToStringStyle.MULTI_LINE_STYLE));

			if (distanceTimeDataMatrix.get(origin) == null) {
				distanceTimeDataMatrix.put(origin, new HashMap<Long, DistanceTimeData>());
			}
			(distanceTimeDataMatrix.get(origin)).put(destination, distanceDurationData);
		}
		log.debug("distanceTimeDataMatrix keys: " + distanceTimeDataMatrix.keySet());
		log.debug("distanceTimeDataMatrix size: " + distanceTimeDataMatrix.size());

	}

	public Map<Long, Map<Long, DistanceTimeData>> getDistanceTimeDataMatrix() {
		return this.distanceTimeDataMatrix;
	}

	// public VehicleRoutingSolution importFromDatabase(Date date, VehicleType
	// vehicleType) {
	//
	// List<Order> orders = new ArrayList<Order>();
	// Map<String, Customer> customers = new HashMap<String, Customer>();
	// List<Vehicle> vehicles;
	// List<Location> locations = new ArrayList<Location>();
	// List<Depot> depots = new ArrayList<Depot>();
	//
	// String key;
	// orders = databaseService.getOrdersByDateAndOrderType(date, new
	// OrderType(vehicleType.getVehicleTypeId()));
	//
	// for (Order order: orders) {
	// key = String.valueOf(order.getCustomer().getCustomerId());
	// if (customers.get(order.getCustomer().getCustomerId()) == null) {
	// customers.put(key, order.getCustomer());
	// }
	// customers.get(key).addOrder(order);
	// }
	//
	// List<Customer> customersToIterate = new
	// ArrayList<Customer>(customers.values());
	//
	// customers.clear();
	//
	// for(Customer customer : customersToIterate)
	// {
	// if(customer.getDemand()>maxCapacity)
	// {
	// List<Customer> fixedCustomers = this.fixDemand((TimeWindowedCustomer)
	// customer);
	// for(Customer fixedCustomer : fixedCustomers)
	// {
	// key = fixedCustomer.getCustomerId()+"_"+fixedCustomer.getTrip();
	// customers.put(key, fixedCustomer);
	// }
	// }
	// else
	// {
	// key = customer.getCustomerId()+"_"+customer.getTrip();
	// customers.put(key, customer);
	// }
	// customer.getLocation().setDistanceDurationMatrix(distanceDurationMatrix);
	// locations.add(customer.getLocation());
	// }
	//
	//
	// vehicles = this.databaseService.getVehiclesByType(new
	// VehicleType(vehicleType.getVehicleTypeId()));
	//
	// depots.add(vehicles.get(0).getDepot());
	//
	// VehicleRoutingSolution schedule = new VehicleRoutingSolution();
	// schedule.setId(Long.valueOf(0L));
	// schedule.setName("name");
	//
	// schedule.setLocationList(locations);
	// schedule.setCustomerList(new ArrayList<Customer>(customers.values()));
	// schedule.setDepotList(depots);
	// schedule.setVehicleList(vehicles);
	// schedule.setDate(date);
	//
	//
	//
	// log.debug("---------customers size: "+customersToIterate.size());
	// for (Customer customer : customers.values())
	// {
	// if(customer.getDemand()>maxCapacity)
	// log.debug("----------------customer "+customer.getCustomerId()+" demand: "+
	// customer.getDemand());
	// }
	//
	// // for(Location location : locations)
	// // {
	// // log.debug("---------location "+location.getAddress());
	// // }
	// // for (Vehicle vehicle : vehicles) {
	// //
	// log.debug("---------vehicle "+vehicle.getVehicleClientIdentifier()+" capacity: "+vehicle.getCapacity());
	// // }
	// // for (Depot depot: depots) {
	// // log.debug("---------depot "+depot.getDepotId());
	// // }
	//
	//
	//
	// ///////////////////////////////////////////////////////////////////////////////////
	// ////Código
	// original////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////////
	//
	//
	// // List<Customer> customers;
	// // List<Vehicle> vehicles;
	// // List<Location> locations = new ArrayList<Location>();
	// // List<Depot> depots = new ArrayList<Depot>();
	// //
	// // customers = new
	// ArrayList<Customer>(this.databaseService.getTimeWindowedCustomers());
	// // for (Customer customer : customers) {
	// // locations.add(customer.getLocation());
	// // }
	// //
	// //
	// // vehicles = this.databaseService.getVehicles();
	// // for (Vehicle vehicle : vehicles) {
	// // depots.add(vehicle.getDepot());
	// // }
	// //
	// //
	// // TimeWindowedVehicleRoutingSolution schedule = new
	// TimeWindowedVehicleRoutingSolution();
	// // schedule.setId(Long.valueOf(0L));
	// // schedule.setName("name");
	// //
	// // schedule.setLocationList(locations);
	// // schedule.setCustomerList(customers);
	// // schedule.setDepotList(depots);
	// // schedule.setVehicleList(vehicles);
	//
	// // for(Vehicle vehicle : vehicles)
	// // {
	// //
	// log.debug("----------------vehicle "+vehicle.getVehicleClientIdentifier()+" capacity: "+
	// vehicle.getCapacity());
	// // }
	// //
	// //
	// // for (Customer customer : customers) {
	// //
	// log.debug("----------------customer "+customer.getCustomerId()+" demand: "+
	// customer.getDemand());
	// // }
	//
	//
	//
	// return schedule;
	// }

	// public VehicleRoutingSolution importFromDatabase(Date date, Vehicle
	// vehicle) {
	//
	// List<Order> orders = new ArrayList<Order>();
	// Map<String, Customer> customers = new HashMap<String, Customer>();
	// List<Vehicle> vehicles;
	// List<Location> locations = new ArrayList<Location>();
	// List<Depot> depots = new ArrayList<Depot>();
	//
	// String key;
	// orders = databaseService.getOrdersByDateAndVehicle(date, vehicle);
	//
	// for (Order order: orders) {
	// key =
	// String.valueOf(order.getCustomer().getCustomerId()+"_"+order.getCustomer().getTrip());
	// if (customers.get(order.getCustomer().getCustomerId()) == null) {
	// customers.put(key, order.getCustomer());
	// }
	// customers.get(key).addOrder(order);
	// }
	//
	// List<Customer> customersToIterate = new
	// ArrayList<Customer>(customers.values());
	//
	// customers.clear();
	//
	// for(Customer customer : customersToIterate)
	// {
	// if(customer.getDemand()>maxCapacity)
	// {
	// List<Customer> fixedCustomers = this.fixDemand((Customer) customer);
	// for(Customer fixedCustomer : fixedCustomers)
	// {
	// key = fixedCustomer.getCustomerId()+"_"+fixedCustomer.getTrip();
	// customers.put(key, fixedCustomer);
	// }
	// }
	// else
	// {
	// key = customer.getCustomerId()+"_"+customer.getTrip();
	// customers.put(key, customer);
	// }
	// customer.getLocation().setDistanceDurationMatrix(distanceDurationMatrix);
	// locations.add(customer.getLocation());
	// }
	//
	// vehicles = new ArrayList<Vehicle>();
	// vehicles.add(vehicle);
	//
	// depots.add(vehicle.getDepot());
	//
	// VehicleRoutingSolution schedule = new VehicleRoutingSolution();
	// schedule.setId(Long.valueOf(0L));
	// schedule.setName("name");
	//
	// schedule.setLocationList(locations);
	// schedule.setCustomerList(new ArrayList<Customer>(customers.values()));
	// schedule.setDepotList(depots);
	// schedule.setVehicleList(vehicles);
	// schedule.setDate(date);
	//
	//
	// // log.debug("---------customers size: "+customersToIterate.size());
	// // for(Location location : locations)
	// // {
	// // log.debug("---------location "+location.getAddress());
	// // }
	// // for (Vehicle vehicle : vehicles) {
	// //
	// log.debug("---------vehicle "+vehicle.getVehicleClientIdentifier()+" capacity: "+vehicle.getCapacity());
	// // }
	// // for (Depot depot: depots) {
	// // log.debug("---------depot "+depot.getDepotId());
	// // }
	//
	//
	//
	// ///////////////////////////////////////////////////////////////////////////////////
	// ////Código
	// original////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////////
	//
	//
	// // List<Customer> customers;
	// // List<Vehicle> vehicles;
	// // List<Location> locations = new ArrayList<Location>();
	// // List<Depot> depots = new ArrayList<Depot>();
	// //
	// // customers = new
	// ArrayList<Customer>(this.databaseService.getTimeWindowedCustomers());
	// // for (Customer customer : customers) {
	// // locations.add(customer.getLocation());
	// // }
	// //
	// //
	// // vehicles = this.databaseService.getVehicles();
	// // for (Vehicle vehicle : vehicles) {
	// // depots.add(vehicle.getDepot());
	// // }
	// //
	// //
	// // TimeWindowedVehicleRoutingSolution schedule = new
	// TimeWindowedVehicleRoutingSolution();
	// // schedule.setId(Long.valueOf(0L));
	// // schedule.setName("name");
	// //
	// // schedule.setLocationList(locations);
	// // schedule.setCustomerList(customers);
	// // schedule.setDepotList(depots);
	// // schedule.setVehicleList(vehicles);
	//
	// // for(Vehicle vehicle : vehicles)
	// // {
	// //
	// log.debug("----------------vehicle "+vehicle.getVehicleClientIdentifier()+" capacity: "+
	// vehicle.getCapacity());
	// // }
	// //
	// //
	// // for (Customer customer : customers) {
	// //
	// log.debug("----------------customer "+customer.getCustomerId()+" demand: "+
	// customer.getDemand());
	// // }
	//
	//
	//
	// return schedule;
	// }

	// public List<Customer> fixDemand(Customer customer)
	// {
	// if(customer.getDemand()<maxCapacity)
	// {
	// List<Customer> customers = new ArrayList<Customer>();
	// customers.add(customer);
	// return customers;
	// }
	//
	// List<Order> orders = new ArrayList<Order>(customer.getOrders());
	// List<Order> leftorders = new ArrayList<Order>();
	//
	// customer.getOrders().clear();
	// customer.setDemand(0);
	//
	// for(Order order : orders)
	// {
	// if((customer.getDemand()+order.getDemand())<maxCapacity)
	// {
	// customer.addOrder(order);
	// }
	// else
	// {
	// leftorders.add(order);
	// }
	// }
	//
	// TimeWindowedCustomer newTimeWindowedCustomer = new
	// TimeWindowedCustomer(customer);
	// newTimeWindowedCustomer.setTrip(newTimeWindowedCustomer.getTrip()+1);
	// newTimeWindowedCustomer.setOrders(leftorders);
	// newTimeWindowedCustomer.recalculateDemand();
	//
	// List<Customer> returnArrayCustomers = new ArrayList<Customer>();
	// returnArrayCustomers.add(customer);
	// for(Customer tempTimeWindowedCustomer :
	// fixDemand(newTimeWindowedCustomer))
	// {
	// returnArrayCustomers.add(tempTimeWindowedCustomer);
	// }
	//
	// return returnArrayCustomers;
	//
	// }
}
