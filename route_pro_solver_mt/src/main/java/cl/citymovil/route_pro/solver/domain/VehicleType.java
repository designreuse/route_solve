package cl.citymovil.route_pro.solver.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Entity
public class VehicleType {

//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long vehicleTypeId;
	
	private String description;
	
	public VehicleType(){}
	
	public VehicleType(long vehicleTypeId){
		
		this.vehicleTypeId = vehicleTypeId;
	}
	
	public VehicleType(VehicleType vehicleType){
		
		this.vehicleTypeId = vehicleType.getVehicleTypeId();
		this.description = vehicleType.getDescription();
	}
	
	

	public long getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(long vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
