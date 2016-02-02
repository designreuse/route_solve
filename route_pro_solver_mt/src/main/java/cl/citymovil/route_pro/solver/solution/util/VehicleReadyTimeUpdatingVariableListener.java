package cl.citymovil.route_pro.solver.solution.util;

import org.optaplanner.core.impl.domain.variable.listener.VariableListener;
import org.optaplanner.core.impl.score.director.ScoreDirector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.citymovil.route_pro.solver.domain.Customer;

public class VehicleReadyTimeUpdatingVariableListener implements VariableListener<Customer> {

	Logger logger = LoggerFactory.getLogger(VehicleReadyTimeUpdatingVariableListener.class);

	@Override
	public void beforeEntityAdded(ScoreDirector scoreDirector, Customer entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterEntityAdded(ScoreDirector scoreDirector, Customer entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeVariableChanged(ScoreDirector scoreDirector, Customer entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterVariableChanged(ScoreDirector scoreDirector, Customer entity) {
		// TODO Auto-generated method stub
		logger.error("VehicleReadyTimeUpdatingVariableListener-----------------------------------------------------------------------------------");
		logger.error("VehicleReadyTimeUpdatingVariableListener-----------------------------------------------------------------------------------");
		logger.error("VehicleReadyTimeUpdatingVariableListener-----------------------------------------------------------------------------------");
		
		System.out.println("VehicleReadyTimeUpdatingVariableListener-----------------------------------------------------------------------------------");
		System.out.println("VehicleReadyTimeUpdatingVariableListener-----------------------------------------------------------------------------------");
		System.out.println("VehicleReadyTimeUpdatingVariableListener-----------------------------------------------------------------------------------");
		
	}

	@Override
	public void beforeEntityRemoved(ScoreDirector scoreDirector, Customer entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterEntityRemoved(ScoreDirector scoreDirector, Customer entity) {
		// TODO Auto-generated method stub
		
	}
}
