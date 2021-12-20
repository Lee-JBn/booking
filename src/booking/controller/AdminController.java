package booking.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;

import booking.dao.AirplaneDAO;
import booking.dao.ConnUtil;
import booking.dao.RegistAirplaneDAO;
import booking.dto.AirplaneDTO;
import booking.dto.RegistAirplaneDTO;
import booking.tableview.AirplaneInformation;
import booking.tableview.CancelMember;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class AdminController implements Initializable {

	@FXML
	private HBox airplaneHBox;
	@FXML
	private Button airplaneButton;
	@FXML
	private JFXComboBox airline;
	@FXML
	private HBox informationHBox;
	@FXML
	private JFXComboBox airline2;
	@FXML
	private JFXComboBox airplaneList;

	@FXML
	private JFXComboBox fromAirport;
	@FXML
	private JFXComboBox toAirport;

	@FXML
	private JFXDatePicker fromDay;
	@FXML
	private JFXDatePicker toDay;

	@FXML
	private JFXTimePicker fromTime;
	@FXML
	private JFXTimePicker toTime;

	@FXML
	private TableView information;
	@FXML
	private TableColumn startDate;
	@FXML
	private TableColumn startTime;
	@FXML
	private TableColumn startAirport;
	@FXML
	private TableColumn arriveAirport;
	@FXML
	private TableColumn arriveDate;
	@FXML
	private TableColumn emptySeat;

	private AirplaneDTO dto;
	private AirplaneDAO dao;

	private RegistAirplaneDTO dto1;
	private RegistAirplaneDAO dao1;

	JFXComboBox airplaneComboBox;

	@FXML
	private TableView tableView;
	@FXML
	private TableColumn viewName;
	@FXML
	private TableColumn viewEmail;
	@FXML
	private TableColumn viewPhone;
	@FXML
	private TableColumn viewStart;
	@FXML
	private TableColumn viewStartSeat;
	@FXML
	private TableColumn viewArrive;
	@FXML
	private TableColumn viewArriveSeat;
	@FXML
	private TableColumn viewCancelRequest;

	ObservableList<CancelMember> deleteList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		createList();

		tableView.setOnMouseClicked(event -> {
			deleteList = FXCollections.observableArrayList();
			deleteList.add((CancelMember) tableView.getSelectionModel().getSelectedItem());
		});
	}

	@FXML
	private void airplaneButtonSearchAction() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String tmp = String.valueOf(airline.getValue());

			con = ConnUtil.getConnection();
			pstmt = con.prepareStatement("select * from airplane where airlinename = ?");
			pstmt.setString(1, tmp);
			rs = pstmt.executeQuery();

			airplaneComboBox = new JFXComboBox();
			airplaneComboBox.setStyle("-fx-min-width: 150; -fx-min-height: 30;");
			while (rs.next()) {
				airplaneComboBox.getItems().add(rs.getString("airplanename"));
			}

			airplaneHBox.getChildren().clear();

			Label airplaneLabel = new Label("항공기");
			airplaneLabel.setStyle(
					"-fx-min-width: 100; -fx-min-height: 30; -fx-alignment: CENTER; -fx-font-family: consolas; -fx-font-size: 17;");

			airplaneHBox.getChildren().add(airplaneLabel);
			airplaneHBox.setMargin(airplaneLabel, new Insets(0, 0, 0, 50));
			airplaneHBox.setPadding(new Insets(10, 0, 0, 15));

			airplaneHBox.getChildren().add(airplaneComboBox);

			Button check = new Button("확인");
			check.setStyle("-fx-min-width: 50; -fx-min-height: 30;");
			airplaneHBox.setMargin(airplaneComboBox, new Insets(0, 30, 0, 0));

			check.setOnAction(event -> {
				informationHBox.getChildren().clear();

				dao = new AirplaneDAO();
				ArrayList list = new ArrayList();
				list = dao.search(String.valueOf(airplaneComboBox.getValue()));
				dto = new AirplaneDTO(String.valueOf(list.get(0)), Integer.parseInt(String.valueOf(list.get(1))),
						String.valueOf(list.get(2)));

				Label informationLabel = new Label("항공기 정보");
				informationLabel.setStyle(
						"-fx-min-width: 100; -fx-min-height: 30; -fx-alignment: CENTER; -fx-font-family: consolas; -fx-font-size: 17;");
				informationHBox.setMargin(informationLabel, new Insets(15, 30, 0, 0));
				informationHBox.getChildren().add(informationLabel);

				ObservableList airplaneList = FXCollections
						.observableArrayList(new AirplaneInformation(dto.getAirplaneName(), dto.getSeat()));

				TableView informationTV = new TableView();
				informationTV.setStyle("-fx-min-width: 840; -fx-min-height: 60;");

				TableColumn airplaneName = new TableColumn("항공기 이름");
				informationTV.getColumns().add(airplaneName);
				airplaneName.setCellValueFactory(new PropertyValueFactory("airplaneName"));
				airplaneName.setStyle("-fx-pref-width: 120;");

				TableColumn airplaneSeat = new TableColumn("좌석 수");
				informationTV.getColumns().add(airplaneSeat);
				airplaneSeat.setStyle("-fx-pref-width: 120;");
				airplaneSeat.setCellValueFactory(new PropertyValueFactory("airplaneSeat"));

				informationTV.setItems(airplaneList);

				informationHBox.getChildren().add(informationTV);
			});
			airplaneHBox.getChildren().add(check);
		} catch (Exception e) {
		}
	}

	@FXML
	private void airplaneButtonAddAction() {
		try {
			if (airline2.getValue().equals("Air")) {
				airplaneList.getItems().add(new String("Boeing737"));
			}
		} catch (Exception e) {
		}
	}

	@FXML
	private void timeSetButton() {

		if (fromAirport.getValue().equals(toAirport.getValue()))
			return;

		String airline = String.valueOf(airline2.getValue());
		String airplane = String.valueOf(airplaneList.getValue());
		String startTime = String.valueOf(fromDay.getValue()) + String.valueOf(fromTime.getValue());
		String startAirport = String.valueOf(fromAirport.getValue());
		String arriveAirport = String.valueOf(toAirport.getValue());

		StringBuffer serial = new StringBuffer();

		char[] sAirport = new char[2];
		for (int i = 0; i < 2; i++) {
			sAirport[i] = startAirport.charAt(i);
			serial.append(sAirport[i]);
		}

		char[] aAirport = new char[2];
		for (int i = 0; i < 2; i++) {
			aAirport[i] = arriveAirport.charAt(i);
			serial.append(aAirport[i]);
		}

		int[] timeSerial = new int[15];
		for (int i = 0; i < startTime.length(); i++) {
			if (!(i == 4 || i == 7 || i == 12 || i == 15)) {
				timeSerial[i] = startTime.charAt(i) - 48;
				serial.append(timeSerial[i]);
			}
		}

		dto1 = new RegistAirplaneDTO(airline, airplane, startTime, startAirport, arriveAirport, serial.toString());
		dao1 = new RegistAirplaneDAO();
		dao1.registAirplane(dto1);
	}

	@FXML
	private void searchBtnAddAction() throws ParseException {
		dao1 = new RegistAirplaneDAO();
		ArrayList list = dao1.searchAirplane(String.valueOf(airline.getValue()),
				String.valueOf(airplaneComboBox.getValue()));

		ObservableList flyList = FXCollections.observableArrayList(list);

		startDate.setCellValueFactory(new PropertyValueFactory("startDay"));
		startTime.setCellValueFactory(new PropertyValueFactory("startTime"));
		startAirport.setCellValueFactory(new PropertyValueFactory("startAirport"));
		arriveAirport.setCellValueFactory(new PropertyValueFactory("arriveAirport"));
		arriveDate.setCellValueFactory(new PropertyValueFactory("arriveDate"));
		emptySeat.setCellValueFactory(new PropertyValueFactory("emptySeat"));

		information.setItems(flyList);
	}

	@FXML
	private void cancelBtnAddAction(ActionEvent event) {

		if (deleteList.size() != 0) {
			String deletePhone = deleteList.get(0).getPhone();

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			String[] seatBooker = null;
			String tmpSerial = null;
			String[] seatTmp = null;
			StringBuffer sb = new StringBuffer();

			try {
				con = ConnUtil.getConnection();
				pstmt = con.prepareStatement("select startseat, serial, deleterequest from reservationmember where phone = ?");
				pstmt.setString(1, deletePhone);
				rs = pstmt.executeQuery();

				if (rs.next()) {
					seatBooker = rs.getString("startseat").split("/");
					tmpSerial = rs.getString("serial");
					if(rs.getString("deleterequest") == null) {
						return;
					}
				}

				pstmt.close();
				rs.close();

				pstmt = con.prepareStatement("select * from seat where serial = ?");
				pstmt.setString(1, tmpSerial);
				rs = pstmt.executeQuery();

				if (rs.next()) {
					seatTmp = rs.getString("seat").split("/");
				}

				int count = 0;
				for (int i = 0; i < seatTmp.length; i++) {
					for (int j = 0; j < seatBooker.length; j++) {
						if (seatTmp[i].equals(seatBooker[j])) {
							count++;
							break;
						}
					}
					if (count == 0) {
						sb.append(seatTmp[i] + "/");
					} else {
						count = 0;
					}
				}

				pstmt.close();
				rs.close();

				pstmt = con.prepareStatement("update seat set seat = ? where serial = ?");
				pstmt.setString(1, sb.toString());
				pstmt.setString(2, tmpSerial);
				rs = pstmt.executeQuery();

			} catch (SQLException e) {

			} finally {
				try {
					if (rs != null)
						rs.close();
				} catch (SQLException e) {
				}
				try {
					if (pstmt != null)
						pstmt.close();
				} catch (SQLException e) {
				}
				try {
					if (con != null)
						con.close();
				} catch (SQLException e) {
				}
			}
		}

		if (deleteList.size() != 0) {

			String deletePhone = deleteList.get(0).getPhone();
			
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			String[] seatBooker = null;
			String tmpSerial = null;
			String[] seatTmp = null;
			StringBuffer sb = new StringBuffer();

			try {
				con = ConnUtil.getConnection();
				pstmt = con.prepareStatement("select arriveseat, arriveserial from reservationmember where phone = ?");
				pstmt.setString(1, deletePhone);
				rs = pstmt.executeQuery();

				if (rs.next()) {
					seatBooker = rs.getString("arriveseat").split("/");
					tmpSerial = rs.getString("arriveserial");
				}

				pstmt.close();
				rs.close();

				pstmt = con.prepareStatement("select * from seat where serial = ?");
				pstmt.setString(1, tmpSerial);
				rs = pstmt.executeQuery();

				if (rs.next()) {
					seatTmp = rs.getString("seat").split("/");
				}

				int count = 0;
				for (int i = 0; i < seatTmp.length; i++) {
					for (int j = 0; j < seatBooker.length; j++) {
						if (seatTmp[i].equals(seatBooker[j])) {
							count++;
							break;
						}
					}
					if (count == 0) {
						sb.append(seatTmp[i] + "/");
					} else {
						count = 0;
					}
				}

				pstmt.close();
				rs.close();

				pstmt = con.prepareStatement("update seat set seat = ? where serial = ?");
				pstmt.setString(1, sb.toString());
				pstmt.setString(2, tmpSerial);
				rs = pstmt.executeQuery();

			} catch (SQLException e) {

			} finally {
				try {
					if (rs != null)
						rs.close();
				} catch (SQLException e) {
				}
				try {
					if (pstmt != null)
						pstmt.close();
				} catch (SQLException e) {
				}
				try {
					if (con != null)
						con.close();
				} catch (SQLException e) {
				}
			}
		}
		
		if(deleteList.size() != 0){
			
			String deletePhone = deleteList.get(0).getPhone();
			
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				con = ConnUtil.getConnection();
				pstmt = con.prepareStatement("delete reservationmember where phone = ?");
					pstmt.setString(1, deletePhone);
				pstmt.executeUpdate();
			} catch (SQLException e) {

			} finally {
				try {
					if (rs != null)
						rs.close();
				} catch (SQLException e) {
				}
				try {
					if (pstmt != null)
						pstmt.close();
				} catch (SQLException e) {
				}
				try {
					if (con != null)
						con.close();
				} catch (SQLException e) {
				}
			}
		}
		createList();
	}
		
	@FXML
	private void refreshBtnAddAction() {
		createList();
	}

	private void createList() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs3 = null;
		try {
			con = ConnUtil.getConnection();
			pstmt = con.prepareStatement("select * from airport");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				fromAirport.getItems().add(rs.getString("airportname"));
				toAirport.getItems().add(rs.getString("airportname"));
			}

			pstmt2 = con.prepareStatement("select * from airline");
			rs2 = pstmt2.executeQuery();

			while (rs.next()) {
				airline.getItems().add(rs.getString("airlinename"));
			}

		} catch (Exception e) {
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
			}
			try {
				if (rs2 != null)
					rs2.close();
			} catch (SQLException e) {
			}
			try {
				if (pstmt2 != null)
					pstmt2.close();
			} catch (SQLException e) {
			}
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}

		ObservableList memberList = FXCollections.observableArrayList();

		try {
			con = ConnUtil.getConnection();
			pstmt = con.prepareStatement("select * from reservationmember order by deleterequest nulls last");
			rs = pstmt.executeQuery();

			while (rs.next()) {

				String name = rs.getString("name");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				String start = rs.getString("serial");
				String arrive = rs.getString("arriveSerial");
				String startSeat = rs.getString("startseat");
				String arriveSeat = rs.getString("arriveseat");
				String cancelRequest = new String();

				if (rs.getString("deleterequest") != null) {
					cancelRequest = rs.getString("deleterequest");
				}

				CancelMember tmp = new CancelMember(name, email, phone, start, startSeat, arrive, arriveSeat,
						cancelRequest);

				memberList.add(tmp);
			}
		} catch (SQLException e) {

		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
			}
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}

		viewName.setCellValueFactory(new PropertyValueFactory("name"));
		viewEmail.setCellValueFactory(new PropertyValueFactory("email"));
		viewPhone.setCellValueFactory(new PropertyValueFactory("phone"));
		viewStart.setCellValueFactory(new PropertyValueFactory("start"));
		viewStartSeat.setCellValueFactory(new PropertyValueFactory("startSeat"));
		viewArrive.setCellValueFactory(new PropertyValueFactory("arrive"));
		viewArriveSeat.setCellValueFactory(new PropertyValueFactory("arriveSeat"));
		viewCancelRequest.setCellValueFactory(new PropertyValueFactory("cancelRequest"));

		tableView.setItems(memberList);
	}
}
