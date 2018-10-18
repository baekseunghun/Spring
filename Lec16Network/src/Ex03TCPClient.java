import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Ex03TCPClient {

	public static void main(String[] args) {
		
		Socket soc = null;
		
		try {
			
			soc = new Socket("192.168.10.37", 9000);
			System.out.println("서버로 접속 성공 : " + soc.getRemoteSocketAddress());
			
			// 데이터 입출력 : 키보드 -> 네트워크 -> 서버 전송.
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			
			PrintWriter writer = new PrintWriter(soc.getOutputStream(), true);  // 바이트기반의 스트림을 문자기반으로 씀
			
			while(true) {
				System.out.print("메시지 : ");
				String msg = reader.readLine();
				if(msg == null) {
					break;
				}
				writer.println(msg);
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(soc != null) soc.close();
			}catch(IOException e) {}
		}
		
		
		
		
	}
	
	
}
