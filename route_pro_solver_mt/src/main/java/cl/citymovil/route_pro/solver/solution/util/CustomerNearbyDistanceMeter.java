package cl.citymovil.route_pro.solver.solution.util;

import org.optaplanner.core.impl.heuristic.selector.common.nearby.NearbyDistanceMeter;

import cl.citymovil.route_pro.solver.domain.Customer;
import cl.citymovil.route_pro.solver.domain.Standstill;

public class CustomerNearbyDistanceMeter implements NearbyDistanceMeter<Customer, Standstill> {

	public double getNearbyDistance(Customer origin, Standstill destination) {
		return origin.getLocation().getMetersTo(destination.getLocation());
	}
}
