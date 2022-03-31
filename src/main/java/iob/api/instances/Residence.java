package iob.api.instances;

public class Residence {
	private String state;
	private String city;
	
	public Residence() {
		
	}

	public Residence(String state, String city) {
		this.state = state;
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Residence [state=" + state + ", city=" + city + "]";
	}
	
}
