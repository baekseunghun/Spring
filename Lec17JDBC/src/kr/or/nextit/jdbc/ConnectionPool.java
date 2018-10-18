package kr.or.nextit.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

public class ConnectionPool {

	// List -> ArrayList, Vector
	
	// 커넥션 풀
	private static Vector<Connection> pool = new Vector<>();  // 여기서만 쓸거니깐 private static으로 생성 
	
	private static ConnectionPool instance = new ConnectionPool();
	
	public static ConnectionPool getInstance() {
		if(instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}
	
	private ConnectionPool() {   // 디폴트 생성자
		try {
			initPool();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// 커넥션 풀 초기화 
	private synchronized void initPool() throws SQLException {  
		
		destroyPool();
		
		ConnectionFactory factory = ConnectionFactory.getInstance();
		
		for(int i = 0; i < factory.getMaxConnection(); i++) {   // 커넥션을 미리만들기
			Connection conn = factory.getConnection();
			pool.add(conn);
		}	
		
		System.out.println(pool.size() + "개의 커넥션이 풀에 준비완료.");
	}
	
	// 커넥션 풀 폐기
	private synchronized void destroyPool() throws SQLException {
		
		for(Connection conn : pool) {
			conn.close();
		}
		pool.clear();
	}
	
	// 커넥션 대여(제공)
	public synchronized Connection getConnection() {   // synchronized :동기화 보장, 꺼내오기전에 건들지 못하게
		
		if(pool.size() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		Connection conn = pool.remove(pool.size() - 1);
		
		return conn;
	}
	
	// 커넥션 반납(회수)
	public synchronized void releaseConnection(Connection conn) {  // 
		
		pool.add(conn);
		notify();
		
	}
}
