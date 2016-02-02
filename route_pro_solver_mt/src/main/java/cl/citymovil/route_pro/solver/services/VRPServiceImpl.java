package cl.citymovil.route_pro.solver.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cl.citymovil.route_pro.solver.controllers.SolverController;
import cl.citymovil.route_pro.solver.domain.Customer;
import cl.citymovil.route_pro.solver.domain.Depot;
import cl.citymovil.route_pro.solver.domain.DistanceTimeData;
import cl.citymovil.route_pro.solver.domain.Order;
import cl.citymovil.route_pro.solver.domain.Schedule;
import cl.citymovil.route_pro.solver.domain.Route;
import cl.citymovil.route_pro.solver.domain.ScheduledCustomer;
import cl.citymovil.route_pro.solver.domain.VRPProblem;
import cl.citymovil.route_pro.solver.domain.Vehicle;
import cl.citymovil.route_pro.solver.domain.VehicleRoutingSolution;

@Service("cvrptwService")
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class VRPServiceImpl implements VRPService {

	Logger logger = LoggerFactory.getLogger(VRPServiceImpl.class);

	private static final String solverConfig = "/cl/citymovil/route_pro/solver/config/vehicleRoutingSolverConfigTesting.xml";
	// private static final String solverConfig =
	// "/cl/citymovil/route_pro/solver/config/vehicleRoutingSolverConfigPruebasJP.xml";
	private static ExecutorService solvingExecutor = Executors.newFixedThreadPool(4);

	@Autowired
	VehicleRoutingFactsImporter vehicleRoutingFactsImporter;

	@Autowired
	DataBaseService databaseService;

	private Solver solver;
	private SolverFactory solverFactory;
	long secondsSpentLimit = 40;

	public VRPServiceImpl() {
		// build();
	}

	public void solveAsync(VehicleRoutingSolution vehicleRoutingProblem, Schedule schedule) {
		/*
		 * if(solverFactory!=null) {
		 * schedule.setSecondsToSpend(solverFactory.getSolverConfig
		 * ().getTerminationConfig().getSecondsSpentLimit()); }
		 */
		if (schedule.getSecondsToSpend() != 0) {
			this.secondsSpentLimit = schedule.getSecondsToSpend();
		}

		solvingExecutor.submit(new Runnable() {
			public void run() {
				solveAndSave(vehicleRoutingProblem, schedule);
			}
		});
	}

	public void solveAndSave(VehicleRoutingSolution unsolvedSolution, Schedule schedule) {
		terminateEarly();
		solver.solve(unsolvedSolution);
		VehicleRoutingSolution vehicleRoutingSolution = (VehicleRoutingSolution) solver.getBestSolution();
		save(solver, vehicleRoutingSolution, schedule);
	}

	public void save(Solver solver, VehicleRoutingSolution vehicleRoutingSolution, Schedule schedule) {

		// System.out.println("schedule_id: "+ schedule.getScheduleId());
		logger.debug(ToStringBuilder.reflectionToString(schedule, ToStringStyle.MULTI_LINE_STYLE));

		schedule.setDate(vehicleRoutingSolution.getDate());
		schedule.setIsFeasible(vehicleRoutingSolution.getScore().isFeasible());
		schedule.setHardScore(vehicleRoutingSolution.getScore().getHardScore());
		schedule.setSoftScore(vehicleRoutingSolution.getScore().getSoftScore());
		schedule.setMillisecondsSpent((int) solver.getTimeMillisSpent());
		


		Map<String, Customer> firstCustomers = new HashMap<String, Customer>();
		Map<Long, Vehicle> usedVehicles = new HashMap<Long, Vehicle>();
		String key;
		for (Customer customer : vehicleRoutingSolution.getCustomerList()) {
			if (customer.getPreviousStandstill().getLocation().getLocationId() == 1) {
				key = String.valueOf(customer.getExternalId());
				firstCustomers.put(key, customer);
				usedVehicles.put(customer.getVehicle().getVehicleId(), customer.getVehicle());
			}
		}

		schedule.setTotalVehicles(vehicleRoutingSolution.getVehicleList().size());
		schedule.setUsedVehicles(usedVehicles.size());
		schedule.setFinished(new Date());
		
		//logger.debug("about to save..." + schedule.getScheduleId());
		//schedule = databaseService.updateSchedule(schedule);
		//logger.debug("saved..." + schedule.getScheduleId());

		Depot depot = vehicleRoutingSolution.getVehicleList().get(0).getDepot();

		for (Vehicle tempVehicle : usedVehicles.values()) {
			
//			logger.debug(ToStringBuilder.reflectionToString(tempVehicle, ToStringStyle.MULTI_LINE_STYLE));
			
			logger.debug("vehicle: "+ tempVehicle.getExternalId());
			logger.debug("endOfTrip: "+ tempVehicle.getEndOfTrip());
			
			
			Route route = new Route();
			route.setVehicle(tempVehicle);
			route.setSchedule(schedule);

			int totalCustomersInRoute = 0;
			int totalMeters = 0;
			float totalKilograms = 0;
			
			int secondsToDepot;
			int serviceDuration;
			int arrivalTime;
			int calculatedEndOfTrip;
			
			for (Customer customer : firstCustomers.values()) {
				if (Long.compare(customer.getVehicle().getVehicleId(), tempVehicle.getVehicleId()) == 0) {

					int distanceToDepot;
					do {
						totalCustomersInRoute++;
						totalMeters += customer.getMetersFromPreviousStandstill();
						totalKilograms += customer.getDemand();

						distanceToDepot = (int) customer.getMetersTo(customer.getVehicle());
						
						arrivalTime = customer.getArrivalTime();
						secondsToDepot = (int) customer.getSecondsTo(customer.getVehicle());
						serviceDuration = customer.getServiceDuration();

						customer = customer.getNextCustomer();
					} while (customer != null);

					totalMeters += distanceToDepot;
					
					
					calculatedEndOfTrip = arrivalTime + serviceDuration + secondsToDepot;
					logger.debug("calculatedEndOfTrip: "+calculatedEndOfTrip);
					
					
					break;
				}
			}
			route.setTotalCustomersInRoute(totalCustomersInRoute);
			route.setTotalMeters(totalMeters);
			route.setTotalKilograms(totalKilograms);

//			route = databaseService.saveRoute(route);

			schedule.getRoutes().add(route);

			int sequence = 0;
			ScheduledCustomer tempScheduledCustomer;
			for (Customer customer : firstCustomers.values()) {
				if (Long.compare(customer.getVehicle().getVehicleId(), tempVehicle.getVehicleId()) == 0) {

					do {
						sequence++;

						tempScheduledCustomer = new ScheduledCustomer(customer);
						tempScheduledCustomer.setSequence(sequence);
						tempScheduledCustomer.setRoute(route);

//						tempScheduledCustomer = databaseService.saveScheduledCustomer(tempScheduledCustomer);

						route.getScheduledCustomers().add(tempScheduledCustomer);

						for (Order order : customer.getOrders()) {

							order.setCustomer(customer);
							order.setScheduledCustomer(tempScheduledCustomer);
							order.setDate(schedule.getDate());


//							order = databaseService.saveOrder(order);
							tempScheduledCustomer.getOrders().add(order);


						}

						customer = customer.getNextCustomer();

					} while (customer != null);

					break;
				}
			}
		}
		// schedule = databaseService.saveSchedule(schedule);
		 schedule = databaseService.updateSchedule(schedule);
	}

	private void build() {
		solverFactory = SolverFactory.createFromXmlResource(solverConfig);
		solverFactory.getSolverConfig().getTerminationConfig().setSecondsSpentLimit(secondsSpentLimit);
		solver = solverFactory.buildSolver();
	}

	public void loadDistanceTimeData() {
		vehicleRoutingFactsImporter.loadDistanceTimeData();
	}

	public Map<Long, Map<Long, DistanceTimeData>> getDistanceTimeMatrix() {
		return vehicleRoutingFactsImporter.getDistanceTimeDataMatrix();
	}

	public VehicleRoutingSolution solve(VehicleRoutingSolution unsolvedSolution) {
		terminateEarly();
		solver.solve(unsolvedSolution);
		return (VehicleRoutingSolution) solver.getBestSolution();
	}

	public Schedule createScheduleFromVehicleRoutingSolution(VehicleRoutingSolution vehicleRoutingSolution) {

		Schedule schedule = new Schedule();

		schedule.setDate(vehicleRoutingSolution.getDate());
		schedule.setIsFeasible(vehicleRoutingSolution.getScore().isFeasible());
		schedule.setHardScore(vehicleRoutingSolution.getScore().getHardScore());
		schedule.setSoftScore(vehicleRoutingSolution.getScore().getSoftScore());
		// schedule.setMillisecondsSpent((int) solver.getTimeMillisSpent());

		Map<String, Customer> firstCustomers = new HashMap<String, Customer>();
		Map<Long, Vehicle> usedVehicles = new HashMap<Long, Vehicle>();
		String key;
		for (Customer customer : vehicleRoutingSolution.getCustomerList()) {
			if (customer.getPreviousStandstill().getLocation().getLocationId() == 1) {
				key = String.valueOf(customer.getExternalId());
				firstCustomers.put(key, customer);
				usedVehicles.put(customer.getVehicle().getVehicleId(), customer.getVehicle());
			}
		}

		schedule.setTotalVehicles(vehicleRoutingSolution.getVehicleList().size());
		schedule.setUsedVehicles(usedVehicles.size());

		Depot depot = vehicleRoutingSolution.getVehicleList().get(0).getDepot();

		databaseService.saveSchedule(schedule);

		for (Vehicle tempVehicle : usedVehicles.values()) {
			Route route = new Route();
			route.setVehicle(tempVehicle);
			route.setSchedule(schedule);

			int totalCustomersInRoute = 0;
			int totalMeters = 0;
			float totalKilograms = 0;
			for (Customer customer : firstCustomers.values()) {
				if (Long.compare(customer.getVehicle().getVehicleId(), tempVehicle.getVehicleId()) == 0) {

					int distanceToDepot;
					do {
						totalCustomersInRoute++;
						totalMeters += customer.getMetersFromPreviousStandstill();
						totalKilograms += customer.getDemand();

						distanceToDepot = (int) customer.getMetersTo(customer.getVehicle());

						customer = customer.getNextCustomer();
					} while (customer != null);

					totalMeters += distanceToDepot;
					break;
				}
			}
			route.setTotalCustomersInRoute(totalCustomersInRoute);
			route.setTotalMeters(totalMeters);
			route.setTotalKilograms(totalKilograms);

			databaseService.saveRoute(route);

			schedule.getRoutes().add(route);
			int sequence = 0;
			ScheduledCustomer tempScheduledCustomer;
			for (Customer customer : firstCustomers.values()) {
				if (Long.compare(customer.getVehicle().getVehicleId(), tempVehicle.getVehicleId()) == 0) {

					do {
						sequence++;

						tempScheduledCustomer = new ScheduledCustomer(customer);
						tempScheduledCustomer.setSequence(sequence);
						tempScheduledCustomer.setRoute(route);
						route.getScheduledCustomers().add(tempScheduledCustomer);
						customer = customer.getNextCustomer();

					} while (customer != null);

					break;
				}
			}
		}
		return schedule;
	}

	public void terminateEarly() {
		if (solver != null) {
			solver.terminateEarly();
			solver = null;
		}
		build();
	}

	@Override
	public long getSecondsToSpend() {

		if (solverFactory != null) {
			return solverFactory.getSolverConfig().getTerminationConfig().getSecondsSpentLimit();
		}
		// TODO Auto-generated method stub
		return 0;
	}

}
