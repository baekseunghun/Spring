import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReceiverThread extends Thread{
	
	Socket soc;
	
	public ReceiverThread(Socket soc) {
		this.soc = soc;
	}
	
	
	@Override
	public void run() {
		
		try {
			// 소켓 입력 -> 화면 출력
			BufferedReader reader = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			
			while(true) {
				String msg = reader.readLine();
				if(msg == null){
					System.out.println("채팅을 종료합니다.");
					break;
				}
				System.out.println("수신 > " + msg);
			}
		}catch(IOException e){
				e.printStackTrace();
		}finally {
			try {
				if(soc != null) soc.close();
			}catch(IOException e) {}
		}
	}
}
