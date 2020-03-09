package simulator.model;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject{
	
	private List<Junction> itinerary;
	private static int maxSpeed;
	private int velocidad_actual;
	private Road carretera;
	private int localizacion;
	private int grado_contaminacion;
	private int contaminacion_total;
	private int distancia_total_recorida;
	private VehicleStatus estado;
	private int longRide;

	Collections.unmodifiableList(new ArrayList<>(itinerary));
	
	Vehicle(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
	
		/*
		 * Contaminacion
		 * 
		 * 
		 * 
		 * 
		 * */
		
		/*
		 * ActualizarLocalizacion
		 * 
		 * 
		 * 
		 * */
	


	
	public void setSpeed(int s) {
		
		if(s<0) {
			throw new Exception();
		}
		else {
			if (s > maxSpeed) {
				this.velocidad_actual = maxSpeed;
			}
			else {
				this.velocidad_actual = s;
			}
		}
	}
	
	public void setContaminationClass(int c) {
		
		if(c < 0 || c > 10) {
			throw new Exception();
		}
		this.grado_contaminacion = c;
	}
	
	
	
	public int getGrado_contaminacion() {
		return grado_contaminacion;
	}


	@Override
	void advance(int time) {
		
		int localizacionAntigua = this.localizacion, l, contaminacion;

		if(estado.equals(VehicleStatus.TRAVELING)) {
			
			//(a)
			if((localizacion + this.velocidad_actual) < longRide)
				this.localizacion = localizacion + this.velocidad_actual;
			else
				this.localizacion = longRide;
			
			//(b)
			l = this.localizacion - localizacionAntigua;
			contaminacion = l * grado_contaminacion;
			contaminacion_total += contaminacion;
			carretera.addContamination(contaminacion);
			//Falta añadir contaminacion a la carretera, invocando alguna clase de road
			
			//(c)
			//if(this.localizacion == longRide) //cambiar llamando a un metodo del road que devuelva la longitud
			//el vehiculo entra en la cola del cruce, llamando algun metodo del Junction. Modificar el estado del vehiculo
		}
	}
	
	public void moveToNextRoad() {
		if(!estado.equals(VehicleStatus.PENDING) ||!estado.equals(VehicleStatus.WAITING)) {
			throw new Exception();
		}
		//Invocar metodos de Road para entrar y salir de la carretera
		//Preguntar a Junction en cual se esta espèrando
		
		
	}
	
	@Override
	public JSONObject report() {
		return null;
	}



	
	
	//Getters y setters
	public int getLocalizacion() {
		return localizacion;
	}


	public void setLocalizacion(int localizacion) {
		this.localizacion = localizacion;
	}


	public int getVelocidad_actual() {
		return velocidad_actual;
	}


	public void setVelocidad_actual(int velocidad_actual) {
		this.velocidad_actual = velocidad_actual;
	}
	
	
	
	
	

	
}