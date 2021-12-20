package booking.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import booking.dao.ConnUtil;
import booking.dto.MemberDTO;
import booking.dto.UserMember;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class SeatController4 implements Initializable {

	@FXML private BorderPane seatBorder;
	@FXML private BorderPane seatScrollBorder;
	@FXML private Button nextBtn;
	
	private MaterialIconView icon;
	private GridPane seatGrid;
	private ArrayList<ArrayList<MaterialIconView>> icons;
	private ArrayList<MaterialIconView> iconsIcons;
	private StringBuffer seatSB = new StringBuffer();
	
	private int seatColumn = 7;
	private int seatRow = 10;
	

	int click = 0;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		findSeat();
		gridAddSeat();
		
		for(int i=0; i<seatRow; i++) {
			for(int j=0; j<seatColumn-1; j++) {
				icons.get(i).get(j).setOnMouseClicked(e->{
					if(click < UserMember.getAdult() + UserMember.getChildren()) {
						if(!(boolean)((Node)e.getSource()).getStyle().equals("-fx-fill: red; -fx-font-family: 'Material Icons'; -fx-font-size: 80; -fx-alignment: CENTER;")){
							if((boolean)((Node)e.getSource()).getStyle().equals("-fx-fill: blue; -fx-font-family: 'Material Icons'; -fx-font-size: 80; -fx-alignment: CENTER;")) {
								((Node)e.getSource()).setStyle("-fx-fill: black; -fx-font-family: 'Material Icons'; -fx-font-size: 80; -fx-alignment: CENTER;");
								if(seatSB.length() < 4) {
									seatSB.delete(seatSB.length()-3, seatSB.length());
								} else if(seatSB.length() != 0) {
									seatSB.delete(seatSB.length()-4, seatSB.length()-1);
								}
								click--;
							} else {
								((Node)e.getSource()).setStyle("-fx-fill: blue; -fx-font-family: 'Material Icons'; -fx-font-size: 80; -fx-alignment: CENTER;");
								seatSB.append(((Node)e.getSource()).getId() + "/");
								click++;
							}
						} else {
							System.out.println("이미 예약된 좌석입니다.");
						}
					} else if(click == UserMember.getAdult() + UserMember.getChildren()) {
						if(!(boolean)((Node)e.getSource()).getStyle().equals("-fx-fill: red; -fx-font-family: 'Material Icons'; -fx-font-size: 80; -fx-alignment: CENTER;")){
							if((boolean)((Node)e.getSource()).getStyle().equals("-fx-fill: blue; -fx-font-family: 'Material Icons'; -fx-font-size: 80; -fx-alignment: CENTER;")) {
								((Node)e.getSource()).setStyle("-fx-fill: black; -fx-font-family: 'Material Icons'; -fx-font-size: 80; -fx-alignment: CENTER;");
								if(seatSB.length() < 4) {
									seatSB.delete(seatSB.length()-3, seatSB.length());
								} else if(seatSB.length() != 0) {
									seatSB.delete(seatSB.length()-4, seatSB.length()-1);
								}
								click--;
							}
						}
					}
				});
			}
		}
	}

	@FXML
	private void nextBtnAction(ActionEvent event) {
		
		if(click != UserMember.getAdult() + UserMember.getChildren()) {
			return;
		}
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuffer tmpSeat = new StringBuffer();
		try {
			con = ConnUtil.getConnection();
			pstmt = con.prepareStatement("update reservationmember set arriveseat = ?, arriveserial = ? where phone = ?");
				pstmt.setString(1, seatSB.toString());
				pstmt.setString(2, FromToController.getSerialArrive());
				pstmt.setString(3, MemberDTO.getPhone());
			int tmp = pstmt.executeUpdate();
			pstmt.close();
			System.out.println(tmp + " 좌석 예약 성공");
			
			pstmt = con.prepareStatement("select * from seat where serial = ?");
				pstmt.setString(1, FromToController.getSerialArrive());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(!(rs.getString("seat") == null))
					tmpSeat.append(rs.getString("seat"));
				tmpSeat.append(seatSB.toString());
			}
			pstmt.close();
			
			pstmt = con.prepareStatement("update seat set seat = ? where serial = ?");
				pstmt.setString(1, tmpSeat.toString());
				pstmt.setString(2, FromToController.getSerialArrive());
			tmp = pstmt.executeUpdate();
			System.out.println(tmp + " 비행기 좌석 예약 성공");
			
		} catch(SQLException e) {
		} finally {
			try { if(rs != null) rs.close(); } catch(SQLException e) {}
			try { if(pstmt != null) pstmt.close(); } catch(SQLException e) {} 
			try { if(con != null) con.close(); } catch(SQLException e) {} 
		}
		
		FxmlLoader object = new FxmlLoader();
		Pane view = object.getPage("payment");
		seatBorder.setCenter(view);
	}
	
	private void gridAddSeat() {
		
		int idAlphabet = (int)'a';
		int idNumber = 1;
		
		StringBuffer sb; 
		
		seatGrid = new GridPane();
		seatGrid.setHgap(10);
		seatGrid.setVgap(10);
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ConnUtil.getConnection();
			pstmt = con.prepareStatement("select seat from seat where serial = ?");
				pstmt.setString(1, FromToController.getSerialArrive());
			rs = pstmt.executeQuery();
			rs.next();
			
			icons = new ArrayList<ArrayList<MaterialIconView>>();
			for(int i=0; i<seatRow; i++) {
				iconsIcons = new ArrayList<MaterialIconView>();
				for(int j=0; j<seatColumn; j++) {
					icon = new MaterialIconView(MaterialIcon.EVENT_SEAT);
					if(j == 3) {
						icon.setStyle("-fx-fill: null; -fx-font-family: 'Material Icons'; -fx-font-size: 80; -fx-alignment: CENTER;");
					} else {
						icon.setStyle("-fx-fill: black; -fx-font-family: 'Material Icons'; -fx-font-size: 80; -fx-alignment: CENTER; ");
						
						if(idAlphabet == (int)'g') {
							idAlphabet = (int)'a';
							idNumber++;
						}
						sb = new StringBuffer();
						sb.append(String.valueOf(idNumber));
						sb.append(String.valueOf((char)idAlphabet));
						icon.setId(sb.toString());
						idAlphabet++;
						
						if(rs.getString("seat") != null) {
							String[] tmp = rs.getString("seat").split("/");
							
							for(int col=0; col<seatRow; col++) {
								for(int al='a'; al<'g'; al++) {
									for(int z=0; z<tmp.length; z++) {
										if(icon.getId().equals(tmp[z])) {
											icon.setStyle("-fx-fill: red; -fx-font-family: 'Material Icons'; -fx-font-size: 80; -fx-alignment: CENTER;");
										}
									}
								}
							}
						}
						
						iconsIcons.add(icon);
					}
					seatGrid.addColumn(j, icon);
				}
				icons.add(iconsIcons);
			}
		} catch(SQLException e) {
		} finally {
			try { if(rs != null) rs.close();} catch(SQLException e) {}
			try { if(pstmt != null) pstmt.close();} catch(SQLException e) {}
			try { if(con != null) con.close();} catch(SQLException e) {}
		}	
		seatScrollBorder.setCenter(seatGrid);
		
		GridPane seatNumber = new GridPane();
		seatNumber.setMinWidth(100);
		
		Label seatNumberLabel;
		
		for(int i=0; i<seatRow; i++) {
			seatNumberLabel = new Label(String.valueOf(i+1));
			seatNumberLabel.setMinHeight(90);
			seatNumberLabel.setMinWidth(100);
			seatNumber.addRow(i, seatNumberLabel);
			seatNumberLabel.setAlignment(Pos.CENTER);
		}
		seatScrollBorder.setLeft(seatNumber);
	}
	
	private void findSeat() {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ConnUtil.getConnection();
			pstmt = con.prepareStatement("select airplanename from airplaneregist where serial = ?");
				pstmt.setString(1, FromToController.getSerialArrive());
			rs = pstmt.executeQuery();
			
			String tmp = null;
			if(rs.next()) {
				tmp = rs.getString("airplanename");
			}
			rs.close();
			pstmt.close();
			
			pstmt = con.prepareStatement("select seat from airplane where airplanename = ?");
				pstmt.setString(1, tmp);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				seatRow = rs.getInt("seat") / 6;
			}
			
		} catch(SQLException e) {
		} finally {
			try { if(rs != null) rs.close();} catch(SQLException e) {}
			try { if(pstmt != null) pstmt.close();} catch(SQLException e) {}
			try { if(con != null) con.close();} catch(SQLException e) {}
		}
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
			pstmt = con.prepareStatement("select startseat from reservationmember where phone = ?");
				pstmt.setString(1, ReservationController.getUserPhone());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				seatBooker = rs.getString("startseat").split("/");
			}
			
			pstmt.close();
			rs.close();
			
			pstmt = con.prepareStatement("update reservationmember set startseat = ? where phone = ?");
				pstmt.setString(1, "");
				pstmt.setString(2, ReservationController.getUserPhone());
			rs = pstmt.executeQuery();
			
			pstmt.close();
			rs.close();
			
			pstmt = con.prepareStatement("select * from seat where serial = ?");
				pstmt.setString(1, FromToController.getSerial());
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
				pstmt.setString(2, FromToController.getSerial());
			rs = pstmt.executeQuery();
			
		} catch(SQLException e) {
			
		} finally {
			try { if(rs != null) rs.close(); } catch(SQLException e) {}
			try { if(pstmt != null) pstmt.close(); } catch(SQLException e) {}
			try { if(con != null) con.close(); } catch(SQLException e) {}			
		}
		
		FxmlLoader object = new FxmlLoader();
		Pane view = object.getPage("seat3");
		seatBorder.setCenter(view);
	}
}
