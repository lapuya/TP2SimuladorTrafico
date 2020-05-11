package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event{

	private String id;
	private int contClass;
	private int maxSpeed;
	private List<String> itinerary;

	public NewVehicleEvent(int time, String id, int maxSpeed, int
			contClass, List<String> itinerary) {
			super(time);
			this.id = id;
			this.maxSpeed = maxSpeed;
			this.contClass = contClass;
			this.itinerary = itinerary;
			}

	@Override
	void execute(RoadMap map) throws Exception {

		List <Junction> listJunc = new ArrayList<Junction> ();
		Junction j;

		for(int i = 0; i < itinerary.size(); i++) {
			j = map.getJunction(itinerary.get(i));
			listJunc.add(j);
		}

		Vehicle car = new Vehicle(this.id, this.maxSpeed, this.contClass, listJunc);
		map.addVehicle(car);//añadir a la lista de coches creada en map.

		//Empezar su itinerario
		car.moveToNextRoad();
	}

}
