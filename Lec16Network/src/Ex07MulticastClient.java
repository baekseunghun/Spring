import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

public class Ex07MulticastClient {

public static void main(String[] args) {
		
		DatagramPacket packet = null;
		MulticastSocket socket = null;
		
		try {
			socket = new MulticastSocket(9006);   
			System.out.println("클라이언트 생성.");
			
			// 멀티캐스트는 특정그룹만 
			// 그룹에 조인
			InetAddress address = InetAddress.getByName("224.128.1.5");
			socket.joinGroup(address);
			
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

	

