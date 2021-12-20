package booking.tableview;

import javafx.beans.property.SimpleStringProperty;

public class FromToStart {
	
	private SimpleStringProperty airline;
	private SimpleStringProperty serial;
	private SimpleStringProperty startAirport;
	private SimpleStringProperty startDate;
	private SimpleStringProperty arriveAirport;
	private SimpleStringProperty arriveDate;
	
	public FromToStart() {}
	
	public FromToStart(String airline, String serial, String startAirport, String startDate, String arriveAirport, String arriveDate) {
		this.airline = new SimpleStringProperty(airline);
		this.serial = new SimpleStringProperty(serial);
		this.startAirport = new SimpleStringProperty(startAirport);
		this.startDate = new SimpleStringProperty(startDate);
		this.arriveAirport = new SimpleStringProperty(arriveAirport);
		this.arriveDate = new SimpleStringProperty(arriveDate);
	}

	public String getAirline() {
		return airline.get();
	}

	public void setAirline(String airline) {
		this.airline.set(airline);
	}

	public String getSerial() {
		return serial.get();
	}

	public void setSerial(String serial) {
		this.serial.set(serial);
	}

	public String getStartAirport() {
		return startAirport.get();
	}

	public void setStartAirport(String startAirport) {
		this.startAirport.set(startAirport);
	}

	public String getStartDate() {
		return startDate.get();
	}

	public void setStartDate(String startDate) {
		this.startDate.set(startDate);
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

	
	
}
