package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	int timeSlot;
	public MostCrowdedStrategy(int timeSlot){
		this.timeSlot = timeSlot;
	}
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		if(roads.isEmpty()) {
			return -1;
		} else if (currGreen == -1) {
			int mayor = 0;
			for(int i = 0; i < qs.size(); i++) {
				if(qs.get(i).size() > qs.get(mayor).size()) {
					mayor = i;
				}
			}

			return mayor;
		} else if (currTime - lastSwitchingTime < timeSlot) {
			return currGreen;
		}



		int i = currGreen+1%qs.size(), mayor = currGreen+1%qs.size() ;
		while(i !=currGreen) {
			if(qs.get(i).size() > qs.get(mayor).size()) {
				mayor = i;
			}
			i+=currGreen+1%qs.size();
		}
		return mayor;
	}

}
