package cl.citymovil.route_pro.solver.solution.util;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;

import cl.citymovil.route_pro.solver.domain.Customer;

public class VrpCustomerDifficultyComparator implements Comparator<Customer>, Serializable {
	private static final long serialVersionUID = 1L;

	public int compare(Customer a, Customer b) {
		return new CompareToBuilder()
		// .append(a.getLocation().getLatitude(), b.getLocation().getLatitude())
		// .append(a.getLocation().getLongitude(),
		// b.getLocation().getLongitude())
				.append(a.getDemand(), b.getDemand())
				// .append(a.getId(), b.getId())
				.toComparison();
	}
}
