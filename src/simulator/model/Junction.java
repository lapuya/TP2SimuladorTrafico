package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {

	private List<Road> incomingRoads;
	private Map<Junction, Road> outgoingRoadsMap;
	private List<List<Vehicle>> queueList;
	private Map<Road,List<Vehicle>> roadQueueMap;
	private int greenLightIndex;
	private int lightLastStep = 0;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;

	//Para la proxima practica
	private int x;
	private int y;



	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int x, int y) throws Exception  {
		super(id);
		if(lsStrategy == null || dqStrategy == null || x < 0 || y < 0) {
			throw new Exception();
		} else {
			this.lsStrategy = lsStrategy;
			this.dqStrategy = dqStrategy;
			this.x = x;
			this.y = y;
			greenLightIndex = -1;
			outgoingRoadsMap = new HashMap <Junction, Road>();
			roadQueueMap = new HashMap <Road, List<Vehicle>>();
			queueList = new ArrayList<List<Vehicle>>();
			incomingRoads = new ArrayList<>();
		}
	}

	void addIncomingRoad(Road r) throws Exception {
		//comprobar que el destino de r es este
		if(r.getDestJunc() != this) {
			throw new Exception();
		} else {
			incomingRoads.add(r);

			//Crear nueva cola (linkedlist)
			LinkedList<Vehicle> colaV = new LinkedList<Vehicle>();

			//Añadir a la lista de colas
			queueList.add(colaV);

			//Añadir al mapa carretera-cola
			roadQueueMap.put(r, colaV);
		}

	}

	void addOutGoingRoad (Road r) throws Exception {

		//Ninguna carretera va desde este cruce al cruce de R y R es una carretera saliente
		if(this != r.getDestJunc() && r.getSrcJunc() != this) {
			throw new Exception();
		} else {
			outgoingRoadsMap.put(r.getDestJunc(), r);
		}

	}

	void enter(Vehicle v) throws Exception {
		//Añadir el vehiculo a la cola de su carretera
		v.getRoad().enter(v);

	}

	Road roadTo(Junction j) {
		//Devolver la carretera que va al cruce J
		Road r = outgoingRoadsMap.get(j);

		return r;

	}

	//Añade el vehiculo a la cola de este cruce
	public void addVehicleToJunctionQueue(Vehicle v) {
		//Preguntar a este cruce la posicion de esta carretera entrante en la lista
		int x = this.incomingRoads.indexOf(v.getRoad());
		queueList.get(x).add(v);
	}


	//Métodos heredados de Simulated Objet --------------------------------------------------------------------------

	@Override
	void advance(int time) throws Exception {

		if(greenLightIndex != - 1) {
			List<Vehicle> vehicleList = new ArrayList<Vehicle>();
			vehicleList = this.dqStrategy.dequeue(queueList.get(greenLightIndex));

			for(int i = 0; i < vehicleList.size(); i++) {
				vehicleList.get(i).moveToNextRoad();
			}

			for(int i = 0; i < vehicleList.size(); i++) {
					queueList.get(greenLightIndex).remove(vehicleList.get(i));
			}
		}


		/*if(!queueList.isEmpty()) {
			if(greenLightIndex != -1 && greenLightIndex < queueList.size()) {

				List<Vehicle> vehicleList = new ArrayList<Vehicle>();
				if(queueList.get(greenLightIndex).size() > 0) {
					vehicleList = this.dqStrategy.dequeue(queueList.get(greenLightIndex));


					//Pide a los vehiculos de la lsita anterior que se muevan a las siguientes carreteras
					for(int i = 0; i < vehicleList.size(); i++) {
						vehicleList.get(i).moveToNextRoad();
					}

					//Eliminar los vehiculos de esa cola
					for(int i = 0; i < vehicleList.size(); i++) {
						queueList.get(greenLightIndex).remove(vehicleList.get(i));
					}
				}
			}*/
		int greenIndex = this.lsStrategy.chooseNextGreen(incomingRoads, queueList, greenLightIndex, lightLastStep, time);

		//Si es distinto, cambiamos el indice del semaforo y del ultimo paso del cambio de semaforo
		if(greenIndex != greenLightIndex) {
			greenLightIndex = greenIndex;
			lightLastStep = time;
		}



}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		JSONObject report = new JSONObject();
		report.put("id", this._id );

		JSONArray queueReport = new JSONArray();


		if(greenLightIndex == -1) {
			JSONArray vList = new JSONArray();
			report.put("green", "none");
			report.put("queues", vList);
		} else {

			if(incomingRoads.size() > 0 && greenLightIndex < incomingRoads.size()) {
				report.put("green", incomingRoads.get(greenLightIndex).getId());


				for(int i = 0; i < incomingRoads.size(); i++) {
					JSONArray vList = new JSONArray();
					JSONObject queue = new JSONObject();
					String id = incomingRoads.get(i).getId();

					if(!queueList.get(i).isEmpty()) {
						for(int j = 0; j < queueList.get(i).size(); j++) {
							vList.put(queueList.get(i).get(j).getId());
						}
					}

					queue.put("road", id);
					queue.put("vehicles", vList);
					queueReport.put(queue);

				}
				report.put("queues", queueReport);


			}
		}
		return report;
	}

}
