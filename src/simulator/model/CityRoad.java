package simulator.model;

public class CityRoad extends Road {
	
	private int x;
	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void reduceTotalContamination() {
		// TODO Auto-generated method stub
		if(tiempo.equals(Weather.WINDY) || tiempo.equals(Weather.STORM)) {
			x = 10;
		} else {
			x = 2;
		}
		contaminacion_total -= x;
		
		//Preguntar al profe
		if(contaminacion_total < 0) {
			contaminacion_total = 0;
		}
	}

	@Override
	public void updateSpeedLimit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void calculateVehicleSpeed(Vehicle v) {
		// TODO Auto-generated method stub
		v.setSpeed((int)(((11.0-v.getGrado_contaminacion())/11.0)*speedLimit));
		
	}

}
