package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewInterCityRoadEvent;
import simulator.model.Weather;

public class NewInterCityRoadEventBuilder extends Builder<Event> {

	public NewInterCityRoadEventBuilder() {
		super("new_inter_city_road");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {

		NewInterCityRoadEvent nicrEvent = null;

		int timeSlot = data.getInt("time");
		String id = data.getString("id");
		String srcJunc = data.getString("src");
		String destJunc = data.getString("dest");
		int length = data.getInt("length");
		int contLimit = data.getInt("co2limit");
		int maxSpeed = data.getInt("maxspeed");
		String weatherString = data.getString("weather");
		Weather weather;

		if(weatherString.equals("SUNNY")){
			weather = Weather.SUNNY;
		} else if (weatherString.equals("CLOUDY")) {
			weather = Weather.CLOUDY;
		} else if (weatherString.equals("RAINY")) {
			weather = Weather.RAINY;
		} else if (weatherString.equals("WINDY")) {
			weather = Weather.WINDY;
		} else {
			weather = Weather.STORM;
		}

		nicrEvent = new NewInterCityRoadEvent(timeSlot, id, srcJunc, destJunc, length, contLimit, maxSpeed, weather);

		return nicrEvent;
	}

}
