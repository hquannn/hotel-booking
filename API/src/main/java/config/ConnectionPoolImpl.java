package config;

import java.sql.*;
import java.util.*;

public class ConnectionPoolImpl implements ConnectionPool {
	
	// Trình điều khiển
	private String driver;
	//Đường dẫn thực thi
	private String url;
	
	// Tài khoản làm việc
	private String username;
	private String userpass;
	
	
	//Đối tượng lưu trữ kết nối
	private Stack<Connection> pool = new Stack<>();
	
	public ConnectionPoolImpl()
	{
		//Xác định trình điều khiển
		
		this.driver = "com.mysql.cj.jdbc.Driver";
		
		try {
			Class.forName(this.driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//xác định đường dẫn
		this.url = "jdbc:mysql://localhost:3306/it6020003_data_btl?characterEncoding=UTF-8";
		
		//Xác định tài khoản 
		this.username = "khoi";
		this.userpass = "Leminhkhoi2003";
		
//		ArrayList<Connection> pool = new ArrayList<>();
		// kỹ thuật upcasting
		
		//Xác định bộ nhớ
		this.pool = new Stack<>();
		
	}
	
	@Override
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		
		if(this.pool.isEmpty())
		{
			return DriverManager.getConnection(this.url, this.username, this.userpass);
		}
		else {
			return this.pool.pop();
		}
	}

	@Override
	public void releaseConnection(Connection con) throws SQLException {
		this.pool.push(con);
	}
	
	
	protected void finalize() throws Throwable {
		this.pool.clear();
		this.pool = null;
		System.out.println("ConnectionPool is Closed");
	}
}