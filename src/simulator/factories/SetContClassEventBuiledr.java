package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;

public class SetContClassEventBuilder extends Builder <Event> {

	public SetContClassEventBuilder() {
		super("set_cont_class");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) throws IllegalArgumentException {
		// TODO Auto-generated method stub

		NewSetContClassEvent nsccEvent = null;

		try {

			String vehicleId;
			int contClass;

			List<Pair<String,Integer>> cs = new ArrayList<Pair<String, Integer>>();
			Pair<String, Integer> p;

			int time = data.getInt("time");

			for(int i = 0; i < data.getJSONArray("info").length(); i++) {
				vehicleId = data.getJSONArray("info").getJSONObject(i).getString("vehicle");
				contClass = data.getJSONArray("info").getJSONObject(i).getInt("class");
				p = new Pair<>(vehicleId, contClass);
				cs.add(p);
			}

			nsccEvent = new NewSetContClassEvent (time, cs);
		} catch (JSONException e){
			throw new IllegalArgumentException("Clave inexistente");
		}

		return nsccEvent;
	}


}
