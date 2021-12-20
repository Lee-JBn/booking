package booking.dto;

public class AirplaneDTO {

	private String airplaneName;
	private int seat;
	private String airlineName;
	
	public AirplaneDTO() {}
	
	public AirplaneDTO(String airplaneName, int seat, String airlineName) {
		this.airplaneName = airplaneName;
		this.seat = seat;
		this.airlineName = airlineName;
	}
	
	public String getAirplaneName() {
		return airplaneName;
	}
	public void setAirplaneName(String airplaneName) {
		this.airplaneName = airplaneName;
	}
	public int getSeat() {
		return seat;
	}
	public void setSeat(int seat) {
		this.seat = seat;
	}
	public String getAirlineName() {
		return airlineName;
	}
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}
	
	
}
