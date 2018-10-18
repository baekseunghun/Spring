package kr.or.nextit.jdbc;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

	private String driver;
	private String url;
	private String user;
	private String password;
	private int maxConnection;
	
    private static ConnectionFactory instance = new ConnectionFactory(); 
    
    public static ConnectionFactory getInstance() {
    	if(instance == null) {     // 절대 null 안나오게 안전장치!
    		instance = new ConnectionFactory();
    	}
    	return instance;
    }

	private ConnectionFactory() {   // 생성자
		
		
		try {
			
			Properties prop = new Properties();
			prop.load(new FileReader("src/db.properties"));
			
			driver = prop.getProperty("driver");// oracle.jdbc.driver.OracleDriver 값을준다. 
			url = prop.getProperty("url");
			user = prop.getProperty("user");
			password = prop.getProperty("password");
			 
			if(prop.getProperty("max.connection") != null) {
				maxConnection = Integer.parseInt(prop.getProperty("max.connection"));
			}
			// 드라이버 로딩
			Class.forName(driver);
			System.out.println("ConnectionFactory -> JDBC Driver 로딩.");
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() throws SQLException {  // 메서드
		
		Connection conn = DriverManager.getConnection(url, user, password);
		
		return conn;
	}
	public int getMaxConnection() {
		return maxConnection;
	}
}
