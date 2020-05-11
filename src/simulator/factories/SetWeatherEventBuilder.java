package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;
import simulator.misc.*;

public class SetWeatherEventBuilder extends Builder<Event> {

	public SetWeatherEventBuilder() {
		super("set_weather");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) throws IllegalArgumentException {
		// TODO Auto-generated method stub

		SetWeatherEvent swEvent = null;
		try {

			int time = data.getInt("time");
			List<Pair<String,Weather>> ws = new ArrayList<Pair<String, Weather>>();
			Pair<String, Weather> p;
			String road, weatherString;
			Weather weather;

			for(int i = 0; i < data.getJSONArray("info").length(); i++) {
				road = data.getJSONArray("info").getJSONObject(i).getString("road");
				weatherString = data.getJSONArray("info").getJSONObject(i).getString("weather");

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
				p = new Pair<>(road, weather);
				ws.add(p);
			}
			swEvent = new SetWeatherEvent(time, ws);
		} catch (JSONException e) {
			throw new IllegalArgumentException ("Clave inexistente");
		}
		return swEvent;
	}

}
