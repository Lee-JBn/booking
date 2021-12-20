package booking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import booking.dto.AirplaneDTO;

public class AirplaneDAO {
	
	ArrayList list = new ArrayList();

	public AirplaneDAO() {}
	
	public ArrayList search(String name) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ConnUtil.getConnection();
			pstmt = con.prepareStatement("select * from airplane where airplanename = ?");
				pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				list.add(rs.getString("airplanename"));
				list.add(rs.getInt("seat"));
				list.add(rs.getString("airlinename"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { if(rs != null) { rs.close(); } } catch (SQLException e) {}
			try { if(pstmt != null) { pstmt.close(); } } catch (SQLException e) {}
			try { if(con != null) { con.close(); } } catch (SQLException e) {}
			
			return list;
		}
	}
	
	public void regist(AirplaneDTO dto) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "mytest", "mytest");
			pstmt = con.prepareStatement("insert into airplane values(?, ?, ?)");
				pstmt.setString(1, dto.getAirplaneName());
				pstmt.setInt(2, dto.getSeat());
				pstmt.setString(3, dto.getAirlineName());
			int result = pstmt.executeUpdate();
			System.out.println(result + "종류의 항공기 등록 완료!");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { if(pstmt != null) { pstmt.close(); } } catch (SQLException e) {}
			try { if(con != null) { con.close(); } } catch (SQLException e) {}
		}
		
	}
}
