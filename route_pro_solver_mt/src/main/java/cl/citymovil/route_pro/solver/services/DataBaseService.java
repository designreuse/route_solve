package cl.citymovil.route_pro.solver.services;

import java.util.List;

import cl.citymovil.route_pro.solver.domain.DistanceTime;
import cl.citymovil.route_pro.solver.domain.Order;
import cl.citymovil.route_pro.solver.domain.Route;
import cl.citymovil.route_pro.solver.domain.Schedule;
import cl.citymovil.route_pro.solver.domain.ScheduledCustomer;

public interface DataBaseService {

//	public List<Customer> getCustomers();
//
//	public List<Depot> getDepots();
//
//	public List<Customer> getTimeWindowedCustomers();
//
//	public List<Depot> getTimeWindowedDepots();
//
//	public List<Location> getLocations();
//
//	public Vehicle getVehicleById(long id);
//
//	public List<Vehicle> getVehicles();
//	
//	public List<Vehicle> getVehiclesByType(VehicleType vehicleType);
//
	public List<DistanceTime> getDistancesAndDurations();
//	
//	public List<Order> getOrders();
//
//	public List<Order> getOrders(Date date);
//	
//	public List<Order> getOrdersByDateAndVehicle(Date date,Vehicle vehicle);
//	
	public Order saveOrder(Order order);
//	
//	public void removeOrder(Order order);
//	
//	public List<Schedule> getSchedules();
//
	public Schedule saveSchedule(Schedule schedule);

	public Schedule updateSchedule(Schedule schedule);
	
	public Route saveRoute(Route route);
	
	public ScheduledCustomer saveScheduledCustomer(ScheduledCustomer customerInRoute);

//	public Schedule getScheduleById(long scheduleId);
}
