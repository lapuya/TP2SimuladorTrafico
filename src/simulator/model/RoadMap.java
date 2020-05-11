package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {

	private List<Junction> jList;
	private List<Road> rList;
	private List<Vehicle> vList;
	private Map<String,Junction> jMap;
	private Map <String,Road> rMap;
	private Map<String,Vehicle> vMap;



	protected RoadMap(List<Junction> jList, List<Road> rList, List<Vehicle> vList, Map<String, Junction> jMap,
			Map<String, Road> rMap, Map<String, Vehicle> vMap) {
		this.jList = jList;
		this.rList = rList;
		this.vList = vList;
		this.jMap = jMap;
		this.rMap = rMap;
		this.vMap = vMap;
	}


	void addJunction(Junction j) {
		if(!jMap.containsKey(j.getId())) {
			jList.add(j);
		}
	}

	void addRoad(Road r) throws Exception {
		if(!rMap.containsKey(r.getId()) || jMap.containsValue(r.getSrcJunc()) || jMap.containsValue(r.getDestJunc())) {
			rList.add(r);
		} else {
			throw new Exception();
		}
	}

	void addVehicle(Vehicle v) {
		if(!vMap.containsKey(v.getId())) {
			vList.add(v);
		}
	}

	public Junction getJunction(String id) {
		int i = 0, pos = 0;
		boolean encontrado = false;
		while(i < jList.size() && !encontrado) {
			if(id.equals(jList.get(i).getId())) {
			encontrado = true;  pos = i;
			}
			else { i++;}
		}
		if(i < jList.size()) {
			return jList.get(pos);
		} else {
			return null;
		}

	}


	public Road getRoad(String id) {
		int i = 0, pos = 0;
		boolean encontrado = false;
		while(i < rList.size() && id != rList.get(i).getId()) {
			if(id.equals(rList.get(i).getId())) {
				encontrado = true;
				pos = i;
			}
			i++;
		}
		if(encontrado) {
			return rList.get(pos);
		} else {
			return null;
		}
	}

	public Vehicle getVehicle(String id) {
		int i = 0, pos = 0;
		boolean encontrado = false;
		while(i < vList.size() && !encontrado) {
			if(id.equals(vList.get(i).getId())) {
				encontrado = true;
				pos = i;
			}
			i++;
		}
		if(encontrado) {
			return vList.get(pos);
		} else {
			return null;
		}
	}

	//Métodos para devolver las listas solo lectura ----------------------------------------------------------------------------------

	//De Junction
	public List<Junction> getJunctions(){
		List<Junction> onlyReadJunctionsList = new ArrayList<Junction>();
		onlyReadJunctionsList = jList;
		onlyReadJunctionsList = Collections.unmodifiableList(onlyReadJunctionsList);

		return onlyReadJunctionsList;
	}

	//De Roads
	public List<Road> getRoads(){
		List<Road> onlyReadRoadsList = new ArrayList<Road>();
		onlyReadRoadsList = rList;
		onlyReadRoadsList = Collections.unmodifiableList(onlyReadRoadsList);

		return onlyReadRoadsList;
	}

	//De vehicles
	public List <Vehicle>getVehicles(){
		List<Vehicle> onlyReadVehiclesList = new ArrayList<Vehicle>();
		onlyReadVehiclesList = vList;
		onlyReadVehiclesList = Collections.unmodifiableList(onlyReadVehiclesList);

		return onlyReadVehiclesList;
	}

	//Limpiar listas y mapas
	 void reset() {
		jList.clear();
		rList.clear();
		vList.clear();
		jMap.clear();
		rMap.clear();
		vMap.clear();

	}

	public JSONObject report() {
		JSONObject data = new JSONObject();

		//para road
		JSONArray roadReport = new JSONArray();
		for(int i = 0; i < rList.size(); i++) {
			roadReport.put(rList.get(i).report());
		}
		data.put("roads",roadReport);

		//para vehicles
		JSONArray vehiclesReport = new JSONArray();
		for(int i = 0; i < vList.size(); i++) {
			vehiclesReport.put(vList.get(i).report());
		}

		data.put("vehicles",vehiclesReport);

		//para junctions
		JSONArray junctionsReport = new JSONArray();
		for(int i = 0; i < jList.size(); i++) {
			junctionsReport.put(jList.get(i).report());
		}
		data.put("junctions",junctionsReport);

		return data;
	}
}
