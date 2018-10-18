import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Ex05MultiChatServer {
	
	private static ArrayList<ClientThread> clientList = new ArrayList<>();
	
	class ClientThread extends Thread{     //inner class
		
		Socket soc;
		PrintWriter writer;
		String nickName;
		
		ClientThread(Socket soc){
			try {
				this.soc = soc;
				writer = new PrintWriter(soc.getOutputStream(), true);
				
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			// Client로 부터 수신한 메시지 -> 접속한 모든 Client에게 전송.
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(soc.getInputStream()));
				
				// 닉네임 수신.
				nickName = reader.readLine();
				sendAll("#" + nickName + "님이 입장하셨습니다.");
				
				// 클라이언트가 보낸 메시지 수신.
				while(true) {
					String msg = reader.readLine();
					if(msg == null) {
						break;
					}
						
					// 모든 클라이언트에게 메시지 전송
					sendAll(nickName + "님의 말 : " + msg);
				}
				
			}catch(IOException e) {
				e.printStackTrace();
			}finally {
				try {
					
					sendAll(nickName + "님이 퇴장하셨습니다.");
					clientList.remove(this);
					
					if(soc != null) soc.close();
					
				}catch(IOException e) {}
			}
		}
		
		public void sendAll(String msg) {   // 클라이언트 목록에서 하나씩꺼내서 출력
			
			for(ClientThread client : clientList) {
				client.writer.println(msg);
				
			}
			
		}
		
	}
	
	public void start() {    // 메인이 static이기때문에 스타트로 넘김
		
		ServerSocket server = null;
		try {
			server = new ServerSocket(9002);
			System.out.println(">>> Server Ready......");
			
			while(true) {
				// 클라이언트 대기 , 들어오면 추가해주고 반복
				Socket soc = server.accept();
				System.out.println("클라이언트 접속 : " + soc.getRemoteSocketAddress());
				
				ClientThread client = new ClientThread(soc);
				clientList.add(client);   // 클라이언트 목록.
				client.start();
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {   // 호출 
		
		Ex05MultiChatServer obj = new Ex05MultiChatServer();
		obj.start();
		
	}
	
	
}
