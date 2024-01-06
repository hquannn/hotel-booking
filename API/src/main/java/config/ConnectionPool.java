package config;

import java.sql.*;

public interface ConnectionPool {
	//Xin kết nối để làm việc
	public Connection getConnection() throws SQLException;
	
	//Yêu cầu trả về kết nối
	public void releaseConnection(Connection con) throws SQLException;
}
