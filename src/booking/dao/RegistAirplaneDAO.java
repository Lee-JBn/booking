package booking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import booking.dto.RegistAirplaneDTO;
import booking.tableview.RegistInformation;

public class RegistAirplaneDAO {

	private ArrayList list = new ArrayList();

	public void registAirplane(RegistAirplaneDTO dto) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ConnUtil.getConnection();
			pstmt = con.prepareStatement("insert into airplaneregist values(?, ?, ?, ?, ?, ?)");
				pstmt.setString(1, dto.getAirline());
				pstmt.setString(2, dto.getAirplaneName());
				pstmt.setString(3, dto.getTime());
				pstmt.setString(4, dto.getStartAirport());
				pstmt.setString(5, dto.getArriveAirport());
				pstmt.setString(6, dto.getSerial());
			int tmp = pstmt.executeUpdate();
			System.out.println(tmp + "대의 운행이 등록되었습니다.");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
		
		try {
			con = ConnUtil.getConnection();
			pstmt = con.prepareStatement("insert into seat values(?, ?)");
				pstmt.setString(1, dto.getSerial());
				pstmt.setString(2, null);
			int tmp = pstmt.executeUpdate();
			System.out.println(tmp + "좌석 등록 완료");
		} catch(SQLException e) {
			
		} finally {
			try { if(pstmt != null) pstmt.close(); } catch(SQLException e) {} 
			try { if(con != null) con.close(); } catch(SQLException e) {} 
		}
	}

	public ArrayList searchAirplane(String airlineName, String airplaneName) throws ParseException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;

		try {
			con = ConnUtil.getConnection();
			pstmt = con.prepareStatement("select * from airplaneregist where airlinename = ? and airplanename = ? order by time asc");
				pstmt.setString(1, airlineName);
				pstmt.setString(2, airplaneName);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String startTime = rs.getString("time");
				String[] tmp = startTime.split(" ");
				String date = tmp[0];
				String time = tmp[1].substring(0, 5);
				String startAirport = rs.getString("startairport");
				String arriveAirport = rs.getNString("arriveairport");
				String arriveDate = null;
				String serial = rs.getString("serial");
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date tmpDate = dateFormat.parse(rs.getNString("time"));

				Calendar cal = Calendar.getInstance();

				if (rs.getString("startairport").equals("Incheon")) {
					switch (rs.getString("arriveairport")) {
					case "Osaka":
						cal.setTime(tmpDate);
						cal.add(Calendar.HOUR, 2);
						arriveDate = dateFormat.format(cal.getTime());
						break;
					case "Los Angeles":
						cal.setTime(tmpDate);
						cal.add(Calendar.HOUR, -4);
						arriveDate = dateFormat.format(cal.getTime());
						break;
					}
				} else if (rs.getString("startairport").equals("Osaka")) {
					switch (rs.getString("arriveairport")) {
					case "Incheon":
						cal.setTime(tmpDate);
						cal.add(Calendar.HOUR, 2);
						arriveDate = dateFormat.format(cal.getTime());
						break;
					case "Los Angeles":
						cal.setTime(tmpDate);
						cal.add(Calendar.HOUR, 36);
						arriveDate = dateFormat.format(cal.getTime());
						break;
					}
				} else if (rs.getString("startairport").equals("Los Angeles")) {
					switch (rs.getString("arriveairport")) {
					case "Osaka":
						cal.setTime(tmpDate);
						cal.add(Calendar.HOUR, 60);
						arriveDate = dateFormat.format(cal.getTime());
						break;
					case "Incheon":
						cal.setTime(tmpDate);
						cal.add(Calendar.HOUR, 30);
						arriveDate = dateFormat.format(cal.getTime());
						break;
					}
				}
				
				pstmt2 = con.prepareStatement("select seat from airplane where airplanename = ?");
					pstmt2.setString(1, airplaneName);
				rs2 = pstmt2.executeQuery();
				
				int airSeat = 0;
				if(rs2.next()) {
					airSeat = rs2.getInt("seat");
				}
				
				int emptySeat = airSeat;
				
				pstmt2.close();
				rs2.close();
				
				pstmt2 = con.prepareStatement("select seat from seat where serial = ?");
					pstmt2.setString(1, serial);
				rs2 = pstmt2.executeQuery();
				
				if(rs2.next()) {
					if(rs2.getString("seat") != null) {
						String[] tmp2 = rs2.getString("seat").split("/");
						emptySeat = airSeat - tmp2.length;
					}
				}
				
				RegistInformation ri = new RegistInformation(date, time, startAirport, arriveAirport, arriveDate, emptySeat);
				list.add(ri);
				
				pstmt2.close();
				rs2.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
			}
			try {
				if (rs2 != null) {
					rs.close();
				}
			} catch (SQLException e) {
			}
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
			}
			try {
				if (pstmt2 != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}

		return list;
	}

}

