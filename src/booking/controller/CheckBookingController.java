package booking.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import booking.dao.ConnUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class CheckBookingController implements Initializable {

	@FXML private BorderPane mainBorder; 
	@FXML private JFXComboBox firstNum;
	@FXML private JFXTextField secondNum;
	@FXML private JFXTextField thirdNum;
	@FXML private Label nameLabel;
	@FXML private Label emailLabel;
	@FXML private Label phoneLabel;
	@FXML private Label startAirport;
	@FXML private Label arriveAirport;
	@FXML private Label startAirport1;
	@FXML private Label arriveAirport1;
	@FXML private Label startTime; 
	@FXML private Label arriveTime;
	@FXML private Label startSeat;
	@FXML private Label arriveSeat;
	@FXML private Label startDate;
	@FXML private Label arriveDate;
	@FXML private Label startAirline;
	@FXML private Label arriveAirline;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	@FXML
	private void returnBtnAddAction(ActionEvent event) {
		FxmlLoader object = new FxmlLoader();
		Pane view = object.getPage("userMain");
		mainBorder.setCenter(view);
	}
	
	@FXML
	private void checkBtnAddAction(ActionEvent event) {
		
		String phoneNum = firstNum.getValue() + "-" + secondNum.getText() + "-" + thirdNum.getText();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs3 = null;
		
		try {
			con = ConnUtil.getConnection();
			pstmt = con.prepareStatement("select * from reservationmember where phone = ?");
				pstmt.setString(1, phoneNum);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				nameLabel.setText(rs.getString("name"));
				emailLabel.setText(rs.getString("email"));
				phoneLabel.setText(rs.getString("phone"));
				String tmp1 = rs.getString("startseat").replaceAll("/", ", ");
				startSeat.setText(tmp1.substring(0, tmp1.length()-2));
				String tmp2 = rs.getString("arriveseat").replaceAll("/", ", ");
				arriveSeat.setText(tmp2.substring(0, tmp2.length()-2));
				
				pstmt2 = con.prepareStatement("select * from airplaneregist where serial = ?");
					pstmt2.setString(1, rs.getString("serial"));
				rs2 = pstmt2.executeQuery();
				
				if(rs2.next()) {
					String[] tmp = rs2.getString("time").split(" ");
					startDate.setText(tmp[0]);
					startTime.setText(tmp[1].substring(0, 5));
					startAirline.setText(rs2.getString("airlinename"));
					startAirport.setText(rs2.getString("startairport"));
					startAirport1.setText(rs2.getString("arriveairport"));
				}
				
				pstmt3 = con.prepareStatement("select * from airplaneregist where serial = ?");
					pstmt3.setString(1, rs.getString("arriveserial"));
				rs3 = pstmt3.executeQuery();
				
				if(rs3.next()) {
					String[] tmp = rs3.getString("time").split(" ");
					arriveDate.setText(tmp[0]);
					arriveTime.setText(tmp[1].substring(0, 5));
					arriveAirline.setText(rs3.getString("airlinename"));
					arriveAirport.setText(rs3.getString("startairport"));
					arriveAirport1.setText(rs3.getString("arriveairport"));
				}
			}
		} catch(SQLException e) {
			
		} finally {
			try { if(rs != null) rs.close(); } catch(SQLException e) {}
			try { if(rs2 != null) rs2.close(); } catch(SQLException e) {}
			try { if(rs3 != null) rs3.close(); } catch(SQLException e) {}
			try { if(pstmt != null) pstmt.close(); } catch(SQLException e) {}
			try { if(pstmt2 != null) pstmt2.close(); } catch(SQLException e) {}
			try { if(pstmt3 != null) pstmt3.close(); } catch(SQLException e) {}
			try { if(con != null) con.close(); } catch(SQLException e) {}
		}
	}
	
	@FXML
	private void bookingCancelBtnAddAction(ActionEvent event) {
		
		String phoneNum = firstNum.getValue() + "-" + secondNum.getText() + "-" + thirdNum.getText();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ConnUtil.getConnection();
			pstmt = con.prepareStatement("update reservationmember set deleterequest = 'true' where phone = ?");
				pstmt.setString(1, phoneNum);
			int i = pstmt.executeUpdate();
			System.out.println(i + " 삭제 요청 성공!");
			
		} catch(SQLException e) {
			
		} finally {
			try { if(pstmt != null) pstmt.close(); } catch(SQLException e) {}
			try { if(con != null) con.close(); } catch(SQLException e) {}
		}
	}
}
