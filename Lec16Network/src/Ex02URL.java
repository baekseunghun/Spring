import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Ex02URL {

	public static void main(String[] args) throws IOException {
		
		// https의 s =보안유지 ?user_name=san : query, &를붙이고 내용추가할수있다.
		URL url = new URL("https://www.naver.com:443/index.html?user_name=san&user_pw=123456!");   
		
		System.out.println("protocol : " + url.getProtocol()); // 위의 문자열을 구분을해줌
		System.out.println("host : " + url.getHost());
		System.out.println("port : " + url.getPort());
		System.out.println("path : " + url.getPath());
		System.out.println("query : " + url.getQuery());
		
		// URL Connection 연결
		URLConnection connection = url.openConnection();  // 자바가 네이버에 연결
		
		//new InputStreamReader(connection.getInputStream());  // 대상이 네트워크에서 받는다.
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
		while(true) {
			String str = reader.readLine();
			if(str == null) {
				break;
			}
			System.out.println(str);
		}
		
	}
}
