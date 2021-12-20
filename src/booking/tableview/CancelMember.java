package booking.tableview;

import javafx.beans.property.SimpleStringProperty;

public class CancelMember {

	private SimpleStringProperty name;
	private SimpleStringProperty email;
	private SimpleStringProperty phone;
	private SimpleStringProperty start;
	private SimpleStringProperty startSeat;
	private SimpleStringProperty arrive;
	private SimpleStringProperty arriveSeat;
	private SimpleStringProperty cancelRequest;
	
	public CancelMember() {}
	
	public CancelMember(String name, String email, String phone, String start, String startSeat, String arrive, String arriveSeat, String cancelRequest) {
		this.name = new SimpleStringProperty(name);
		this.email = new SimpleStringProperty(email);
		this.phone = new SimpleStringProperty(phone);
		this.start = new SimpleStringProperty(start);		
		String tmp1 = startSeat.replaceAll("/", ", ");
		this.startSeat = new SimpleStringProperty(tmp1.substring(0, tmp1.length()-2));
		this.arrive = new SimpleStringProperty(arrive);
		String tmp2 = arriveSeat.replaceAll("/", ", ");
		this.arriveSeat = new SimpleStringProperty(tmp2.substring(0, tmp2.length()-2));
		this.cancelRequest = new SimpleStringProperty(cancelRequest);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public String getEmail() {
		return email.get();
	}

	public void setEmail(String email) {
		this.email.set(email);;
	}

	public String getPhone() {
		return phone.get();
	}

	public void setPhone(String phone) {
		this.phone.set(phone);;
	}

	public String getStart() {
		return start.get();
	}

	public void setStart(String start) {
		this.start.set(start);
	}

	public String getStartSeat() {
		return startSeat.get();
	}

	public void setStartSeat(String startSeat) {
		this.startSeat.set(startSeat);
	}

	public String getArrive() {
		return arrive.get();
	}

	public void setArrive(String arrive) {
		this.arrive.set(arrive);
	}

	public String getArriveSeat() {
		return arriveSeat.get();
	}

	public void setArriveSeat(String arriveSeat) {
		this.arriveSeat.set(arriveSeat);
	}

	public String getCancelRequest() {
		return cancelRequest.get();
	}

	public void setCancelRequest(String cancelRequest) {
		this.cancelRequest.set(cancelRequest);
	}
	
	
}
