import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCBasic {

	public static void main(String[] args) {
		
		// #1. 드라이버 로딩.
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로딩 성공.");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		// #2. 컨넥션 연결.
		Connection conn = null;
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "java";
		String password = "oracle";
		
		String query = "";
		
		Statement stmt = null;
		PreparedStatement pstmt = null;
	
		
		try {
			
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("데이터베이스 접속 성공.");
			
			// 회원 등록
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.print("아이디 : ");
			String id = reader.readLine();
			
			System.out.print("비밀번호 : ");
			String pwd = reader.readLine();
			
			System.out.print("이름 : ");
			String name = reader.readLine();
			
			System.out.print("전화번호 : ");
			String phone = reader.readLine();
			
			System.out.print("이메일 : ");
			String email = reader.readLine();
			
			query = " INSERT INTO TB_MEMBER1( " 
					+ "MEM_ID, "
					+ "MEM_PWD, "
					+ "MEM_NAME, "
					+ "MEM_PHONE, "
					+ "MEM_EMAIL, "
					+ "REG_DATE "
					+ " ) VALUES ("
					+ " ?, "
					+ " ?, "
					+ " ?, "
					+ " ?, "
					+ " ?, "
					+ " SYSDATE"
					+ " ) ";
			
			pstmt = conn.prepareStatement(query);  // 기준 쿼리가 들어간다.
			
			// 데이터 바인딩
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, phone);
			pstmt.setString(5, email);
			
			int updCnt = pstmt.executeUpdate();   // insert이니깐
			
			System.out.println(updCnt + " 명의 회원이 등록되었습니다.");
			
			
		// #3. 쿼리 수행
		
			// 회원 목록 조회
			query = "SELECT MEM_ID, MEM_PWD, MEM_NAME, MEM_PHONE, MEM_EMAIL, REG_DATE FROM TB_MEMBER";
					
					// select * from where mem_name = '향단이'
			
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(query);  // 쿼리 실행.
			
			System.out.println(">>>>>> 회원목록 출력 <<<<<<");
			
			while(rs.next()) {   // 반드시 불러야 한다.
				 
				String mem_id = rs.getString("mem_id");
				String mem_pwd = rs.getString("mem_pwd");
				String mem_name = rs.getString("mem_name");
				String mem_phone = rs.getString("mem_phone");
				String mem_email = rs.getString("mem_email");
				String reg_date = rs.getString("reg_date");
				
				System.out.printf("%s, %s, %s, %s, %s, %s\n", mem_id, mem_pwd, mem_name, mem_phone, mem_email, reg_date);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// #4. 연결 해제 
				if(conn != null) conn.close();
				
			}catch(SQLException e) {}
			
		}
		
		
	}
}
