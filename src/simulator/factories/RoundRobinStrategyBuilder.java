package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy> {

	public RoundRobinStrategyBuilder() {
		super("round_robin_lss");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		RoundRobinStrategy rrStrategy = null;
		int timeSlot = 1;

		if(data.getInt("timeslot") == 0) {
				rrStrategy = new RoundRobinStrategy(timeSlot);
		} else {
			rrStrategy = new RoundRobinStrategy(data.getInt("timeslot"));
		}

		return rrStrategy;
	}

}
