package cl.citymovil.route_pro.solver.controllers;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.citymovil.route_pro.solver.domain.Schedule;
import cl.citymovil.route_pro.solver.domain.VRPProblem;
import cl.citymovil.route_pro.solver.domain.VehicleRoutingSolution;
import cl.citymovil.route_pro.solver.services.DataBaseService;
import cl.citymovil.route_pro.solver.services.VRPService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/solver")
public class SolverController {

	@Autowired
	VRPService cvrptwService;
	
	@Autowired
	DataBaseService databaseService;

	Logger logger = LoggerFactory.getLogger(SolverController.class);
	private static ExecutorService solvingExecutor = Executors.newFixedThreadPool(4);


	@RequestMapping(value = "/solve", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody String solveProblem(@RequestBody VRPProblem vrpProblem) {
		
		VehicleRoutingSolution vehicleRoutingProblem;
//		VehicleRoutingSolution vehicleRoutingSolution;
		vehicleRoutingProblem = vrpProblem.castToVehicleRoutingSolution(cvrptwService.getDistanceTimeMatrix());
		
		Schedule schedule = new Schedule();
		schedule.setDate(vehicleRoutingProblem.getDate());
		schedule.setSecondsToSpend(vrpProblem.getSecondsToSpend());
		schedule.setCreated(new Date());
		
		try {
			schedule = databaseService.saveSchedule(schedule);
			logger.debug(ToStringBuilder.reflectionToString(schedule, ToStringStyle.MULTI_LINE_STYLE));

		} catch (Exception e) {
			logger.error("Error saving schedule");
			e.printStackTrace();
		}
		
		cvrptwService.solveAsync( vehicleRoutingProblem, schedule);
		
		ObjectMapper mapper = new ObjectMapper();
		String returnString = "empty";
		
		try {
			returnString = mapper.writeValueAsString(schedule);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return returnString;
		
		/*
		System.gc();
		
		VehicleRoutingSolution vehicleRoutingProblem;
		VehicleRoutingSolution vehicleRoutingSolution;
		vehicleRoutingProblem = vrpProblem.make(cvrptwService.getDistanceTimeMatrix());
		
		cvrptwService.solveAndSave(vehicleRoutingProblem);
		
//		vehicleRoutingSolution = cvrptwService.solve(vehicleRoutingProblem);
		return "andskahdkshaudhsuahduksa";

		/*
		Schedule schedule = cvrptwService.createScheduleFromVehicleRoutingSolution(vehicleRoutingSolution);

		ObjectMapper mapper = new ObjectMapper();
		String returnString = "empty";
		
		try {
			returnString = mapper.writeValueAsString(schedule);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return returnString;
		*/
	}
}
