package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.ConnectionPoolImpl;
import model.TaiKhoan;

public class AccountFunctionImpl implements AccountFunction {

	private ConnectionPoolImpl connectionPool;
	private Connection _connect;

	public AccountFunctionImpl() throws SQLException {
		this.connectionPool = new ConnectionPoolImpl();
		this._connect = this.connectionPool.getConnection();
	}
	
	@Override
	public List<TaiKhoan> getAll(String request, int pageNumber) throws SQLException {
		// TODO Auto-generated method stub
		int count = 0;
		if (pageNumber > 1) {
			count = 10;
		}
		request = (request == null) ? "" : request.trim();
		List<TaiKhoan> accounts = new ArrayList<>();
		String sql = "SELECT * FROM tblaccount WHERE CONCAT(IFNULL(phone, ''), ' ', IFNULL(fullName,''), ' ', IFNULL(email,'') ) LIKE CONCAT('%',?,'%') and role = 0 ORDER BY id ASC LIMIT  10 OFFSET ?";

		PreparedStatement statement = _connect.prepareStatement(sql);
		statement.setString(1, request);
		statement.setInt(2, count * (pageNumber - 1));
		
		ResultSet result = statement.executeQuery();

		while (result.next()) {
			TaiKhoan account = new TaiKhoan();

			account.id = result.getInt("id");
			account.email = result.getString("email");
			account.password = result.getString("password");
			account.fullName = result.getString("fullName");
			account.phone = result.getString("phone");
			account.gender = result.getString("gender");
			account.birthday = result.getString("birthday");
			account.image = result.getString("image");
			account.role = result.getInt("role");
			account.creationTime = result.getString("creationTime");

			accounts.add(account);
		}

		return accounts;
	}

	@Override
	public int getTotal(String request) throws SQLException {
		// TODO Auto-generated method stub
		int count = 0;
		request = request == null ? "" : request.trim();
		String sql = "SELECT * FROM tblaccount WHERE CONCAT(IFNULL(phone, ''), ' ', IFNULL(fullName,'')) LIKE CONCAT('%',?,'%')  and role = 0" ;

		PreparedStatement statement = _connect.prepareStatement(sql);
		statement.setString(1, request);
		ResultSet result = statement.executeQuery();

		while (result.next()) {
			count ++;
		}

		return count;
	}

	@Override
	public TaiKhoan getById(int id) throws SQLException {
		// TODO Auto-generated method stub
TaiKhoan account = new TaiKhoan();
		
		String sql = "SELECT * FROM tblaccount WHERE id=?";
		PreparedStatement statement = _connect.prepareStatement(sql);
		statement.setInt(1, id);
		ResultSet result = statement.executeQuery();
		if (result.next()) {
			account.id = result.getInt("id");
			account.email = result.getString("email");
			account.password = result.getString("password");
			account.fullName = result.getString("fullName");
			account.phone = result.getString("phone");
			account.gender = result.getString("gender");
			account.birthday = result.getString("birthday");
			account.image = result.getString("image");
			account.role = result.getInt("role");
			account.creationTime = result.getString("creationTime");
		}

		return account;
	}

	@Override
	public boolean create(TaiKhoan data) throws SQLException {
		// TODO Auto-generated method stub
		data.creationTime = String.valueOf(new Date(System.currentTimeMillis()));
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(
				"INSERT INTO tblaccount ( email, password, fullName, phone, gender, birthday, image, role , creationTime)");
		sqlBuilder.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

		PreparedStatement statement = _connect.prepareStatement(sqlBuilder.toString());
		statement.setString(1, data.email);
		statement.setString(2, data.password);
		statement.setString(3, data.fullName);
		statement.setString(4, data.phone);
		statement.setString(5, data.gender);
		statement.setString(6, data.birthday);
		statement.setString(7, data.image);
		statement.setInt(8, data.role);
		statement.setString(9, data.creationTime);
		statement.executeUpdate();

		return true;
	}

	@Override
	public boolean updateInfo(TaiKhoan data) throws SQLException {
		// TODO Auto-generated method stub
		try {
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("UPDATE tblaccount SET fullName =?, phone =?, gender = ?, birthday = ?, image = ? WHERE id=?");

			PreparedStatement statement = _connect.prepareStatement(sqlBuilder.toString());
			statement.setString(1, data.fullName);
			statement.setString(2, data.phone);
			statement.setString(3, data.gender);
			statement.setString(4, data.birthday);
			statement.setString(5, data.image);
			statement.setInt(6, data.id);

			statement.executeUpdate();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		try {
			String sql = "DELETE FROM tblaccount WHERE id=?";
			PreparedStatement statement = _connect.prepareStatement(sql);
			statement.setInt(1, id);
			statement.executeUpdate();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean changePassword(String email, String oldPass, String newPass) throws SQLException {
		// TODO Auto-generated method stub
		try {
			if (checkLogin(email, oldPass) != null) {
	            // If the match is found, update the password
	            StringBuilder sqlBuilder = new StringBuilder();
	            sqlBuilder.append("UPDATE tblaccount SET password = ? WHERE email = ? and password =?");

	            PreparedStatement statement = _connect.prepareStatement(sqlBuilder.toString());
	            statement.setString(1, newPass);
	            statement.setString(2, email);
	            statement.setString(3, oldPass);
	            statement.executeUpdate();

	            return true;
	        } else {
	            return false;
	        }

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean checkEmail(String email) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "SELECT COUNT(*) FROM tblaccount WHERE email = ?";
	    try (PreparedStatement statement = _connect.prepareStatement(sql)) {
	        statement.setString(1, email);
	        try (ResultSet resultSet = statement.executeQuery()) {
	            if (resultSet.next()) {
	                int count = resultSet.getInt(1);
	                return count > 0;
	            }
	        }
	    }
	    return false;
	}

	@Override
	public TaiKhoan checkLogin(String email, String password) throws SQLException {
		// TODO Auto-generated method stub
		TaiKhoan account = null;

	    String sql = "SELECT * FROM tblaccount WHERE email = ? AND password = ?";
	    
	    try (PreparedStatement statement = _connect.prepareStatement(sql)) {
	        statement.setString(1, email);
	        statement.setString(2, password);

	        try (ResultSet result = statement.executeQuery()) {
	            account = result.next() ? new TaiKhoan() : null;
	            if (account != null) {
	                account.id = result.getInt("id");
	                account.email = result.getString("email");
	                account.password = result.getString("password");
	                account.fullName = result.getString("fullName");
	                account.phone = result.getString("phone");
	                account.gender = result.getString("gender");
	                account.birthday = result.getString("birthday");
	                account.image = result.getString("image");
	                account.role = result.getInt("role");
	                account.creationTime = result.getString("creationTime");
	            }
	        }
	    }

	    return account;
	}

}
