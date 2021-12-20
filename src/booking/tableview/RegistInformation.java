package booking.tableview;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RegistInformation {
	
	private SimpleStringProperty startDay;
	private SimpleStringProperty startTime;
	private SimpleStringProperty startAirport;
	private SimpleStringProperty arriveAirport;
	private SimpleStringProperty arriveDate;
	private SimpleIntegerProperty emptySeat;
	
	public RegistInformation() {}
	
	public RegistInformation(String startDay, String startTime, String startAirport, String arriveAirport, String arriveDate, int emptySeat) {
		super();
		this.startDay = new SimpleStringProperty(startDay);
		this.startTime = new SimpleStringProperty(startTime);
		this.startAirport = new SimpleStringProperty(startAirport);
		this.arriveAirport = new SimpleStringProperty(arriveAirport);
		this.arriveDate = new SimpleStringProperty(arriveDate);
		this.emptySeat = new SimpleIntegerProperty(emptySeat);
	}

	public String getStartDay() {
		return startDay.get();
	}
	
	public void setStartDay(String startDay) {
		this.startDay.set(startDay);
	}
	
	public String getStartTime() {
		return startTime.get();
	}
	
	public void setStartTime(String startTime) {
		this.startTime.set(startTime);
	}
	
	public String getStartAirport() {
		return startAirport.get();
	}
	
	public void setStartAirport(String startAirport) {
		this.startAirport.set(startAirport);
	}
	
	public String getArriveAirport() {
		return arriveAirport.get();
	}
	
	public void setArriveAirport(String arriveAirport) {
		this.arriveAirport.set(arriveAirport);
	}
	
	public String getArriveDate() {
		return arriveDate.get();
	}
	
	public void setArriveDate(String arriveDate) {
		this.arriveDate.set(arriveDate);
	}
	
	public int getEmptySeat() {
		return emptySeat.get();
	}
	
	public void setEmptySeat(int emptySeat) {
		this.emptySeat.set(emptySeat);
	}
}
