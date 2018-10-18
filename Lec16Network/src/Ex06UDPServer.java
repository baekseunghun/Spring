import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Ex06UDPServer {

	public static void main(String[] args) {
		
		DatagramPacket packet = null;
		DatagramSocket socket = null;
		
		try {
			socket = new DatagramSocket();
			System.out.println("서버 소켓 생성.");
			
			// 키보드 메시지 입력 -> 소켓 전송.
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			
			while(true) {
				System.out.print("입력 : ");
				String msg = reader.readLine();
				if(msg == null) {
					break;
				}
				
				// 0 ~ 255 : 0, 1, 255 (다른 목적들, 포함 시키면안된다)
				//for(int i = 2; i <= 254; i++) {
				//	InetAddress address = InetAddress.getByName("192.168.10." + i);
					
					InetAddress address = InetAddress.getByName("192.168.10.255");
					
					packet = new DatagramPacket(msg.getBytes(), msg.length(), address, 9005);
				
				// 패킷 전송
				socket.send(packet);
				
			}
			
		}catch(SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
}
