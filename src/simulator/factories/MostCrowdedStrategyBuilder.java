package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;
public class Controller {

	private TrafficSimulator sim;
	private Factory <Event> eventsFactory;

	public Controller (TrafficSimulator sim, Factory<Event> eventsFactory) throws Exception {
		if(sim == null || eventsFactory == null) {
			throw new Exception();
		} else {
			this.sim = sim;
			this.eventsFactory = eventsFactory;
		}
	}

	public void loadEvents(InputStream in) throws JSONException {
		JSONObject jo = new JSONObject(new JSONTokener(in));
		JSONArray events = jo.getJSONArray("events");

		for(int i=0;i<events.length();i++) {
			sim.addEvent(eventsFactory.createInstance(events.getJSONObject(i)));
		}

	}

	public void run(int n, OutputStream out) throws Exception {

		//Lo imprimira en el archivo de salida
		if(out!=null) {
			StringBuilder sb = new StringBuilder();
			sb.append("{"+"\"states\": [ ");
			PrintStream p = new PrintStream(out);


			for(int i = 0; i < n; i++) {
				sim.advance(i);
				sb.append(sim.report().toString(3));
				if(i<(n-1)) {
					sb.append(",");
				}


			}

			sb.append("] }");

			p.print(sb.toString());
			p.close();

		} else {

			//salida por consola
			for(int i = 0; i < n; i++) {
				sim.advance(i);
				System.out.println("Paso "+i+" : ");
				System.out.println(sim.report().toString(2));

			}
		}




	}
	public void reset() {
		sim.reset();
	}

}
