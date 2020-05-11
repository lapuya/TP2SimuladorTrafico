package simulator.model;

public class NewCityRoadEvent extends Event{

	private String id;
	private String srcJunc;
	private String destJunc;
	private int length;
	private int co2Limit;
	private int maxSpeed;
	private Weather weather;
	public NewCityRoadEvent(int time, String id, String srcJun, String
			destJunc, int length, int co2Limit, int maxSpeed, Weather weather)
			{
			super(time);
			this.id = id;
			this.srcJunc = srcJun;
			this.destJunc = destJunc;
			this.length = length;
			this.co2Limit = co2Limit;
			this.maxSpeed = maxSpeed;
			this.weather = weather;
			}

	@Override
	void execute(RoadMap map) throws Exception {
		Junction src, dest;
		src = map.getJunction(srcJunc);
		dest = map.getJunction(destJunc);
		CityRoad cr = new CityRoad(this.id, src, dest, this.maxSpeed, this.co2Limit, this.length, this.weather);
		map.addRoad(cr);
	}

}
