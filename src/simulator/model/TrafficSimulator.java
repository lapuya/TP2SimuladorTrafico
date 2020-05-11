package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator {

	private RoadMap roadMap;
	private List<Event> eventsList;
	private int time;
	int cont = 0;

	public TrafficSimulator() {
		List<Junction> jList = new ArrayList<>();
		List<Road> rList = new ArrayList <>();
		List<Vehicle> vList = new ArrayList<>();
		Map<String,Junction> jMap = new HashMap<String, Junction>();
		Map<String,Road> rMap = new HashMap<String, Road>();
		Map<String,Vehicle> vMap = new HashMap<String, Vehicle>();
		this.roadMap = new RoadMap(jList, rList, vList, jMap, rMap, vMap);
		this.eventsList = new SortedArrayList<>();
		this.time  = 0;
	}

	public void addEvent(Event e) {
		if(!eventsList.contains(e)) {
			eventsList.add(e);
		}

	}

	public void advance(int tick) throws Exception {

		//1.Incrementar el tiempo en uno
		time +=1;

		//2. Ejecuta todos los eventos que coincidan en el tiempo
		for(int i = 0; i < eventsList.size(); i++) {
			if(eventsList.get(i).getTime() == time) {
				eventsList.get(i).execute(roadMap);
			}
		}

		//Booramos aquellos ya ejecutados
		for(int i = 0; i < eventsList.size(); i++) {
			if(eventsList.get(i).getTime() == time) {
				eventsList.remove(i);
			}
		}



		//3. Llamar el metodoa advance de todos los cruces
		for(int i = 0; i < roadMap.getJunctions().size(); i++){
			roadMap.getJunctions().get(i).advance(time);
		}

		//4. Llamar el metodo advance de todas las carreteras
		for(int i = 0; i < roadMap.getRoads().size(); i++) {
			roadMap.getRoads().get(i).advance(time);
		}
	}

	public void reset() {
		roadMap.reset();
	}

	public JSONObject report() {
		JSONObject data = new JSONObject();

		data.put("time", this.time);


		data.put("state", roadMap.report());

		return data;
	}







}
