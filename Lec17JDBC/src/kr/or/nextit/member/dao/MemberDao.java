package kr.or.nextit.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.or.nextit.member.model.Member;

// Dao = Mapper 
public class MemberDao {
	
	// Singleton 패턴.
	
	private static MemberDao instance = new MemberDao();  // 오직 객체가 하나만 만들어짐
	
	public static MemberDao getInstance(){  
		
		if(instance == null) {
			instance = new MemberDao();
		}
		
		return instance;
	}
	
	private MemberDao() {}
	
	// 데이터베이스 연결해서 입력, 수정, 삭제, 조회 기능(CRUD) 담당.

	// 회원목록 조회
	// connection 파라미터로 선언 
	public List<Member> selectMemberList(Connection conn) throws SQLException{ // 호출한 놈한테 알려줘야하기때문에 던져버린다
		
		StringBuffer query = new StringBuffer();
		
	    query.append(" SELECT          ");
		query.append(" 	  mem_id,      ");		// 회원아이디
		query.append(" 	  mem_pwd,     ");		// 회원비밀번호
		query.append(" 	  mem_name,    ");		// 회원명
		query.append("    mem_phone,   ");		// 회원전화번호
		query.append("    mem_email,   ");		// 회원이메일
		query.append("    reg_date     ");		// 회원등록일
	    query.append(" FROM            ");
	    query.append(" 	  tb_member    ");
	    
	    PreparedStatement pstmt = conn.prepareStatement(query.toString());
	    
	    ResultSet rs = pstmt.executeQuery();
		
	    List<Member> memberList = new ArrayList<>();
	    while(rs.next()) {
	    	
	    	Member member = new Member();
	    	
	    	member.setMem_id(rs.getString("mem_id"));
	    	member.setMem_pwd(rs.getString("mem_pwd"));
	    	member.setMem_name(rs.getString("mem_name"));
	    	member.setMem_phone(rs.getString("mem_phone"));
	    	member.setMem_email(rs.getString("mem_email"));
	    	member.setReg_date(rs.getString("reg_date"));
	    	
	    	memberList.add(member);
	    }
	    
		return memberList;
	}

	// 회원정보 조회(한 사람에 대한)
	public Member selectMember(Connection conn, String mem_id) throws SQLException {

		StringBuffer query = new StringBuffer();
		
	    query.append(" SELECT          ");
		query.append(" 	  mem_id,      ");		// 회원아이디
		query.append(" 	  mem_pwd,     ");		// 회원비밀번호
		query.append(" 	  mem_name,    ");		// 회원명
		query.append("    mem_phone,   ");		// 회원전화번호
		query.append("    mem_email,   ");		// 회원이메일
		query.append("    reg_date     ");		// 회원등록일
	    query.append(" FROM            ");
	    query.append(" 	  tb_member    ");
	    query.append(" WHERE mem_id = ?");
		
	    PreparedStatement pstmt = conn.prepareStatement(query.toString());
	    
	    // 데이터 바인딩
	    pstmt.setString(1, mem_id);
	    
	    ResultSet rs = pstmt.executeQuery();
	    
	    Member member = null;
	    
	    if(rs.next()) {
	    	member = new Member();
	    	
	    	member.setMem_id(rs.getString("mem_id"));
	    	member.setMem_pwd(rs.getString("mem_pwd"));
	    	member.setMem_name(rs.getString("mem_name"));
	    	member.setMem_phone(rs.getString("mem_phone"));
	    	member.setMem_email(rs.getString("mem_email"));
	    	member.setReg_date(rs.getString("reg_date"));
	    }
	    
	    
		return member;
	}

	// 회원등록
	// 몇명의회원이 등록되어있는지 알아야하니깐 int를 리턴 (밑에도 같은 이유)
	public int insertMember(Connection conn, Member member) throws SQLException {

		StringBuffer query = new StringBuffer();
		
		query.append("  INSERT INTO tb_member (    ");
		query.append(" 	      mem_id,              ");
		query.append("	      mem_pwd,             ");
		query.append("	      mem_name,            ");
		query.append("	      mem_phone,           ");
		query.append("	      mem_email,           ");
		query.append("	      reg_date             ");
		query.append("	  ) VALUES (               ");
		query.append("	      ?,                   ");
		query.append("	      ?,                   ");
		query.append("	      ?,                   ");
		query.append("	      ?,                   ");
		query.append("	      ?,                   ");
		query.append("	      SYSDATE              ");
		query.append("	  )                        ");
			                                   
		PreparedStatement pstmt = conn.prepareStatement(query.toString());
		
		int idx = 1;
		pstmt.setString(idx++, member.getMem_id());
		pstmt.setString(idx++, member.getMem_pwd());
		pstmt.setString(idx++, member.getMem_name());
		pstmt.setString(idx++, member.getMem_phone());
		pstmt.setString(idx++, member.getMem_email());
		
		int upCnt = pstmt.executeUpdate();
		
		return upCnt;
	}

	// 회원수정
	public int updateMember(Connection conn, Member member) throws SQLException {

		StringBuffer query = new StringBuffer();
		
		query.append("	UPDATE tb_member SET           ");
		query.append("		mem_pwd       = ?,         ");
		query.append("		mem_name      = ?,         ");
		query.append("		mem_phone     = ?,         ");
		query.append("		mem_email     = ?          ");
		query.append("	WHERE mem_id = ?               ");
				
		PreparedStatement pstmt = conn.prepareStatement(query.toString());
		
		int idx = 1;
		
		pstmt.setString(idx++, member.getMem_pwd());
		pstmt.setString(idx++, member.getMem_name());
		pstmt.setString(idx++, member.getMem_phone());
		pstmt.setString(idx++, member.getMem_email());
		pstmt.setString(idx++, member.getMem_id());
		
		int upCnt = pstmt.executeUpdate();
		
		return upCnt;
		
		
	}

	// 회원삭제
	public int deleteMember(Connection conn, String mem_id) throws SQLException {
		
		StringBuffer query = new StringBuffer();
		 
		query.append("  DELETE FROM tb_member WHERE mem_id = ?  ");
		
		PreparedStatement pstmt = conn.prepareStatement(query.toString());
		
		pstmt.setString(1, mem_id);
		
		int updCnt = pstmt.executeUpdate();
		
		return updCnt;
	}

}
