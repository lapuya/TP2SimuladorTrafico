package simulator.model;

public class InterCityRoad extends Road {

	private int x;

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) throws Exception {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void reduceTotalContamination() {
		// TODO Auto-generated method stub
		if(weather.equals(Weather.SUNNY)) {
			x = 2;
		} else if(weather.equals(Weather.CLOUDY)) {
			x = 3;
		} else if(weather.equals(Weather.RAINY)) {
			x = 10;
		} else if (weather.equals(Weather.WINDY)) {
			x = 15;
		} else {
			x = 20;
		}
		totalCont = (int)(((100.0-x)/100.0)*totalCont);

	}

	@Override
	public void updateSpeedLimit() {
		// TODO Auto-generated method stub
		if(totalCont > contLimit)
			speedLimit = (int)(maxSpeed*0.5);
		else
			speedLimit = maxSpeed;
	}

	@Override
	public int calculateVehicleSpeed(Vehicle v) {
		int speed;
		// TODO Auto-generated method stub
		if(weather.equals(Weather.STORM)) {
			//Si es storm, lo reduce un 20%
			speed = (int)(speedLimit*0.8);
		} else {
			//Si no, es el limite de la carretera
			speed = speedLimit;
		}

		return speed;
	}

}
