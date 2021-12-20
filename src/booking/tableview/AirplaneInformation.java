package booking.tableview;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AirplaneInformation {
	
	private SimpleStringProperty airplaneName;
	private SimpleIntegerProperty airplaneSeat;

	public AirplaneInformation() {
		this.airplaneName = new SimpleStringProperty();
	}

	public AirplaneInformation(String airplaneName, int airplaneSeat) {
		this.airplaneName = new SimpleStringProperty(airplaneName);
		this.airplaneSeat = new SimpleIntegerProperty(airplaneSeat);
	}

	public String getAirplaneName() {
		return airplaneName.get();
	}

	public void setAirplaneName(String airplaneName) {
		this.airplaneName.set(airplaneName);
	}

	public int getAirplaneSeat() {
		return airplaneSeat.get();
	}

	public void setAirplaneSeat(int airplaneSeat) {
		this.airplaneSeat.set(airplaneSeat);
	}
	
	
}
