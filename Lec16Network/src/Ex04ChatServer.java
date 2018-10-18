import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Ex04ChatServer {

	public static void main(String[] args) {
		
		ServerSocket server = null;
		
		try {
			server = new ServerSocket(9001);
			System.out.println(">>> Serever Ready......");
			
			// 클라이언트 요청 대기
			Socket soc = server.accept();
			System.out.println("클라이언트 접속 : " + soc.getRemoteSocketAddress());
			
			SenderThread sender = new SenderThread(soc);
			ReceiverThread receiver = new ReceiverThread(soc);
			
			sender.start();
			receiver.start();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
