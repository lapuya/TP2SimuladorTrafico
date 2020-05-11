package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
public abstract class Road extends SimulatedObject{

	private Junction srcJunc;
	private Junction destJunc;
	private int length;
	protected int maxSpeed;
	protected int speedLimit;
	protected int contLimit;
	protected Weather weather;
	protected int totalCont;
	private List<Vehicle> vehicles;


	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed,
			int contLimit, int length, Weather weather) throws Exception {
			super(id);
			if(maxSpeed < 0 && contLimit < 0 && length < 0 && srcJunc == null && destJunc == null && weather == null){
				throw new Exception();
			} else {
				this.srcJunc = srcJunc;
				this.destJunc = destJunc;
				this.maxSpeed = maxSpeed;
				this.speedLimit = maxSpeed;	//valor inicial es valor maxima
				this.contLimit = contLimit;
				this.length = length;
				this.weather = weather;
				vehicles = new ArrayList<>();

				this.destJunc.addIncomingRoad(this);
				this.srcJunc.addOutGoingRoad(this);
			}


		}

	//Métodos en orden de aparicion en el enunciado de la practica------------------------------------------------------------------------------
	//Se añade el vehiculo al final de la lista
	public void enter(Vehicle v) throws Exception {

		if(v.getLocation() == 0 && v.getCurrentSpeed() == 0) {
			vehicles.add(v);
		} else {
			throw new Exception();
		}
	}

	//Elimina el vehiculo de la lista
	public void exit(Vehicle v) {
		vehicles.remove(v);
	}

	//Pone las condiciones atmosfericas al valor de w
	public void setWeather(Weather w) throws Exception {
		if(w == null) {
			throw new Exception();
		} else {
			weather = w;
		}
	}

	//Se añade c unidades de C02 al totalde la carretera
	public void addContamination(int c) throws Exception {
		if(c < 0) {
			throw new Exception();
		} else {
			totalCont += c;
		}
	}

	@Override
	void advance(int time) throws Exception {

		//(1)
		this.reduceTotalContamination();
		//(2)
		this.updateSpeedLimit();
		//(3)
		for(int i = 0; i < vehicles.size(); i++) {
			vehicles.get(i).setSpeed(this.calculateVehicleSpeed(vehicles.get(i)));
			vehicles.get(i).advance(time);
		}

		//Ordenar por localizacion: override en vehicles de compareTo
		Collections.sort(vehicles, Collections.reverseOrder());	//implementado en vehicle
	}


	@Override
	public JSONObject report() {

		JSONObject report = new JSONObject();
		report.put("id", _id);
		report.put("speedlimit", speedLimit);
		report.put("weather", weather.toString());
		report.put("co2", totalCont);

		JSONArray array_vehicles = new JSONArray();
		for(int i = 0; i < vehicles.size(); i++) {
			array_vehicles.put(vehicles.get(i).getId());
		}

		report.put("vehicles", array_vehicles);

		return report;
	}

	//Métodos getters y setters ---------------------------------------------------------------------------------------------------------------
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}


	public Junction getSrcJunc() {
		return srcJunc;
	}

	public void setSrcJunc(Junction srcJunc) {
		this.srcJunc = srcJunc;
	}

	public Junction getDestJunc() {
		return destJunc;
	}

	public void setDestJunc(Junction destJunc) {
		this.destJunc = destJunc;
	}

	public List<Vehicle> getList() {
		return vehicles;
	}




	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	//M�todos abstractos a implementar por las subclases ---------------------------------------------------------------------------------------
	public abstract void reduceTotalContamination();

	public abstract void updateSpeedLimit();

	public abstract int calculateVehicleSpeed(Vehicle v);


}
