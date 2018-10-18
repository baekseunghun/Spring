import java.io.IOException;
import java.net.Socket;

public class Ex05MultiChatClient {

	public static void main(String[] args) {
		
		Socket soc = null;
		
		try {
			
			soc = new Socket("192.168.10.37", 9002);
			System.out.println("서버 접속 성공 : " + soc.getRemoteSocketAddress());
			
			String nickName = "이산2";
			SenderThread sender = new SenderThread(soc, nickName);
			ReceiverThread receiver = new ReceiverThread(soc);
			
			sender.start();
			receiver.start();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
