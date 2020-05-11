package simulator.model;

public class CityRoad extends Road {

	private int x;
	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) throws Exception {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void reduceTotalContamination() {
		// TODO Auto-generated method stub
		if(weather.equals(Weather.WINDY) || weather.equals(Weather.STORM)) {
			x = 10;
		} else {
			x = 2;
		}
		totalCont -= x;

		//Preguntar al profe
		if(totalCont < 0) {
			totalCont = 0;
		}
	}

	@Override
	public void updateSpeedLimit() {
		// TODO Auto-generated method stub
	}

	@Override
	public int calculateVehicleSpeed(Vehicle v) {
		// TODO Auto-generated method stub
		int speed;
		speed = ((int)(((11.0-v.getGrado_contaminacion())/11.0)*speedLimit));

		return speed;

	}

}
