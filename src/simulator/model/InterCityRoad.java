package simulator.model;

public class InterCityRoad extends Road {
	
	private int x;
	
	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void reduceTotalContamination() {
		// TODO Auto-generated method stub
		if(tiempo.equals(Weather.SUNNY)) {
			x = 2;
		} else if(tiempo.equals(Weather.CLOUDY)) {
			x = 3;
		} else if(tiempo.equals(Weather.RAINY)) {
			x = 10;
		} else if (tiempo.equals(Weather.WINDY)) {
			x = 15;
		} else {
			x = 20;
		}
		contaminacion_total = (int)(((100.0-x)/100.0)*contaminacion_total);
		
	}

	@Override
	public void updateSpeedLimit() {
		// TODO Auto-generated method stub
		
		//Preguntar al profe por si acaso
		if(maxSpeed > alarm) {
			speedLimit = (int)(maxSpeed*0.5);
		} else {
			speedLimit = maxSpeed;
		}
	}

	@Override
	public void calculateVehicleSpeed(Vehicle v) {
		// TODO Auto-generated method stub
		if(tiempo.equals(Weather.STORM)) {
			//Si es storm, lo reduce un 20%
			v.setSpeed((int)(speedLimit*0.8));
		} else {
			//Si no, es el limite de la carretera
			v.setSpeed(speedLimit);
		}
	}

}
