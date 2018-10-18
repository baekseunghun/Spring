import java.net.InetAddress;
import java.net.UnknownHostException;

public class Ex01InetAddress {

	public static void main(String[] args) throws UnknownHostException {
		
		System.out.println(">>> 로컬 시스템 정보 <<<");
		
		InetAddress local = InetAddress.getLocalHost();
		System.out.println("getHostAddress() : " + local.getHostAddress());
		System.out.println("getHostName() : " + local.getHostName());
		System.out.println("getLoopbackAddress() : " + local.getLoopbackAddress());
		
		System.out.println(">>> 원격 시스템 정보 <<<");
		
		// ex) www.naver.com(도메인)221.224.3.120 : DNS  아이피주소로 들어간다.
		InetAddress remote = InetAddress.getByName("www.naver.com");  
		System.out.println("IP 주소 : " + remote.getHostAddress());
		
		System.out.println(">>> 원격 시스템 정보2 <<<");
		
		InetAddress[] remotes = InetAddress.getAllByName("www.naver.com");
		
		for(int i = 0; i < remotes.length; i++) {
			System.out.println("IP 주소 : " + remote.getHostAddress());
		}
		
		
	}
}
