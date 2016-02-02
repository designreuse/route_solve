package cl.citymovil.route_pro.solver.domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.InverseRelationShadowVariable;

@PlanningEntity
public abstract interface Standstill {
	public abstract Location getLocation();

	public abstract Vehicle getVehicle();

	@InverseRelationShadowVariable(sourceVariableName = "previousStandstill")
	public abstract Customer getNextCustomer();

	public abstract void setNextCustomer(Customer paramCustomer);

}
