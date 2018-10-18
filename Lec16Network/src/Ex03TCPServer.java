import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class Ex03TCPServer {

	public static void main(String[] args) {
		
		ServerSocket server = null;
		
		try {
			server = new ServerSocket(9000);
			System.out.println(">>> Server Ready......");
			
			// 클라이언트 요청 대기 
			Socket soc = server.accept();
			System.out.println("클라이언트 접속 성공 : " + soc.getRemoteSocketAddress());
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			
			while(true) {
				String msg = reader.readLine();
				if(msg == null) {
					break;
				}
				System.out.println("수신 > " + msg);
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(server != null) server.close();
			}catch(IOException e) {}
		}
		
		
		
		
	}
}
