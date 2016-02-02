package cl.citymovil.route_pro.solver.services;

import java.util.Map;

import cl.citymovil.route_pro.solver.domain.DistanceTimeData;
import cl.citymovil.route_pro.solver.domain.Schedule;
import cl.citymovil.route_pro.solver.domain.VehicleRoutingSolution;


public interface VRPService {

	public void loadDistanceTimeData();
	public VehicleRoutingSolution solve(VehicleRoutingSolution unsolvedSolution);
	public Schedule createScheduleFromVehicleRoutingSolution(VehicleRoutingSolution vehicleRoutingSolution);
	public Map<Long, Map<Long, DistanceTimeData>> getDistanceTimeMatrix();
	
	public void solveAndSave(VehicleRoutingSolution unsolvedSolution, Schedule schedule);
	public void solveAsync(VehicleRoutingSolution vehicleRoutingProblem, Schedule schedule);
	public long getSecondsToSpend();
	
//	public VehicleRoutingSolution solve(Date date, VehicleType vehicleType);
//	public VehicleRoutingSolution solve(Date date, Vehicle vehicle);
//	public List<VehicleRoutingSolution> solve(Date date);
//	public void terminateEarly();
//	public Schedule save(VehicleRoutingSolution vehicleRoutingSolution);
//	public void save(List<VehicleRoutingSolution> vehicleRoutingSolution);
}
