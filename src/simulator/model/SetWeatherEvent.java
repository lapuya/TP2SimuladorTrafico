package simulator.model;

import java.util.List;
import simulator.misc.*;

public class SetWeatherEvent extends Event{

	private List<Pair<String, Weather>> ws;

	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) throws IllegalArgumentException {
		super(time);
		if(ws == null) {
			throw new IllegalArgumentException("Par de weather null");
		} else {
			this.ws = ws;
		}
	}

	@Override
	void execute(RoadMap map) throws Exception {

		for(int i = 0; i < ws.size(); i++) {
			if(ws.get(i).getFirst() == null) {
				throw new Exception();
			} else {
				map.getRoad(ws.get(i).getFirst()).setWeather(ws.get(i).getSecond());
			}
		}
	}
}
