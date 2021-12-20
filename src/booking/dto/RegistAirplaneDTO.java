package booking.dto;

public class RegistAirplaneDTO {

	private String airline;
	private String airplaneName;
	private String time;
	private String startAirport;
	private String arriveAirport;
	private String serial;
	
	public RegistAirplaneDTO() {}
	
	public RegistAirplaneDTO(String airline, String airplaneName, String time, String startAirport, String arriveAirport, String serial) {
		this.airline = airline;
		this.airplaneName = airplaneName;
		this.time = time;
		this.startAirport = startAirport;
		this.arriveAirport = arriveAirport;
		this.serial = serial;
	}

	public String getAirline() {
		return airline;
	}
	
	public void setAirline(String airline) {
		this.airline = airline;
	}
	
	public String getAirplaneName() {
		return airplaneName;
	}

	public void setAirplaneName(String airplaneName) {
		this.airplaneName = airplaneName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStartAirport() {
		return startAirport;
	}

	public void setStartAirport(String startAirport) {
		this.startAirport = startAirport;
	}

	public String getArriveAirport() {
		return arriveAirport;
	}

	public void setArriveAirport(String arriveAirport) {
		this.arriveAirport = arriveAirport;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}
}
