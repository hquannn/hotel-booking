package DAO;

import java.sql.SQLException;
import java.util.List;

import model.TaiKhoan;

public interface AccountFunction {
	public List<TaiKhoan> getAll(String request, int pageNumber) throws SQLException;
	public int getTotal(String request) throws SQLException;
	public TaiKhoan getById(int id) throws SQLException;
	public boolean create(TaiKhoan data) throws SQLException;
	public boolean updateInfo(TaiKhoan data) throws SQLException;
	public boolean delete(int id);
	public boolean changePassword(String email, String oldPass , String newPass) throws SQLException;
	public boolean checkEmail(String email) throws SQLException;
	public TaiKhoan checkLogin(String email, String password) throws SQLException;
}
