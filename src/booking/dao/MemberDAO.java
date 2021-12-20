package booking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import booking.dto.MemberDTO;

public class MemberDAO {
	
	public MemberDAO() {}
	
	public void reservation(MemberDTO dto) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnUtil.getConnection();
			pstmt = con.prepareStatement("insert into reservationmember (name, email, phone) values(?, ?, ?)");
				pstmt.setString(1, dto.getName());
				pstmt.setString(2, dto.getEmail());
				pstmt.setString(3, dto.getPhone());
			int result = pstmt.executeUpdate();
			System.out.println(result + "명의 예약자 예약 성공!");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { if(pstmt != null) { pstmt.close(); } } catch (SQLException e) {}
			try { if(con != null) { con.close(); } } catch (SQLException e) {}
		}
		
	}
}
