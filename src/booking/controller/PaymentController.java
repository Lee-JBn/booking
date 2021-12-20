package booking.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import booking.dao.ConnUtil;
import booking.dto.UserMember;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class PaymentController implements Initializable {
	
	@FXML private TextFlow nowText;
	@FXML private TextFlow laterText;
	@FXML private BorderPane paymentBorder;
	@FXML private Label payAll;
	@FXML private CheckBox agree;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		FileInputStream fis = null;
		InputStreamReader is = null;
		BufferedReader br = null;
		
		try {
			fis = new FileInputStream("C:\\pmProject\\myJAVA\\booking\\src\\booking\\textFile\\now.txt");
			is = new InputStreamReader(fis);
			br = new BufferedReader(is);
			
			String str = null;
			
			while((str = br.readLine()) != null) {
				nowText.getChildren().add(new Text(str));
				nowText.getChildren().add(new Text(System.lineSeparator()));
			}
		} catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try { if(br != null) {	br.close();	} } catch(IOException e) {}
			try { if(is != null) { is.close();	} } catch(IOException e) {}
			try { if(fis != null) { fis.close(); }	} catch(IOException e) {}
		}
		
		try {
			fis = new FileInputStream("C:\\pmProject\\myJAVA\\booking\\src\\booking\\textFile\\later.txt");
			is = new InputStreamReader(fis);
			br = new BufferedReader(is);
			
			String str = null;
			
			while((str = br.readLine()) != null) {
				laterText.getChildren().add(new Text(str));
				laterText.getChildren().add(new Text(System.lineSeparator()));
			}
			
			
		} catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try { if(br != null) {	br.close();	} } catch(IOException e) {}
			try { if(is != null) { is.close();	} } catch(IOException e) {}
			try { if(fis != null) { fis.close(); }	} catch(IOException e) {}
		}
		
		UserMember um = new UserMember();
		
		payAll.setText(String.valueOf(um.getAdult() * 100000 + um.getChildren() * 50000));
	}
	
	@FXML
	private void nextBtnAddAction(ActionEvent event) {
		if(!agree.isSelected() == true)
			return;
		
		FxmlLoader object = new FxmlLoader();
		Pane view = object.getPage("userMain");
		paymentBorder.setCenter(view);
	}
	
	@FXML
	private void returnBtnAddAction(ActionEvent event) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String[] seatBooker = null;
		String[] seatTmp = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			con = ConnUtil.getConnection();
			pstmt = con.prepareStatement("select arriveseat from reservationmember where phone = ?");
				pstmt.setString(1, ReservationController.getUserPhone());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				seatBooker = rs.getString("arriveseat").split("/");
			}
			
			pstmt.close();
			rs.close();
			
			pstmt = con.prepareStatement("update reservationmember set arriveseat = ? where phone = ?");
				pstmt.setString(1, "");
				pstmt.setString(2, ReservationController.getUserPhone());
			rs = pstmt.executeQuery();
			
			pstmt.close();
			rs.close();
			
			pstmt = con.prepareStatement("select * from seat where serial = ?");
				pstmt.setString(1, FromToController.getSerialArrive());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				seatTmp = rs.getString("seat").split("/");
			}
			
			int count = 0;
			for(int i=0; i<seatTmp.length; i++) {
				for(int j=0; j<seatBooker.length; j++) {
					if(seatTmp[i].equals(seatBooker[j])) {
						count++;
						break;
					}
				}
				if(count == 0) {
					sb.append(seatTmp[i] + "/");
				} else {
					count = 0;
				}
			}
		
			pstmt.close();
			rs.close();
			
			pstmt = con.prepareStatement("update seat set seat = ? where serial = ?");
				pstmt.setString(1, sb.toString());
				pstmt.setString(2, FromToController.getSerialArrive());
			rs = pstmt.executeQuery();
			
		} catch(SQLException e) {
			
		} finally {
			try { if(rs != null) rs.close(); } catch(SQLException e) {}
			try { if(pstmt != null) pstmt.close(); } catch(SQLException e) {}
			try { if(con != null) con.close(); } catch(SQLException e) {}			
		}
		
		FxmlLoader object = new FxmlLoader();
		Pane view = object.getPage("seat4");
		paymentBorder.setCenter(view);
	}
}
