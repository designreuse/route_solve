package cl.citymovil.route_pro.solver.solution.util;

import org.apache.commons.lang3.ObjectUtils;
import org.optaplanner.core.impl.domain.variable.listener.VariableListener;
import org.optaplanner.core.impl.score.director.ScoreDirector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.citymovil.route_pro.solver.domain.Customer;
import cl.citymovil.route_pro.solver.domain.Standstill;
import cl.citymovil.route_pro.solver.domain.Vehicle;

public class ArrivalTimeUpdatingVariableListener implements VariableListener<Customer> {

	Logger logger = LoggerFactory.getLogger(ArrivalTimeUpdatingVariableListener.class);

	public void beforeEntityAdded(ScoreDirector scoreDirector, Customer customer) {

	}

	public void afterEntityAdded(ScoreDirector scoreDirector, Customer customer) {

		// logger.trace("------afterEntityAdded--------");
		// logger.trace("customer: " + customer);
		// logger.trace("------afterEntityAdded--------");
		updateArrivalTimeAndEnfOfTrip(scoreDirector, customer);

		// logger.debug("tripFinishTime: "+tripFinishTime);
		//
		// if (tripFinishTime != 0 && customer.getVehicleRecursive() != null &&
		// customer.getVehicleRecursive().getNextVehicle() != null) {
		// while (tripFinishTime != 0 && customer != null &&
		// customer.getVehicleRecursive() != null &&
		// customer.getVehicleRecursive().getNextVehicle() != null) {
		//
		// // logger.debug(customer.getVehicleRecursive().getExternalId() +
		// " => " +
		// customer.getVehicleRecursive().getNextVehicle().getExternalId());
		// // logger.debug("tripFinishTime: " + tripFinishTime);
		// // logger.debug("nextVehicleReadyTime: " +
		// customer.getVehicleRecursive().getNextVehicle().getReadyTime());
		// customer.getVehicleRecursive().getNextVehicle().setReadyTime(tripFinishTime);
		// // logger.debug("nextVehicleReadyTime: " +
		// customer.getVehicleRecursive().getNextVehicle().getReadyTime());
		//
		// if (customer.getVehicleRecursive().getNextVehicle().getNextCustomer()
		// != null) {
		// tripFinishTime = updateChain(scoreDirector,
		// customer.getVehicleRecursive().getNextVehicle().getNextCustomer());
		// customer =
		// customer.getVehicleRecursive().getNextVehicle().getNextCustomer();
		// } else {
		// customer = null;
		// }
		// }
		// }

	}

	public void beforeVariableChanged(ScoreDirector scoreDirector, Customer customer) {
	}

	public void afterVariableChanged(ScoreDirector scoreDirector, Customer customer) {

		// logger.error("------afterVariableChanged--------");
		// logger.error("customer: "+customer);
		// logger.error("------afterVariableChanged--------");

		updateArrivalTimeAndEnfOfTrip(scoreDirector, customer);

		// int tripFinishTime = updateChain(scoreDirector, (Customer) customer);
		//
		// logger.debug("afterVariableChanged, tripFinishTime: "+tripFinishTime);
		//
		// if (tripFinishTime != 0 && customer.getVehicleRecursive() != null &&
		// customer.getVehicleRecursive().getNextVehicle() != null) {
		//
		// logger.debug("dentro de if");
		//
		// while (tripFinishTime != 0 && customer != null &&
		// customer.getVehicleRecursive() != null &&
		// customer.getVehicleRecursive().getNextVehicle() != null) {
		//
		// // logger.debug(customer.getVehicleRecursive().getExternalId() +
		// " => " +
		// customer.getVehicleRecursive().getNextVehicle().getExternalId());
		// // logger.debug("tripFinishTime: " + tripFinishTime);
		// // logger.debug("nextVehicleReadyTime: " +
		// customer.getVehicleRecursive().getNextVehicle().getReadyTime());
		// customer.getVehicleRecursive().getNextVehicle().setReadyTime(tripFinishTime);
		// // logger.debug("nextVehicleReadyTime: " +
		// customer.getVehicleRecursive().getNextVehicle().getReadyTime());
		//
		// if (customer.getVehicleRecursive().getNextVehicle().getNextCustomer()
		// != null) {
		// tripFinishTime = updateChain(scoreDirector,
		// customer.getVehicleRecursive().getNextVehicle().getNextCustomer());
		// customer =
		// customer.getVehicleRecursive().getNextVehicle().getNextCustomer();
		// } else {
		// customer = null;
		// }
		// }
		// }
	}

	public void beforeEntityRemoved(ScoreDirector scoreDirector, Customer customer) {
	}

	public void afterEntityRemoved(ScoreDirector scoreDirector, Customer customer) {
	}

	protected void updateArrivalTimeAndEnfOfTrip(ScoreDirector scoreDirector, Customer customer) {

		// updateChain(scoreDirector, customer);
		// return;
		logger.trace("--------------------------------------------------------------------------------");
		logger.trace("-----------------updateArrivalTimeAndEnfOfTrip begins---------------------------");
		logger.trace("--------------------------------------------------------------------------------");

		// customer.getPreviousStandstill()==null
		// customer.getNextCustomer()==null
		// customer.getVehicle().getNextCustomer()==null

		logger.trace("customer : " + customer);
		logger.trace("customer.getPreviousStandstill() : " + customer.getPreviousStandstill());
		logger.trace("customer.getNextCustomer() : " + customer.getNextCustomer());
		logger.trace("customer.getVehicleRecursive(): " + customer.getVehicleRecursive());
		logger.debug("vehicle.getNextCustomer(): " + customer.getVehicleRecursive().getNextCustomer());
		
		updateArrivalTime(scoreDirector, customer);
		Vehicle vehicle = customer.getVehicleRecursive();
		Integer calculatedEndOfTrip = calculateEndOfTrip(vehicle);
		
//		if(calculatedEndOfTrip==1 && customer.getPreviousStandstill()!=null)
//		{
//			logger.debug("customer : " + customer);
//			logger.debug("customer.getPreviousStandstill() : " + customer.getPreviousStandstill());
//			logger.debug("customer.getNextCustomer() : " + customer.getNextCustomer());
//			logger.debug("customer.getVehicleRecursive(): " + customer.getVehicleRecursive());
//			logger.debug("vehicle.getNextCustomer(): " + customer.getVehicleRecursive().getNextCustomer());
//		}
		
		vehicle.setEndOfTrip(calculatedEndOfTrip);
		
//		while(vehicle!=null)
//		{
//			updateArrivalTime(scoreDirector, customer);
//			vehicle.setEndOfTrip(calculateEndOfTrip(vehicle));
//			
//			vehicle = vehicle.getNextVehicle();
//		}
		
		
//		logger.trace(" while (nextVehicle != null)...");
//		logger.trace("vehicle: " + vehicle);
//		logger.trace("nextVehicle: " + nextVehicle);
//		logger.trace("nextVehicle.getReadyTime(): " + nextVehicle.getReadyTime());
//		logger.trace("calculatedEndOfTrip: " + calculatedEndOfTrip);
//		logger.trace("nextVehicle.getNextCustomer(): " + nextVehicle.getNextCustomer());
		
		
		
//		updateArrivalTime(scoreDirector, customer);
//		Vehicle vehicle = customer.getVehicleRecursive();
//		
//		Integer calculatedEndOfTrip = calculateEndOfTrip(vehicle);
//		vehicle.setEndOfTrip(calculatedEndOfTrip);
//		
//		Vehicle nextVehicle = vehicle.getNextVehicle();
//		
//		while (nextVehicle != null)
//		{
//			
//
//			nextVehicle.setReadyTime(calculatedEndOfTrip);
//			if (nextVehicle.getNextCustomer() != null) {
//				updateArrivalTime(scoreDirector, nextVehicle.getNextCustomer());
//				
//			}
//			calculatedEndOfTrip = calculateEndOfTrip(nextVehicle);
//			nextVehicle.setEndOfTrip(calculatedEndOfTrip);
//			
//			vehicle = nextVehicle;
//			nextVehicle = vehicle.getNextVehicle();
//			calculatedEndOfTrip = calculateEndOfTrip(vehicle);
//
//		}		
		
		logger.trace("--------------------------------------------------------------------------------");
		logger.trace("-----------------updateArrivalTimeAndEnfOfTrip ends-----------------------------");
		logger.trace("--------------------------------------------------------------------------------");
	}


	protected void updateArrivalTime(ScoreDirector scoreDirector, Customer sourceCustomer) {

		logger.trace("--------------------------------------------------------------------------------");
		logger.trace("----------------updateArrivalTime (customer) begins-----------------------------");
		logger.trace("--------------------------------------------------------------------------------");

		Integer previousDepartureTime = calculatePreviousDepartureTime(sourceCustomer);
		Integer arrivalTime = calculateArrivalTime(sourceCustomer, previousDepartureTime);
		
		Customer nextCustomer = sourceCustomer;

		logger.trace("sourceCustomer: " + sourceCustomer);
		logger.trace("sourceCustomer.getVehicleRecursive(): " + sourceCustomer.getVehicleRecursive());
		logger.trace("sourceCustomer.getVehicleRecursive().getNextCustomer(): " + sourceCustomer.getVehicleRecursive().getNextCustomer());
		logger.trace("calculated departureTime : " + previousDepartureTime);
		logger.trace("sourceCustomer.getArrivalTime() : " + sourceCustomer.getArrivalTime());
		logger.trace("calculated arrivalTime : " + arrivalTime);


		// actualiza solo si es necesario (while se ejecuta solo una vez en las correcciones de los punteros)
		while ((nextCustomer != null) && (ObjectUtils.notEqual(nextCustomer.getArrivalTime(), arrivalTime))) {

			logger.trace("while() shadowCustomer: " + nextCustomer);
			scoreDirector.beforeVariableChanged(nextCustomer, "arrivalTime");
			nextCustomer.setArrivalTime(arrivalTime);
			scoreDirector.afterVariableChanged(nextCustomer, "arrivalTime");
			previousDepartureTime = nextCustomer.getDepartureTime();
			nextCustomer = nextCustomer.getNextCustomer();
			arrivalTime = calculateArrivalTime(nextCustomer, previousDepartureTime);
		}

		logger.trace("--------------------------------------------------------------------------------");
		logger.trace("----------------updateArrivalTime (customer) ends-------------------------------");
		logger.trace("--------------------------------------------------------------------------------");
	}
	
	private Integer calculateArrivalTime(Customer customer, Integer previousDepartureTime) {

		// para que no falle al final del while
		if (customer == null) {
			return null;
		}
		// no ha sido asignado
		if (previousDepartureTime == null) {
			return Integer.valueOf((int) Math.max(customer.getReadyTime(), customer.getSecondsFromPreviousStandstill()));
		}
		// caso común
		return Integer.valueOf((int) (previousDepartureTime.intValue() + customer.getSecondsFromPreviousStandstill()));
	}

	private Integer calculatePreviousDepartureTime(Customer customer) {

		Standstill previousStandstill = customer.getPreviousStandstill();
		Integer departureTime;

		if (previousStandstill instanceof Customer) {
			departureTime = ((Customer) previousStandstill).getDepartureTime();
		} else if (previousStandstill instanceof Vehicle) {
			// el mayor entre la hora de apertura del depósito y la hora de
			// inicio de operación del vehículo
			// logger.trace("antes de obtener readyTime de vehiculo!!!");
			departureTime = Integer.valueOf(Math.max(((Vehicle) previousStandstill).getDepot().getReadyTime(), ((Vehicle) previousStandstill).getReadyTime()));
		} else {
			// si no ha sido asignado entonces el departureTime es null para que
			// calcule un arrivalTime igual al readyTime
			// (para no romper una restriccion dura)
			departureTime = null;
		}

		return departureTime;
	}
	
	private Integer calculateEndOfTrip(Vehicle vehicle) {
		
		logger.trace("--------------------------------------------------------------------------------");
		logger.trace("--------------------calculateEndOfTrip begins ----------------------------------");
		logger.trace("--------------------------------------------------------------------------------");

		logger.trace("vehicle : " + vehicle);
		logger.trace("vehicle.getNextCustomer() : " + vehicle.getNextCustomer());
		
		Integer endOfTrip = vehicle.getReadyTime();
		Customer customer = vehicle.getNextCustomer();
		while (customer != null) {
			if (customer.getNextCustomer() == null) {

				endOfTrip = (int) (customer.getDepartureTime() + customer.getSecondsTo(customer.getVehicleRecursive()));
			}
			customer = customer.getNextCustomer();
		}
		
		logger.trace("--------------------------------------------------------------------------------");
		logger.trace("--------------------calculateEndOfTrip begins ----------------------------------");
		logger.trace("--------------------------------------------------------------------------------");

		return endOfTrip;
	}

}
