import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import kr.or.nextit.jdbc.ConnectionFactory;
import kr.or.nextit.jdbc.ConnectionPool;
import kr.or.nextit.member.dao.MemberDao;
import kr.or.nextit.member.model.Member;

public class JDBCBasic2 {

	public static void main(String[] args) {
		
		//MemberDao memberDao = new MemberDao();
		MemberDao memberDao = MemberDao.getInstance();
		
		//ConnectionFactory connFatory = new ConnectionFactory();
		ConnectionFactory connFatory = ConnectionFactory.getInstance();
		
		ConnectionPool pool = ConnectionPool.getInstance();  //new ConnectionPool();
		
		// #2. 컨넥션 연결.
		Connection conn = null;
		
		String query = "";
		
		Statement stmt = null;
		PreparedStatement pstmt = null;
	
		
		try {
			
			//conn = connFatory.getConnection();   // 직접 커넥션 연결
			
			conn = pool.getConnection();   // 커넥션 풀에서 대여.
			
			System.out.println("데이터베이스 접속 성공.");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.print("회원아이디 검색 : ");
			
			String mem_id = reader.readLine();
			
			Member m = memberDao.selectMember(conn, mem_id);
			
			System.out.println("==== 검색 결과====");
			
			if(m != null) {
			System.out.printf("%s, %s, %s, %s, %s, %s\n",
					m.getMem_id(),
					m.getMem_pwd(),
					m.getMem_name(),
					m.getMem_phone(),
					m.getMem_email(),
					m.getReg_date()
					);
			}else {
				System.out.println("검색된 결과가 없습니다.");
			}
			
			// 회원 등록
			Member mem = new Member();
			
			System.out.print("아이디 : ");
			mem.setMem_id(reader.readLine());
			
			System.out.print("비밀번호 : ");
			mem.setMem_pwd(reader.readLine());
			
			System.out.print("이름 : ");
			mem.setMem_name(reader.readLine());
			
			System.out.print("전화번호 : ");
			mem.setMem_phone(reader.readLine());
			
			System.out.print("이메일 : ");
			mem.setMem_email(reader.readLine());
			
			int updCnt = memberDao.insertMember(conn, mem);   // 이곳을 update, delete로바꿔서 실행하면 할수있다
			
			
			System.out.println(updCnt + " 명의 회원이 등록되었습니다."); 
			
			
		// #3. 쿼리 수행
		
			// 회원 목록 조회
			
			System.out.println(">>>>>> 회원목록 출력 <<<<<<");
			
			List<Member> memberList = memberDao.selectMemberList(conn);
			
			for(Member member : memberList) {
				System.out.printf("%s, %s, %s, %s, %s, %s\n",
						member.getMem_id(),
						member.getMem_pwd(),
						member.getMem_name(),
						member.getMem_phone(),
						member.getMem_email(),
						member.getReg_date()
						);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// #4. 연결 해제 
				if(conn != null) pool.releaseConnection(conn);  // 커넥션 반납.
				
			}catch(Exception e) {}
			
		}
		
		
	}
}
