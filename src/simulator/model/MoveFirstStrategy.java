package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy {

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {

		List<Vehicle> lv = new ArrayList<Vehicle>();
		if(!q.isEmpty()) {
			//Devolvemos la lista con el primer elemento de q
			lv.add(q.get(0));
		}

		//Devolvemos la lista con el primer elemento de q
		return lv;
	}
}
