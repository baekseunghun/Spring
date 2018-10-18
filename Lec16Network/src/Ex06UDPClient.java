import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Ex06UDPClient {

	public static void main(String[] args) {
		
		DatagramPacket packet = null;
		DatagramSocket socket = null;
		
		try {
			socket = new DatagramSocket(9005);   //  ex) 우편함번호
			System.out.println("클라이언트 생성.");
			
			byte[] buf = new byte[512];
			
 			packet = new DatagramPacket(buf, buf.length);
 			
 			while(true) {
	 			// 패킷 수신.
	 			socket.receive(packet);
	 			
	 			String msg = new String(packet.getData(), 0, packet.getLength());
	 			System.out.println("수신 > " + msg);
 			}
 			
 			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
