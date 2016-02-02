package cl.citymovil.route_pro.solver.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import cl.citymovil.route_pro.solver.dao.DistanceTimeDAO;
import cl.citymovil.route_pro.solver.dao.OrderDAO;
import cl.citymovil.route_pro.solver.dao.RouteDAO;
import cl.citymovil.route_pro.solver.dao.ScheduleDAO;
import cl.citymovil.route_pro.solver.dao.ScheduledCustomerDAO;
import cl.citymovil.route_pro.solver.domain.DistanceTime;
import cl.citymovil.route_pro.solver.domain.Order;
import cl.citymovil.route_pro.solver.domain.Route;
import cl.citymovil.route_pro.solver.domain.Schedule;
import cl.citymovil.route_pro.solver.domain.ScheduledCustomer;

@Service("databaseService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DataBaseServiceImpl implements DataBaseService {

	@Autowired
	DistanceTimeDAO distanceDurationDAO;
	
	@Autowired
	OrderDAO orderDAO;

	@Autowired
	ScheduleDAO scheduleDAO;

	@Autowired
	RouteDAO routeDAO;
	
	@Autowired
	ScheduledCustomerDAO scheduledCustomerDAO;

	public List<DistanceTime> getDistancesAndDurations() {
		return this.distanceDurationDAO.getAll();
	}
	
	public Schedule saveSchedule(Schedule schedule)
	{
		Schedule tempSchedule = scheduleDAO.save(schedule);
		return tempSchedule;
	}
	
	public Schedule updateSchedule(Schedule schedule)
	{
		Schedule tempSchedule = scheduleDAO.update(schedule);
		return tempSchedule;
	}


	public Route saveRoute(Route route)
	{
//		Route tempRoute = routeDAO.save(route);
		Route tempRoute = routeDAO.saveRoute(route);
		return tempRoute;
	}

	public ScheduledCustomer saveScheduledCustomer(ScheduledCustomer scheduledCustomer)
	{
//		ScheduledCustomer tempCustomerInRoute = scheduledCustomerDAO.save(scheduledCustomer);
		ScheduledCustomer tempCustomerInRoute = scheduledCustomerDAO.saveScheduledCustomer(scheduledCustomer);
		return tempCustomerInRoute;
	}
	
	public Order saveOrder(Order order)
	{
//		return orderDAO.save(order);
		return orderDAO.saveOrder(order);
	}

}
