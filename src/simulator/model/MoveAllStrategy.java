package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy {

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {

		//Copiamos y devolvemos la lista con todos los vehiculos de q
		List<Vehicle> lv = new ArrayList<>();
		Collections.copy(lv, q);


		return lv;
	}

}
