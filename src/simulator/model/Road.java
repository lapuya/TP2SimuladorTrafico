package simulator.model;

import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

public abstract class Road extends SimulatedObject{

	private Junction cruce_origen;
	private Junction cruce_destino;
	private int longitud;
	protected int maxSpeed;
	protected int speedLimit;
	protected int alarm;
	protected Weather tiempo;
	protected int contaminacion_total;
	private List<Vehicle> vehiculos;
	
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed,
			int contLimit, int length, Weather weather) {
			super(id);
			if(maxSpeed < 0 && contLimit < 0 && longitud < 0 && srcJunc == null && destJunc == null && weather == null){
				throw new Exception();
			} else {
				this.cruce_origen = srcJunc;
				this.cruce_destino = destJunc;
				this.maxSpeed = maxSpeed;
				this.speedLimit = contLimit;
				this.longitud = length;
				this.tiempo = weather;
			}

		}




	public void enter(Vehicle v) {
		if(v.getLocalizacion() != 0 && v.getVelocidad_actual() != 0) {
			throw new Exception();
		} else {
			vehiculos.add(v);
		}
	}
	
	public void exit(Vehicle v) {
		vehiculos.remove(v);
	}
	
	public void setWeather(Weather w) {
		if(w == null) {
			throw new Exception();
		} else {
			tiempo = w;
		}
	}
	
	public void addContamination(int c) {
		if(c < 0) {
			throw new Exception();
		} else {
			contaminacion_total += c;
		}
	}

	@Override
	void advance(int time) {
		//(1)
		this.reduceTotalContamination();
		//(2)
		this.updateSpeedLimit();
		//(3)
		for(int i = 0; i < vehiculos.size(); i++) {
			
			//vehiculos.get(i).setVelocidad_actual(this.calculateVehicleSpeed()); 		FALTA SABER COMO CALCULAR EL VEHICLE SPEED
			vehiculos.get(i).advance(time);
			
		}
		
		//FALTA ORDENAR LA LISTA POR SU LOCALIZACION
		
		
		/*Iterator iterator = vehiculos.iterator();
		while(iterator.hasNext()) {
			vehiculos.get(
		}*/
		
	}
	
	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//Métodos abstractos a implementar por las subclases ---------------------------------------------------------------------------------------
	public abstract void reduceTotalContamination();
	
	public abstract void updateSpeedLimit();
	
	public abstract void calculateVehicleSpeed(Vehicle v);
	
}