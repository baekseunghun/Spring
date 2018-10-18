import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SenderThread extends Thread{

	
	Socket soc;
	String nickName;
	
	public SenderThread(Socket soc) {
		this.soc = soc;
	}
	
	public SenderThread(Socket soc, String nickName) {
		this.soc = soc;
		this.nickName = nickName;
	}
	
	@Override
	public void run() {
		try {
			// 키보드 입력 -> 소켓 출력
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			
			PrintWriter writer = new PrintWriter(soc.getOutputStream());
			
			// 닉네임 전송
			if(nickName != null) {
				writer.println(nickName);
				writer.flush();
			}
			
			while(true) {
				System.out.print("메시지 : ");
				String msg = reader.readLine();
				if(msg == null || "exit".equals(msg)) {
					System.out.println("채팅을 종료합니다.");
					break;
				}
				writer.println(msg);
				writer.flush();
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(soc != null) soc.close();
			}catch(IOException e) {}
		}
		
	
		
	}
}
