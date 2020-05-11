package simulator.model;

import java.util.List;
import simulator.misc.Pair;


public class NewSetContClassEvent extends Event{

	private List<Pair<String, Integer>> cs;


	public NewSetContClassEvent(int time, List<Pair<String,Integer>> cs) throws IllegalArgumentException {
		super(time);
		if(cs == null) {
			throw new IllegalArgumentException("Par de contclass es null");
		} else {
			this.cs = cs;
		}

	}


	@Override
	void execute(RoadMap map) throws Exception {

		for(int i = 0; i < cs.size(); i++) {
			if(cs.get(i).getFirst() == null) {
				throw new Exception();
			} else {
				map.getVehicle(cs.get(i).getFirst()).setContaminationClass((cs.get(i).getSecond()));
			}
		}
	}
}
