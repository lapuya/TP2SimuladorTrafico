package simulator.model;

import java.util.*;

import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject implements Comparable <Vehicle>{

	private List<Junction> itinerary;
	private int maxSpeed;
	private int currentSpeed;
	private VehicleStatus state;
	private Road road;
	private int location;
	private int contaminationLevel;
	private int totalContamination;
	private int totalDistanceTraveled;
	private int lastJunctionVisited = 0;

	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) throws Exception {
		super(id);

		//Comprobar que los argumentos tienen valores validos
		//Itinerario no se puede modificar desde fuera
		if(maxSpeed > 0 && contClass >= 0 && contClass <= 10 && itinerary.size() >= 2) {
			this.maxSpeed = maxSpeed;
			this.contaminationLevel = contClass;
			this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));;
			this.state = VehicleStatus.PENDING;
		} else {
			throw new Exception();
		}
	}

	//Velocidad actual al valor mínimo entre s y la velocidad maxma
	 void setSpeed(int s) throws Exception {

		if(s<0) {
			throw new Exception();
		}else {
			if (s > maxSpeed) {
				this.currentSpeed = maxSpeed;
			}
			else {
				this.currentSpeed = s;
			}
		}
	}

	 //Poner el valor de contaminacion a c
	public void setContaminationClass(int c) throws Exception {

		if(c < 0 || c > 10) {
			throw new Exception();
		}
		this.contaminationLevel = c;
	}

	@Override
	void advance(int time) throws Exception {

		int previousLocation = this.location, l, c;

		if(state.equals(VehicleStatus.TRAVELING)) {

			//(a), actualizar localizacion
			if((this.location + this.currentSpeed) < road.getLength()) {
				this.location = this.location + this.currentSpeed;
				totalDistanceTraveled += this.currentSpeed;
			}else {
				this.location = road.getLength();

			}

			//(b), calcular contaminacion
			l = this.location - previousLocation;
			c = l * contaminationLevel;
			this.totalContamination += c;
			this.road.addContamination(c);

			//(c)
			if(location >= road.getLength()) {
				//el vehiculo entra en la cola de un cruce
				int resta =  road.getLength() - totalDistanceTraveled;
				if(resta < 0) {
					totalDistanceTraveled += this.currentSpeed-10;
				} else {
					totalDistanceTraveled += resta;
				}
				this.road.getDestJunc().addVehicleToJunctionQueue(this);
				this.state = VehicleStatus.WAITING;
				this.currentSpeed = 0;
			}
		}

	}


	public void moveToNextRoad() throws Exception {
		if(lastJunctionVisited+1 != itinerary.size()) {
			if(this.state.equals(VehicleStatus.PENDING)) {
				//Empezar su itinerario
				this.road = itinerary.get(0).roadTo(this.itinerary.get(1));
				this.road.enter(this);
				//Modificar estado vehiculo
				this.state = VehicleStatus.TRAVELING;
				lastJunctionVisited++;
			} else if (this.state.equals(VehicleStatus.WAITING)) {
				//Esta waiting
				Road nextRoad = null;
				//Asigno el valor del siguiente cruce de mi itinerario
				nextRoad = this.road.getDestJunc().roadTo(this.itinerary.get(lastJunctionVisited+1));
				//salimos de la carretera
				this.road.exit(this);
				//entramos en la nueva
				this.road = nextRoad;
				this.location = 0;
				this.currentSpeed = 0;
				this.road.enter(this);
				//aumentar el indicar del ultimo cruce visitado del itinerario
				lastJunctionVisited++;
				this.state = VehicleStatus.TRAVELING;
			} else {
					throw new Exception();
			}
		} else {
			road.exit(this);
			this.state = VehicleStatus.ARRIVED;

		}

	}

	@Override
	public JSONObject report() {
		JSONObject report = new JSONObject();

		report.put("id", _id);
		if(state.equals(VehicleStatus.TRAVELING)) {
			report.put("speed", currentSpeed);
		} else {
			report.put("speed", 0);	//Si no es traveling, velocidad = 0
		}
		report.put("distance", totalDistanceTraveled);
		report.put("co2", totalContamination);
		report.put("class", contaminationLevel);
		report.put("status", state.toString());

		//Si el estado del vehiculo es pending o arrived, se omite road y location
		if(!state.equals(VehicleStatus.PENDING) && !state.equals(VehicleStatus.ARRIVED)) {
			report.put("road", road.getId());
			report.put("location", location);
		}

		return report;
	}

	//Getters y setters -----------------------------------------------------------------------------------------------------------------------
	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public int getCurrentSpeed() {
		return currentSpeed;
	}

	public void setVelocidad_actual(int currentSpeed) {
		this.currentSpeed = currentSpeed;
	}

	public int getGrado_contaminacion() {
		return contaminationLevel;
	}


	public Road getRoad() {
		return road;
	}


	public void setRoad(Road road) {
		this.road = road;
	}




	public List<Junction> getItinerary() {
		return itinerary;
	}


	public void setItinerary(List<Junction> itinerary) {
		this.itinerary = itinerary;
	}


	@Override
	public int compareTo(Vehicle v1) {
		if(this.getLocation() > v1.getLocation()) {
			return 1;
		} else if (this.getLocation() == v1.getLocation()) {
			return -1;
		} else {
			return -1;
		}
	}





}
