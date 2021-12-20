package booking.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import booking.dao.ConnUtil;
import booking.dto.UserMember;
import booking.tableview.FromToStart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class FromToController implements Initializable {

	@FXML private Button nextBtn;
	@FXML private Button cancelBtn;
	@FXML private BorderPane fromToBorder;
	
	@FXML private TableView<FromToStart> startTV;
		@FXML private TableColumn startAirline;
		@FXML private TableColumn startSerial;
		@FXML private TableColumn startAirport;
		@FXML private TableColumn startTime;
		@FXML private TableColumn startArriveAirport;
		@FXML private TableColumn startArriveTime;
	
	@FXML private TableView<FromToStart> arriveTV;
		@FXML private TableColumn arriveAirline;
		@FXML private TableColumn arriveSerial;
		@FXML private TableColumn arriveStartAirport;
		@FXML private TableColumn arriveStartTime;
		@FXML private TableColumn arriveAirport;
		@FXML private TableColumn arriveTime;
	
	@FXML private TableView<FromToStart> fromToTV;
		@FXML private TableColumn fromToAirline;
		@FXML private TableColumn fromToSerial;
		@FXML private TableColumn fromToStart;
		@FXML private TableColumn fromToStartDate;
		@FXML private TableColumn fromToArrive;
		@FXML private TableColumn fromToArriveDate;
		
	private static String serial;
	private static String serialArrive;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		String[] tmp = UserMember.getsDate().substring(2).split("-");
		String strDate = "%" + tmp[0] + tmp[1] + tmp[2] + "%";
		
		String engSAirport = null;
		String engAAirport = null;
		
		switch(UserMember.getSsAirport()){
			case "인천": engSAirport = "Incheon"; break;
			case "오사카": engSAirport = "Osaka"; break;
			case "LA": engSAirport = "Los Angeles"; break;
		}
		switch(UserMember.getSaAirport()){
			case "인천": engAAirport = "Incheon"; break;
			case "오사카": engAAirport = "Osaka"; break;
			case "LA": engAAirport = "Los Angeles"; break;
		}
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ObservableList<FromToStart> list = FXCollections.observableArrayList();
		
		try {
			con = ConnUtil.getConnection();
			pstmt = con.prepareStatement("select * from airplaneregist where serial like ? and startAirport = ? and arriveAirport = ?");
				pstmt.setString(1, strDate);
				pstmt.setString(2, engSAirport);
				pstmt.setString(3, engAAirport);
			rs = pstmt.executeQuery();
			
			FromToStart start;
			String airline = null;
			String serial = null;
			String sAirport = null;
			Date sTime = null;
			String aAirport = null;
			Date aTime = null;
			
			while(rs.next()) {
				
				airline = rs.getString("airlinename");
				serial = rs.getString("serial");
				sAirport = rs.getString("startairport");
				aAirport = rs.getString("arriveairport");
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date tmpDate = dateFormat.parse(rs.getNString("time"));
				String sSTime = null;
				String sATime = null;
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(tmpDate);
				sSTime = dateFormat.format(cal.getTime());
				
				switch(sAirport) {
					case "Incheon": 
						switch(aAirport) {
							case "Osaka": 
								cal.setTime(tmpDate);
								cal.add(Calendar.HOUR, 2);
								sATime = dateFormat.format(cal.getTime());
								break;
							case "Los Angeles":
								cal.setTime(tmpDate);
								cal.add(Calendar.HOUR, -4);
								sATime = dateFormat.format(cal.getTime());
								break;
						}
						break;
					case "Osaka":
						switch(aAirport) {
							case "Incheon":
								cal.setTime(tmpDate);
								cal.add(Calendar.HOUR, 2);
								sATime = dateFormat.format(cal.getTime());
								break;
							case "Los Angeles":
								cal.setTime(tmpDate);
								cal.add(Calendar.HOUR, 36);
								sATime = dateFormat.format(cal.getTime());
								break;
						}
						break;
					case "Los Angeles":
						switch(aAirport) {
							case "Osaka":
								cal.setTime(tmpDate);
								cal.add(Calendar.HOUR, 60);
								sATime = dateFormat.format(cal.getTime());
								break;
							case "Incheon":
								cal.setTime(tmpDate);
								cal.add(Calendar.HOUR, 30);
								sATime = dateFormat.format(cal.getTime());
								break;
						}
						break;
				}
				
				start = new FromToStart(airline, serial, sAirport, sSTime, aAirport, sATime);
				
				list.add(start);
			}
		} catch(SQLException | ParseException e) {
		} finally {
			try { if(rs != null) rs.close(); } catch(SQLException e) {}
			try { if(pstmt != null) pstmt.close(); } catch(SQLException e) {}
			try { if(con != null) con.close(); } catch(SQLException e) {}
		}
				
		startAirline.setCellValueFactory(new PropertyValueFactory("airline"));      
		startSerial.setCellValueFactory(new PropertyValueFactory("serial"));       
		startAirport.setCellValueFactory(new PropertyValueFactory("startAirport"));      
		startTime.setCellValueFactory(new PropertyValueFactory("startDate"));         
		startArriveAirport.setCellValueFactory(new PropertyValueFactory("arriveAirport"));
		startArriveTime.setCellValueFactory(new PropertyValueFactory("arriveDate"));   
		
		startTV.setItems(list);
		
		// 도착 -----------------------------------------------------------------------------
		
		String[] tmp2 = UserMember.getaDate().substring(2).split("-");
		String strDate2 = "%" + tmp2[0] + tmp2[1] + tmp2[2] + "%";
		
		String engSAirport2 = null;
		String engAAirport2 = null;
		
		switch(UserMember.getAsAirport()){
			case "인천": engSAirport2 = "Incheon"; break;
			case "오사카": engSAirport2 = "Osaka"; break;
			case "LA": engSAirport2 = "Los Angeles"; break;
		}
		switch(UserMember.getAaAirport()){
			case "인천": engAAirport2 = "Incheon"; break;
			case "오사카": engAAirport2 = "Osaka"; break;
			case "LA": engAAirport2 = "Los Angeles"; break;
		}

		Connection con2 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;

		ObservableList<FromToStart> list2 = FXCollections.observableArrayList();
		
		try {
			con2 = ConnUtil.getConnection();
			pstmt2 = con2.prepareStatement("select * from airplaneregist where serial like ? and startAirport = ? and arriveAirport = ?");
				pstmt2.setString(1, strDate2);
				pstmt2.setString(2, engSAirport2);
				pstmt2.setString(3, engAAirport2);
			rs2 = pstmt2.executeQuery();
			
			FromToStart start2;
			String airline2 = null;
			String serial2 = null;
			String sAirport2 = null;
			Date sTime2 = null;
			String aAirport2 = null;
			Date aTime2 = null;
			
			while(rs2.next()) {

				airline2 = rs2.getString("airlinename");
				serial2 = rs2.getString("serial");
				sAirport2 = rs2.getString("startairport");
				aAirport2 = rs2.getString("arriveairport");
				
				SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date tmpDate2 = dateFormat2.parse(rs2.getNString("time"));
				String sSTime2 = null;
				String sATime2 = null;
				
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(tmpDate2);
				sSTime2 = dateFormat2.format(cal2.getTime());
				
				switch(sAirport2) {
					case "Incheon": 
						switch(aAirport2) {
							case "Osaka": 
								cal2.setTime(tmpDate2);
								cal2.add(Calendar.HOUR, 2);
								sATime2 = dateFormat2.format(cal2.getTime());
								break;
							case "Los Angeles":
								cal2.setTime(tmpDate2);
								cal2.add(Calendar.HOUR, -4);
								sATime2 = dateFormat2.format(cal2.getTime());
								break;
						}
						break;
					case "Osaka":
						switch(aAirport2) {
							case "Incheon":
								cal2.setTime(tmpDate2);
								cal2.add(Calendar.HOUR, 2);
								sATime2 = dateFormat2.format(cal2.getTime());
								break;
							case "Los Angeles":
								cal2.setTime(tmpDate2);
								cal2.add(Calendar.HOUR, 36);
								sATime2 = dateFormat2.format(cal2.getTime());
								break;
						}
						break;
					case "Los Angeles":
						switch(aAirport2) {
							case "Osaka":
								cal2.setTime(tmpDate2);
								cal2.add(Calendar.HOUR, 60);
								sATime2 = dateFormat2.format(cal2.getTime());
								break;
							case "Incheon":
								cal2.setTime(tmpDate2);
								cal2.add(Calendar.HOUR, 30);
								sATime2 = dateFormat2.format(cal2.getTime());
								break;
						}
						break;
				}
				
				start2 = new FromToStart(airline2, serial2, sAirport2, sSTime2, aAirport2, sATime2);
				
				list2.add(start2);
			}
		} catch(SQLException | ParseException e) {
		} finally {
			try { if(rs2 != null) rs2.close(); } catch(SQLException e) {}
			try { if(pstmt2 != null) pstmt2.close(); } catch(SQLException e) {}
			try { if(con2 != null) con2.close(); } catch(SQLException e) {}
		}
				
		arriveAirline.setCellValueFactory(new PropertyValueFactory("airline"));      
		arriveSerial.setCellValueFactory(new PropertyValueFactory("serial"));       
		arriveStartAirport.setCellValueFactory(new PropertyValueFactory("startAirport"));      
		arriveStartTime.setCellValueFactory(new PropertyValueFactory("startDate"));         
		arriveAirport.setCellValueFactory(new PropertyValueFactory("arriveAirport"));
		arriveTime.setCellValueFactory(new PropertyValueFactory("arriveDate"));   
		
		arriveTV.setItems(list2);
		
		startTV.setOnMouseClicked(event->{
			if(arriveTV.getSelectionModel().getSelectedItem() != null) {
				ObservableList<FromToStart> list3 = FXCollections.observableArrayList();
				list3.add(startTV.getSelectionModel().getSelectedItem());
				list3.add(arriveTV.getSelectionModel().getSelectedItem());
				
				fromToAirline.setCellValueFactory(new PropertyValueFactory("airline"));   
				fromToSerial.setCellValueFactory(new PropertyValueFactory("serial"));    
				fromToStart.setCellValueFactory(new PropertyValueFactory("startAirport"));     
				fromToStartDate.setCellValueFactory(new PropertyValueFactory("startDate")); 
				fromToArrive.setCellValueFactory(new PropertyValueFactory("arriveAirport"));    
				fromToArriveDate.setCellValueFactory(new PropertyValueFactory("arriveDate"));
				
				fromToTV.setItems(list3);
			}
		});
		
		arriveTV.setOnMouseClicked(event->{
			if(startTV.getSelectionModel().getSelectedItem() != null) {
				ObservableList<FromToStart> list3 = FXCollections.observableArrayList();
				list3.add(startTV.getSelectionModel().getSelectedItem());
				list3.add(arriveTV.getSelectionModel().getSelectedItem());
				
				fromToAirline.setCellValueFactory(new PropertyValueFactory("airline"));   
				fromToSerial.setCellValueFactory(new PropertyValueFactory("serial"));    
				fromToStart.setCellValueFactory(new PropertyValueFactory("startAirport"));     
				fromToStartDate.setCellValueFactory(new PropertyValueFactory("startDate")); 
				fromToArrive.setCellValueFactory(new PropertyValueFactory("arriveAirport"));    
				fromToArriveDate.setCellValueFactory(new PropertyValueFactory("arriveDate"));
				
				fromToTV.setItems(list3);
			}
		});
	}

	@FXML
	private void nextBtnAction(ActionEvent event) {
		
		ObservableList<FromToStart> list = FXCollections.observableArrayList();
		list.add(startTV.getSelectionModel().getSelectedItem());
		
		serial = list.get(0).getSerial();
		
		ObservableList<FromToStart> list2 = FXCollections.observableArrayList();
		list2.add(arriveTV.getSelectionModel().getSelectedItem());
		
		serialArrive = list2.get(0).getSerial();
		
		FxmlLoader object = new FxmlLoader();
		Pane view = object.getPage("terms");
		fromToBorder.setCenter(view);
	}
	
	@FXML
	private void cancelBtnAction(ActionEvent event) {
		FxmlLoader object = new FxmlLoader();
		Pane view = object.getPage("userMain");
		fromToBorder.setCenter(view);
	}
	
	public static String getSerial() {
		return serial;
	}
	
	public static String getSerialArrive() {
		return serialArrive;
	}
}













