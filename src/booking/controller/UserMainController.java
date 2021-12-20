package booking.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;

import booking.dto.UserMember;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class UserMainController implements Initializable {
	
	@FXML private Button nextBtn;
	
	@FXML private BorderPane userMainBorder;
	
	@FXML private JFXComboBox startStartAirport;
	@FXML private JFXComboBox startArriveAirport;
	@FXML private JFXComboBox arriveStartAirport;
	@FXML private JFXComboBox arriveArriveAirport;
	
	@FXML private JFXDatePicker startDate;
	@FXML private JFXDatePicker arriveDate;
	
	@FXML private JFXComboBox adultNum;
	@FXML private JFXComboBox childrenNum;
	@FXML private JFXComboBox babyNum;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	@FXML
	private void nextBtnAction(ActionEvent event) {
		if(String.valueOf(startStartAirport.getValue()).equals(String.valueOf(startArriveAirport.getValue())))
			return;
		else if(String.valueOf(arriveStartAirport.getValue()).equals(String.valueOf(arriveArriveAirport.getValue())))
			return;
		else if(String.valueOf(startStartAirport.getValue()).equals(String.valueOf(arriveStartAirport.getValue())))
			return;
		else if(String.valueOf(startArriveAirport.getValue()).equals(String.valueOf(arriveArriveAirport.getValue())))
			return;
		
		String ssAirport = String.valueOf(startStartAirport.getValue());
		String saAirport = String.valueOf(startArriveAirport.getValue());
		String asAirport = String.valueOf(arriveStartAirport.getValue());
		String aaAirport = String.valueOf(arriveArriveAirport.getValue());
		String sDate = String.valueOf(startDate.getValue());
		String aDate = String.valueOf(arriveDate.getValue());
		int adult = Integer.parseInt(String.valueOf(adultNum.getValue()));
		int children = Integer.parseInt(String.valueOf(childrenNum.getValue()));
		int baby = Integer.parseInt(String.valueOf(babyNum.getValue()));
		
		UserMember um = new UserMember(ssAirport, saAirport, sDate, asAirport, aaAirport, aDate, adult, children, baby);
		
		FxmlLoader object = new FxmlLoader();
		Pane view = object.getPage("fromTo");
		userMainBorder.setCenter(view);
	}
	
	@FXML
	private void checkBtnAddAction(ActionEvent event) {
		FxmlLoader object = new FxmlLoader();
		Pane view = object.getPage("checkBooking");
		userMainBorder.setCenter(view);
	}
}
