package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy {
	int timeSlot;

	public RoundRobinStrategy(int timeSlot){
		this.timeSlot = timeSlot;
	}

	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		//Si la lista de carreteras entrantes esta vacio
		if(roads.isEmpty()) {
			return -1;
		} else if(currGreen == -1) {		//Si todos estan en rojo, ponemos el primero a verde
			return 0;
		} else if((currTime-lastSwitchingTime < timeSlot)) {
			return currGreen;				//Dejamos los semafros como estan
		}

		return (currGreen + 1)%roads.size(); //indice de la siguiente carretera recorriendola de forma circular


	}

}
