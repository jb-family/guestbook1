package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestBookVo;



public class GuestBookDao {
//0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt= null;
	private ResultSet rs= null;
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	//메소드 드라이버 연결
	public void getConnecting() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);
			
			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
		} catch (Exception e) {
			System.out.println("error:" + e);
		}
	}
	
	public void close() {
		// 5. 자원정리
				try {
				if (rs!= null) {
				rs.close();
				}
				if (pstmt!= null) {
				pstmt.close();
				}
				if (conn != null) {
				conn.close();
				}
				} catch (SQLException e) {
				System.out.println("error:" + e);
				}
				}	
	
	
	
	//메소드 - 일반
	
	public int guestBookInsert(GuestBookVo guestBookVo) {
		int count = -1;
		getConnecting();
		try {
		
		// 3. SQL문준비/ 바인딩/ 실행
		//SQL문준비
		String query= "";
		query += " insert into guestbook ";
		query += " values (seq_guestbook_no.nextval, ?, ?, ?, sysdate) ";
			
		
		//바인딩
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, guestBookVo.getName());
		pstmt.setString(2, guestBookVo.getPassword());
		pstmt.setString(3, guestBookVo.getContent());
		
		//실행	
		count = pstmt.executeUpdate();
		// 4.결과처리
		
		System.out.println(count + "건이 추가되었습니다.");
		} catch (SQLException e) {
		System.out.println("error:" + e);
		}
		close();
		
		return count;
	}
	
	
	public int guestBookDelete(GuestBookVo guestBookVo) {
		int count = -1;
		getConnecting();
		try {
		
		// 3. SQL문준비/ 바인딩/ 실행
		//SQL문준비
		String query= "";
		query += " delete from guestbook ";
		query += " where password = ? ";
		query += " and   no = ? ";
			
		
		//바인딩
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, guestBookVo.getPassword());
		pstmt.setInt(2, guestBookVo.getNo());
		
		//실행	
		count = pstmt.executeUpdate();
		
		
		// 4.결과처리
		System.out.println(count + "건이 삭제되었습니다.");
		
		} catch (SQLException e) {
		System.out.println("error:" + e);
		}
		close();
		
		return count;
	}
	
	
	public int guestBookUpdate(GuestBookVo guestBookVo) {
		int count = -1;
		getConnecting();
		try {
		
		// 3. SQL문준비/ 바인딩/ 실행
		//SQL문준비
		String query= "";
		query += " update guestbook ";
		query += " set name = ? ";
		query += "     ,password = ? ";
		query += "     ,content = ? ";
		query += "     ,reg_date = ? ";
		query += " where no = ? ";
			
		
		//바인딩
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1,guestBookVo.getName());	
		pstmt.setString(2,guestBookVo.getPassword());	
		pstmt.setString(3,guestBookVo.getContent());	
		pstmt.setString(4,guestBookVo.getRegDate());	
		pstmt.setInt(5,guestBookVo.getNo());	
		
		//실행	
		count = pstmt.executeUpdate();
		
		// 4.결과처리
		System.out.println(count + "건이 수정되었습니다.");
		
		} catch (SQLException e) {
		System.out.println("error:" + e);
		}
		close();
		
		return count;
	}
	
	
	public List<GuestBookVo> guestBookList() {
		List<GuestBookVo> guestBookList = new ArrayList<GuestBookVo>();
		getConnecting();
		try {
		
		// 3. SQL문준비/ 바인딩/ 실행
		//SQL문준비
		String query= "";
		query += " select  no ";
		query += "         ,name ";
		query += "         ,password ";
		query += "         ,content ";
		query += "         ,reg_date ";
		query += " from    guestbook ";
			
		//바인딩
		pstmt = conn.prepareStatement(query);
		
		//실행	
		rs = pstmt.executeQuery();
		
		// 4.결과처리
		while(rs.next()) {
			
			int no = rs.getInt("no");
			String name = rs.getString("name");
			String password = rs.getString("password");
			String content = rs.getString("content");
			String regDate = rs.getString("reg_date");
			
			GuestBookVo guestBookVo = new GuestBookVo(no, name, password, content, regDate);
			
			guestBookList.add(guestBookVo);
			
		}
		
		
		} catch (SQLException e) {
		System.out.println("error:" + e);
		}
		close();
		
		return guestBookList;
	}
	
	public GuestBookVo guestBookList(int no) {
		GuestBookVo guestBookVo = null;
		getConnecting();
		try {
		
		// 3. SQL문준비/ 바인딩/ 실행
		//SQL문준비
		String query= "";
		query += " select  no ";
		query += "         ,name ";
		query += "         ,password ";
		query += "         ,content ";
		query += "         ,reg_date ";
		query += " from    guestbook ";
		query += " where    no = ? ";
			
		//바인딩
		pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, no);
		
		//실행	
		rs = pstmt.executeQuery();
		
		// 4.결과처리
		while(rs.next()) {
			
			int id = rs.getInt("no");
			String name = rs.getString("name");
			String password = rs.getString("password");
			String content = rs.getString("content");
			String regDate = rs.getString("reg_date");
			
			guestBookVo = new GuestBookVo(id, name, password, content, regDate);
			
			
		}
		
		
		} catch (SQLException e) {
		System.out.println("error:" + e);
		}
		close();
		
		return guestBookVo;
	}
	
	
}
